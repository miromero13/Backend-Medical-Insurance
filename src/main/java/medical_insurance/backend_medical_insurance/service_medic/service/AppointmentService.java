package medical_insurance.backend_medical_insurance.service_medic.service;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Channel;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import medical_insurance.backend_medical_insurance.common.enums.AppointmentStatusEnum;
import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.hospital.service.DoctorService;
import medical_insurance.backend_medical_insurance.service_medic.dto.AppointmentDto;
import medical_insurance.backend_medical_insurance.service_medic.entity.AppointmentEntity;
import medical_insurance.backend_medical_insurance.service_medic.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class AppointmentService {

    private static final String APPLICATION_NAME = "Medical Insurance";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private Calendar calendarService;
    private GoogleAuthorizationCodeFlow flow;
    private Credential credential;

    @Value("${google.client.client-id}")
    private String clientId;

    @Value("${google.client.client-secret}")
    private String clientSecret;

    @Value("${google.client.redirectUri}")
    private String redirectUri;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ClientService patientService;

    public String authorize() throws Exception {
        GoogleClientSecrets clientSecrets = new GoogleClientSecrets()
                .setWeb(new GoogleClientSecrets.Details()
                        .setClientId(clientId)
                        .setClientSecret(clientSecret)
                        .setRedirectUris(Collections.singletonList(redirectUri)));

        flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR))
                .build();

        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUri);
        return authorizationUrl.build();
    }

    public void handleCallback(String code) throws Exception {
        TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
        credential = flow.createAndStoreCredential(response, "user");
        calendarService = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public AppointmentEntity createAppointment(AppointmentDto appointmentDto) throws Exception {
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.doctor = doctorService.getOneById(appointmentDto.doctorId);
        appointment.patient = patientService.getOneById(appointmentDto.patientId);
        appointment.appointmentDate = appointmentDto.appointmentDate;
        appointment.status = AppointmentStatusEnum.SCHEDULE;

        appointment = appointmentRepository.save(appointment);

        createGoogleCalendarEvent(appointment);
        return appointment;
    }

    public AppointmentEntity getAppointmentById(UUID appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public ResponseMessage<List<AppointmentEntity>> getAllAppointments() {
        try {
            List<AppointmentEntity> appointments = appointmentRepository.findAll()
                    .stream()
                    .toList();
            return ResponseMessage.success(appointments, "Appointments found", appointments.size());
        } catch (Exception ex) {
            return ResponseMessage.error("An error occurred while getting the appointments: " + ex.getMessage(), 400);
        }
    }

    public AppointmentEntity updateAppointmentStatus(UUID appointmentId, AppointmentStatusEnum newStatus) throws Exception {
        AppointmentEntity appointment = getAppointmentById(appointmentId);
        appointment.status = newStatus;
        appointmentRepository.save(appointment);

        switch (newStatus) {
            case CANCELLED:
                deleteGoogleCalendarEvent(appointment);
                break;
            case NO_SHOW:
                updateGoogleCalendarEvent(appointment, "El paciente no se presentó");
                break;
            case COMPLETED:
                updateGoogleCalendarEvent(appointment, "Cita completada");
                break;
            default:
                break;
        }

        return appointment;
    }

    private void createGoogleCalendarEvent(AppointmentEntity appointment) throws Exception {
        Event event = new Event()
                .setSummary("Cita médica")
                .setDescription("Consulta con el Dr. " + appointment.doctor.user.name)
                .set("attendees", Arrays.asList(
                        createAttendee(appointment.patient.email),
                        createAttendee(appointment.doctor.user.email)
                ));

        EventDateTime start = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(Date.from(appointment.appointmentDate.atZone(ZoneId.systemDefault()).toInstant())))
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(Date.from(appointment.appointmentDate.plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant())))
                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);

        EventReminder[] reminders = new EventReminder[]{
                new EventReminder().setMethod("popup").setMinutes((int) java.time.Duration.between(appointment.appointmentDate.toLocalDate().atTime(7, 0), appointment.appointmentDate).toMinutes()),
                new EventReminder().setMethod("popup").setMinutes(30)
        };

        Event.Reminders eventReminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminders));
        event.setReminders(eventReminders);

        Event createdEvent = calendarService.events().insert("primary", event)
                .set("sendUpdates", "all")
                .execute();

        appointment.googleCalendarEventId = createdEvent.getId();
        appointmentRepository.save(appointment);
    }

    private Map<String, String> createAttendee(String email) {
        Map<String, String> attendee = new HashMap<>();
        attendee.put("email", email);
        return attendee;
    }

    private void deleteGoogleCalendarEvent(AppointmentEntity appointment) throws Exception {
        if (appointment.googleCalendarEventId != null) {
            calendarService.events().delete("primary", appointment.googleCalendarEventId)
                    .set("sendUpdates", "all")
                    .execute();
        }
    }

    private void updateGoogleCalendarEvent(AppointmentEntity appointment, String newDescription) throws Exception {
        if (appointment.googleCalendarEventId != null) {
            Event event = calendarService.events().get("primary", appointment.googleCalendarEventId).execute();
            event.setDescription(newDescription);
            calendarService.events().update("primary", event.getId(), event)
                    .set("sendUpdates", "all")
                    .execute();
        }
    }

    public ResponseMessage<String> watchCalendarEvents() {
        try {
            Channel channel = new Channel()
                    .setId(UUID.randomUUID().toString())
                    .setType("web_hook")
                    .setAddress("https://yourdomain.com/notifications");

            Channel response = calendarService.events().watch("primary", channel).execute();
            return ResponseMessage.success(response.getId(), "Watching calendar events", 1);
        } catch (Exception ex) {
            return ResponseMessage.error("Failed to watch calendar events: " + ex.getMessage(), 500);
        }
    }

    @Scheduled(fixedRate = 6 * 24 * 60 * 60 * 1000)  // Cada 6 días
    public void renewWatch() {
        try {
            watchCalendarEvents();
            System.out.println("Calendar watch renewed successfully.");
        } catch (Exception ex) {
            System.err.println("Failed to renew calendar watch: " + ex.getMessage());
        }
    }
    public void stopWatching(String channelId, String resourceId) {
        try {
            Channel channel = new Channel().setId(channelId).setResourceId(resourceId);
            calendarService.channels().stop(channel).execute();
            System.out.println("Stopped watching channel: " + channelId);
        } catch (Exception ex) {
            System.err.println("Failed to stop watching channel: " + ex.getMessage());
        }
    }
}

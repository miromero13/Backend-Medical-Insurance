package medical_insurance.backend_medical_insurance.service_medic.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import medical_insurance.backend_medical_insurance.common.enums.AppointmentStatusEnum;
import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.service_medic.dto.AppointmentDto;
import medical_insurance.backend_medical_insurance.service_medic.entity.AppointmentEntity;
import medical_insurance.backend_medical_insurance.service_medic.service.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/appointment")
@Tag(name = "Appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Operation()
    @PostMapping("/")
    public ResponseMessage<AppointmentEntity> create(@Valid @RequestBody AppointmentDto appointmentDto) throws Exception {
        AppointmentEntity appointment = appointmentService.createAppointment(appointmentDto);
        return ResponseMessage.success(appointment, "Appointment created successfully", 1);
    }

    @Operation()
    @GetMapping("/{id}")
    public ResponseMessage<AppointmentEntity> getById(@PathVariable UUID id) {
        return ResponseMessage.success(appointmentService.getAppointmentById(id), "Appointment found successfully", 1);
    }

    @Operation()
    @GetMapping("/")
    public ResponseEntity<ResponseMessage<List<AppointmentEntity>>> getAll() {
        return ResponseEntity.ok(ResponseMessage.success(appointmentService.getAllAppointments().getData(), null,
                appointmentService.getAllAppointments().getData().size()));
    }

    @Operation()
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentEntity> update(@PathVariable UUID id, @RequestBody AppointmentStatusEnum status) throws Exception {
        return ResponseEntity.ok(appointmentService.updateAppointmentStatus(id, status));
    }

    @Operation()
    @GetMapping("/login/google")
    public ResponseMessage<String> googleLogin() throws Exception {
        String authUrl = appointmentService.authorize();
        return ResponseMessage.success(authUrl, "Redirect to this URL to authenticate with Google", 1);
    }

    @Operation()
    @GetMapping("/login/google/callback")
    public String oauthCallback(@RequestParam("code") String code) throws Exception {
        appointmentService.handleCallback(code);
        return "Google authentication completed successfully!";
    }

    @PostMapping("/notifications")
    public void handleNotification(@RequestHeader Map<String, String> headers) {
        String resourceState = headers.get("X-Goog-Resource-State");
        String resourceId = headers.get("X-Goog-Resource-Id");
        String channelId = headers.get("X-Goog-Channel-Id");

        switch (resourceState) {
            case "exists":
                System.out.println("An event was created or updated: " + resourceId);
                break;
            case "deleted":
                System.out.println("An event was deleted: " + resourceId);
                break;
            default:
                System.out.println("Unknown resource state: " + resourceState);
        }
    }

    @PostConstruct
    public void initWatch() {
        try {
            appointmentService.watchCalendarEvents();
        } catch (Exception ex) {
            System.err.println("Failed to initialize event watch: " + ex.getMessage());
        }
    }
}

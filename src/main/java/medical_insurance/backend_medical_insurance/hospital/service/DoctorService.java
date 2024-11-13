package medical_insurance.backend_medical_insurance.hospital.service;

import medical_insurance.backend_medical_insurance.common.enums.DayEnum;
import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.hospital.dto.DoctorDto;
import medical_insurance.backend_medical_insurance.hospital.dto.ScheduleDto;
import medical_insurance.backend_medical_insurance.hospital.dto.ScheduleIntervalDto;
import medical_insurance.backend_medical_insurance.hospital.dto.WeeklyScheduleDto;
import medical_insurance.backend_medical_insurance.hospital.entity.DoctorEntity;
import medical_insurance.backend_medical_insurance.hospital.entity.ScheduleEntity;
import medical_insurance.backend_medical_insurance.hospital.entity.SpecialtyEntity;
import medical_insurance.backend_medical_insurance.hospital.repository.DoctorRepository;
import medical_insurance.backend_medical_insurance.hospital.repository.ScheduleRepository;
import medical_insurance.backend_medical_insurance.hospital.repository.SpecialtyRepository;
import medical_insurance.backend_medical_insurance.service_medic.repository.AppointmentRepository;
import medical_insurance.backend_medical_insurance.user.entity.UserEntity;
import medical_insurance.backend_medical_insurance.user.repository.UserRepository;
import medical_insurance.backend_medical_insurance.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    public ResponseMessage<DoctorEntity> create(DoctorDto doctorDto) {
        try {
            DoctorEntity doctor = new DoctorEntity();
            doctor.licenseNumber = doctorDto.licenseNumber;
            doctor.specialties = specialtyService.getOneById(doctorDto.specialtyId);
            UserEntity user = userService.getUserById(doctorDto.userId);
            doctor.user = user;
            user.doctor = doctor;

            doctor = doctorRepository.save(doctor);

            if (doctorDto.scheduleIds != null) {
                for (ScheduleDto scheduleDto : doctorDto.scheduleIds) {
                    ScheduleEntity schedule = new ScheduleEntity();
                    schedule.dayOfWeek = scheduleDto.dayOfWeek;
                    schedule.startTime = scheduleDto.startTime;
                    schedule.endTime = scheduleDto.endTime;

                    schedule = scheduleRepository.save(schedule);
                    doctor.schedules.add(schedule);
                }
            }

            return ResponseMessage.success(doctor, "Doctor created successfully", 1);

        } catch (Exception ex) {
            throw new RuntimeException("Error while creating doctor: " + ex.getMessage());
        }
    }

    public ResponseMessage<List<DoctorEntity>> getAll() {
        try {
            List<DoctorEntity> doctors = doctorRepository.findAll();
            return ResponseMessage.success(doctors, "Doctors retrieved successfully", doctors.size());
        } catch (Exception ex) {
            return ResponseMessage.error("Error retrieving doctors: " + ex.getMessage(), 400);
        }
    }

    public DoctorEntity getOneById(UUID id) {
        try {
            return doctorRepository.findByIdWithUser(id)
                    .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        } catch (Exception ex) {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }

    @Transactional
    public ResponseMessage<Void> delete(UUID id) {
        try {
            DoctorEntity doctor = doctorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

            doctorRepository.delete(doctor);
            return ResponseMessage.success(null, "Doctor deleted successfully", 1);

        } catch (Exception ex) {
            return ResponseMessage.error("Error deleting doctor: " + ex.getMessage(), 500);
        }
    }

    @Transactional
    public ResponseMessage<DoctorEntity> update(UUID id, Optional<DoctorDto> doctorDto) {
        try {
            DoctorEntity doctor = doctorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

            doctorDto.ifPresent(dto -> {
                if (dto.specialtyId != null) {
                    doctor.specialties = specialtyService.getOneById(dto.specialtyId);
                }

                if (dto.scheduleIds != null) {
                    scheduleRepository.deleteAll(doctor.schedules);

                    List<ScheduleEntity> newSchedules = dto.scheduleIds.stream().map(scheduleDto -> {
                        ScheduleEntity schedule = new ScheduleEntity();
                        schedule.dayOfWeek = scheduleDto.dayOfWeek;
                        schedule.startTime = scheduleDto.startTime;
                        schedule.endTime = scheduleDto.endTime;
                        return scheduleRepository.save(schedule);
                    }).toList();

                    doctor.schedules.clear();
                    doctor.schedules.addAll(newSchedules);
                }
            });

            return ResponseMessage.success(doctorRepository.save(doctor), "Doctor successfully updated", 1);

        } catch (Exception ex) {
            return ResponseMessage.error("Error while updating: " + ex.getMessage(), 500);
        }
    }

    public ResponseMessage<List<WeeklyScheduleDto>> getDoctorWeeklyAvailability(UUID doctorId) {
        try {
            DoctorEntity doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

            LocalDate startDate = LocalDate.now();
            List<WeeklyScheduleDto> weeklyAvailability = new ArrayList<>();

            for (int i = 0; i < 7; i++) { // Loop through 7 days for one week
                LocalDate currentDay = startDate.plusDays(i);
                DayEnum currentDayOfWeek = DayEnum.valueOf(currentDay.getDayOfWeek().toString());

                List<ScheduleEntity> schedules = scheduleRepository.findByDoctorIdAndDayOfWeek(doctorId, currentDayOfWeek);

                List<ScheduleIntervalDto> dailyIntervals = new ArrayList<>();

                for (ScheduleEntity schedule : schedules) {
                    LocalTime time = schedule.startTime;
                    while (!time.isAfter(schedule.endTime)) {
                        LocalDateTime dateTimeSlot = currentDay.atTime(time);
                        boolean isAvailable = appointmentRepository
                                .findByDoctorIdAndAppointmentDate(doctorId, dateTimeSlot)
                                .isEmpty();

                        ScheduleIntervalDto interval = new ScheduleIntervalDto();
                        interval.startTime = time;
                        interval.endTime = time.plusMinutes(30);
                        interval.isAvailable = isAvailable;

                        dailyIntervals.add(interval);

                        time = time.plusMinutes(30);
                    }
                }

                WeeklyScheduleDto dailySchedule = new WeeklyScheduleDto();
                dailySchedule.date = currentDay;
                dailySchedule.day = currentDay.getDayOfWeek().toString(); // Add day name
                dailySchedule.hours = dailyIntervals; // List of intervals

                weeklyAvailability.add(dailySchedule);
            }

            return ResponseMessage.success(weeklyAvailability, "Weekly doctor availability retrieved successfully", weeklyAvailability.size());
        } catch (Exception ex) {
            return ResponseMessage.error("Error retrieving doctor weekly availability: " + ex.getMessage(), 500);
        }
    }
}


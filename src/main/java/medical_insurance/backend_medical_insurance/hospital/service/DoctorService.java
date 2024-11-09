package medical_insurance.backend_medical_insurance.hospital.service;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.hospital.dto.DoctorDto;
import medical_insurance.backend_medical_insurance.hospital.dto.ScheduleDto;
import medical_insurance.backend_medical_insurance.hospital.entity.DoctorEntity;
import medical_insurance.backend_medical_insurance.hospital.entity.ScheduleEntity;
import medical_insurance.backend_medical_insurance.hospital.entity.SpecialtyEntity;
import medical_insurance.backend_medical_insurance.hospital.repository.DoctorRepository;
import medical_insurance.backend_medical_insurance.hospital.repository.ScheduleRepository;
import medical_insurance.backend_medical_insurance.hospital.repository.SpecialtyRepository;
import medical_insurance.backend_medical_insurance.user.entity.UserEntity;
import medical_insurance.backend_medical_insurance.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseMessage<DoctorEntity> createDoctorWithSchedules(DoctorDto doctorDto) {
        try {
            SpecialtyEntity specialty = specialtyRepository.findById(doctorDto.specialtyId)
                    .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + doctorDto.specialtyId));

            UserEntity user = userRepository.findById(doctorDto.userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + doctorDto.userId));

            DoctorEntity doctor = new DoctorEntity();
            doctor.licenseNumber = doctorDto.licenseNumber;
            doctor.specialties = specialty;
            doctor.user = user;

            doctor = doctorRepository.save(doctor);

            // Associate schedules (create new schedules if they don't exist)
            if (doctorDto.scheduleIds != null) {
                for (ScheduleDto scheduleDto : doctorDto.scheduleIds) {
                    ScheduleEntity schedule = new ScheduleEntity();
                    schedule.dayOfWeek = scheduleDto.dayOfWeek;
                    schedule.startTime = scheduleDto.startTime;
                    schedule.endTime = scheduleDto.endTime;

                    schedule = scheduleRepository.save(schedule);
                }
            }

            // Save doctor with schedules
            doctorRepository.save(doctor);

            return ResponseMessage.success(doctor, "Doctor created with schedules successfully", 1);

        } catch (Exception ex) {
            throw new RuntimeException("Error while creating doctor: " + ex.getMessage());
        }
    }

    public ResponseMessage<List<DoctorEntity>> getAllDoctors() {
        try {
            List<DoctorEntity> doctors = doctorRepository.findAll();
            return ResponseMessage.success(doctors, "Doctors retrieved successfully", doctors.size());
        } catch (Exception ex) {
            return ResponseMessage.error("Error retrieving doctors: " + ex.getMessage(), 400);
        }
    }

    public DoctorEntity getOneDoctor(UUID id) {
        try {
            return doctorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        } catch (Exception ex) {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }

    @Transactional
    public ResponseMessage<Void> deleteDoctor(UUID id) {
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
    public ResponseMessage<DoctorEntity> updateDoctor(UUID id, Optional<DoctorDto> doctorDto) {
        try {
            DoctorEntity doctor = doctorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

            doctorDto.ifPresent(dto -> {
                if (dto.specialtyId != null) {
                    doctor.specialties = specialtyRepository.findById(dto.specialtyId)
                            .orElseThrow(() -> new RuntimeException("Specialty not found"));
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
}


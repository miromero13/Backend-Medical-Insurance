package medical_insurance.backend_medical_insurance.service_medic.repository;

import medical_insurance.backend_medical_insurance.service_medic.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, UUID> {
    @Query("SELECT a FROM AppointmentEntity a WHERE a.doctor.id = :doctorId AND a.appointmentDate = :appointmentDate")
    List<AppointmentEntity> findByDoctorIdAndAppointmentDate(@Param("doctorId") UUID doctorId, @Param("appointmentDate") LocalDateTime appointmentDate);
}

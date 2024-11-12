package medical_insurance.backend_medical_insurance.service_medic.repository;
import medical_insurance.backend_medical_insurance.service_medic.entity.FilledFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FilledFormRepository extends JpaRepository<FilledFormEntity, UUID> {
    List<FilledFormEntity> findByAppointmentId(UUID appointmentId);
}
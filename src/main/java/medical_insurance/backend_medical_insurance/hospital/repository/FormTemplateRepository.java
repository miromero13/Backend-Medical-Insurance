package medical_insurance.backend_medical_insurance.hospital.repository;

import medical_insurance.backend_medical_insurance.hospital.entity.FormTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FormTemplateRepository extends JpaRepository<FormTemplateEntity, UUID> {
}

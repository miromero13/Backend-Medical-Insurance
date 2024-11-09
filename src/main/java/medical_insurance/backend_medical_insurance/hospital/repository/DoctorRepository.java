package medical_insurance.backend_medical_insurance.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import medical_insurance.backend_medical_insurance.hospital.entity.DoctorEntity;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, UUID> {
}

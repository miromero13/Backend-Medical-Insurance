package medical_insurance.backend_medical_insurance.service_medic.repository;

import medical_insurance.backend_medical_insurance.service_medic.entity.ClientAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ClientAddressRepository extends JpaRepository<ClientAddressEntity, UUID> {
}

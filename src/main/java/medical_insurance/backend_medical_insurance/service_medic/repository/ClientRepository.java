package medical_insurance.backend_medical_insurance.service_medic.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import medical_insurance.backend_medical_insurance.service_medic.entity.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {
    Optional<ClientEntity> findByEmail (String email);
    Optional<ClientEntity> findByPhone (String phone);
}

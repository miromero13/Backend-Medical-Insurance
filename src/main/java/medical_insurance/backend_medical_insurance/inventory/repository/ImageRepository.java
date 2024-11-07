package medical_insurance.backend_medical_insurance.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import medical_insurance.backend_medical_insurance.inventory.entity.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {
  Optional<ImageEntity> findByName(String fileName);
}

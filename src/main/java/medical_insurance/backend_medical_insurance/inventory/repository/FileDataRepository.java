package medical_insurance.backend_medical_insurance.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import medical_insurance.backend_medical_insurance.inventory.entity.FileData;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, UUID> {
    Optional<FileData> findByName(String fileName);
}

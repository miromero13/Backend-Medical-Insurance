package medical_insurance.backend_medical_insurance.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import medical_insurance.backend_medical_insurance.inventory.entity.BranchEntity;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, UUID> {
}
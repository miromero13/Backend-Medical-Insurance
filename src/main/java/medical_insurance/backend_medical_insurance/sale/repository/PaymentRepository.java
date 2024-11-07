package medical_insurance.backend_medical_insurance.sale.repository;

import medical_insurance.backend_medical_insurance.sale.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
}

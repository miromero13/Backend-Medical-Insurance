package medical_insurance.backend_medical_insurance.sale.entity;

import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class PaymentEntity extends BaseEntity {
    
    @Column(nullable = false)
    public Number amount;

    @Column(nullable = false)
    public String status;

    @Column(nullable = true, length = 600)
    public String paymentUrl;

    // @ManyToOne()
    // @JoinColumn(name = "payment_method_id", nullable = false)
    // public PaymentMethodEntity paymentMethod;
}

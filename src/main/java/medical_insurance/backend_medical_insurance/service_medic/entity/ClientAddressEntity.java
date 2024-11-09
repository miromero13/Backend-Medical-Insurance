package medical_insurance.backend_medical_insurance.service_medic.entity;

import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "client_address")
public class ClientAddressEntity extends BaseEntity {

    @Column(nullable = false)
    public String address;

    @Column(nullable = false)
    public String country;

    @Column(nullable = true)    
    public String zipcode;  

    @Column(nullable = true)
    public String phone;

    @Column(nullable = false)
    public String name;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    public ClientEntity client;
}

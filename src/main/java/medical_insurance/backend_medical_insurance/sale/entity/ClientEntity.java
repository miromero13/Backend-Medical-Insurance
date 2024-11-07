package medical_insurance.backend_medical_insurance.sale.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import medical_insurance.backend_medical_insurance.inventory.entity.BranchEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "clients")
public class ClientEntity extends BaseEntity {

    @Column(nullable = false)
    public String name;
    
    @Column(nullable = false, length = 15)
    public String phone;

    @Column(nullable = true)
    public String email;

    @JsonIgnore
    @Column(nullable = true)
    public String password;
    
    @JsonIgnore
    @Column(nullable = true, length = 6)
    public String verificationCode;

    @JsonIgnore
    @Column(nullable = true)
    public LocalDateTime verificationCodeGeneratedAt;

    @ManyToOne()
    @JoinColumn(name = "branch_id", nullable = true)
    public BranchEntity branch;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<ClientAddressEntity> clientAddress;
        
    public void setVerificationCode(String code) {
        this.verificationCode = code;
        this.verificationCodeGeneratedAt = LocalDateTime.now();
    }
    
    public void clearVerificationCode() {
        this.verificationCode = null;
        this.verificationCodeGeneratedAt = null;
    }
}

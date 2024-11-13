package medical_insurance.backend_medical_insurance.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import medical_insurance.backend_medical_insurance.common.enums.GenderEnum;
import medical_insurance.backend_medical_insurance.hospital.entity.BranchEntity;
import jakarta.persistence.*;
import medical_insurance.backend_medical_insurance.hospital.entity.DoctorEntity;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    public String ci;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String phone;

    @Column(nullable = false, unique = true, length = 100)
    public String email;

    @JsonIgnore
    @Column(nullable = false)
    public String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    public GenderEnum gender;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE", insertable = false)
    public Boolean active;

    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false)
    public RoleEntity role;

    @ManyToOne()
    @JoinColumn(name = "branch_id", nullable = true)
    public BranchEntity branch;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "doctor_id", nullable = true)
    public DoctorEntity doctor;
}

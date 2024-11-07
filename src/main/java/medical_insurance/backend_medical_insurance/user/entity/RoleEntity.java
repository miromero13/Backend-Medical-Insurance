package medical_insurance.backend_medical_insurance.user.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;

@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    public String name;

    // @JsonBackReference
    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    public List<UserEntity> users;
}

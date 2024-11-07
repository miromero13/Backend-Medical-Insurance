package medical_insurance.backend_medical_insurance.inventory.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import medical_insurance.backend_medical_insurance.user.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "branch")
public class BranchEntity extends BaseEntity {

    @Column(nullable = false)
    public String name;

    @Column(nullable = true, length = 200)
    public String address;

    @Column(nullable = false, length = 20)
    public String phone;

    @Column(nullable = false)
    public String email;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE", insertable = false)
    public Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<UserEntity> users;
}

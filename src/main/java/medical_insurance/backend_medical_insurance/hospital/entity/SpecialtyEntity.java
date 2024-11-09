package medical_insurance.backend_medical_insurance.hospital.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "specialty")
public class SpecialtyEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    public String name;

    @Column(length = 500)
    public String description;

    @JsonIgnore
    @OneToMany(mappedBy = "specialties", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<DoctorEntity> doctors;

    @ManyToOne()
    @JoinColumn(name = "branch_id", nullable = false)
    public BranchEntity branch;
}

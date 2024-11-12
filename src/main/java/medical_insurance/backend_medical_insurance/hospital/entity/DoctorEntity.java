package medical_insurance.backend_medical_insurance.hospital.entity;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import jakarta.persistence.*;
import medical_insurance.backend_medical_insurance.user.entity.UserEntity;

@Entity
@Table(name = "doctor")
public class DoctorEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 15)
    public String licenseNumber;

    @ManyToOne()
    @JoinColumn(name = "specialties_id", nullable = false)
    public SpecialtyEntity specialties;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ScheduleEntity> schedules = new ArrayList<>();

    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;
}

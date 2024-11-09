package medical_insurance.backend_medical_insurance.hospital.entity;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import medical_insurance.backend_medical_insurance.common.enums.DayEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "schedule")
public class ScheduleEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public DayEnum dayOfWeek;  // Example values: MONDAY, TUESDAY

    @Column(nullable = false)
    public LocalTime startTime;

    @Column(nullable = false)
    public LocalTime endTime;
}

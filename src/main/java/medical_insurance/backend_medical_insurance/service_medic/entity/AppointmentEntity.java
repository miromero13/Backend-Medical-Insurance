package medical_insurance.backend_medical_insurance.service_medic.entity;

import jakarta.persistence.*;
import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import medical_insurance.backend_medical_insurance.common.enums.AppointmentStatusEnum;
import medical_insurance.backend_medical_insurance.hospital.entity.DoctorEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
public class AppointmentEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    public DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    public ClientEntity patient;

    @Column(nullable = false)
    public LocalDateTime appointmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public AppointmentStatusEnum status;

    @Column(name = "google_calendar_event_id", nullable = true)
    public String googleCalendarEventId;
}

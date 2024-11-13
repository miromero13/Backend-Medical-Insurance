package medical_insurance.backend_medical_insurance.service_medic.entity;

import jakarta.persistence.*;
import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import medical_insurance.backend_medical_insurance.hospital.entity.FormTemplateEntity;

import java.time.LocalDate;

@Entity
@Table(name = "filled_form")
public class FilledFormEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "form_template_id", nullable = false)
    public FormTemplateEntity formTemplate;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    public AppointmentEntity appointment;

    @Lob
    @Column(nullable = false)
    public String filledData;

    @Column(nullable = false)
    public LocalDate dateFilled;
}

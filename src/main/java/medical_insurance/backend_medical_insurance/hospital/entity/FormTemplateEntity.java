package medical_insurance.backend_medical_insurance.hospital.entity;
import jakarta.persistence.*;
import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;

@Entity
@Table(name = "form_templates")
public class FormTemplateEntity extends BaseEntity {

    @Column(nullable = false)
    public String formName; // Nombre del formulario (Ej: "Triaje", "Receta")

    @Lob
    @Column(nullable = false) // Asegura que este campo no sea nulo
    public String formStructure; // Estructura del formulario en formato JSON (campos predefinidos)
}

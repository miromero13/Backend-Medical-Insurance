package medical_insurance.backend_medical_insurance.hospital.dto;

public class FormTemplateDto {
    public String formName;
    public String formStructure;

    public FormTemplateDto(String formName, String formStructure){
        this.formName = formName;
        this.formStructure = formStructure;
    }
}

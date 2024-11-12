package medical_insurance.backend_medical_insurance.service_medic.dto;

import java.util.UUID;

public class FilledFormDto {
    public UUID formTemplateId;
    public UUID appointmentId;
    public String filledData;

    public FilledFormDto(UUID formTemplateId, UUID appointmentId, String filledData) {
        this.formTemplateId = formTemplateId;
        this.appointmentId = appointmentId;
        this.filledData = filledData;
    }
}

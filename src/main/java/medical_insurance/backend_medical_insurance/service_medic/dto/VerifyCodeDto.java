package medical_insurance.backend_medical_insurance.service_medic.dto;

import jakarta.validation.constraints.NotBlank;

public class VerifyCodeDto {

    @NotBlank(message = "Phone number is required")
    public String phone;

    @NotBlank(message = "Verification code is required")
    public String verificationCode;
}

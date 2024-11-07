package medical_insurance.backend_medical_insurance.sale.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClientLoginRequestDto {

    @Schema(description = "Phone number", example = "78712721", required = true)
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number format")
    public String phone;

    public ClientLoginRequestDto() {
    }

    public ClientLoginRequestDto(String phone) {
        this.phone = phone;
    }
}

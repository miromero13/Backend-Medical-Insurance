package medical_insurance.backend_medical_insurance.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLoginRequestDto {
    @Schema(description = "Email", example = "luis@gmail.com", required = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    public String email;

    @Schema(description = "Password", example = "12345678", required = true)
    @NotBlank(message = "Password is required")
    public String password;
}

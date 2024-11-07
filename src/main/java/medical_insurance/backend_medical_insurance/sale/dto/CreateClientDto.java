package medical_insurance.backend_medical_insurance.sale.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateClientDto {

    @NotBlank(message = "Name is required")
    public String name;

    @NotBlank(message = "Phone number is required")
    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    public String phone;

    @Email(message = "Email should be valid")
    public String email;

    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    public String password;
}

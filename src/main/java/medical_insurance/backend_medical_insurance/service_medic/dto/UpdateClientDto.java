package medical_insurance.backend_medical_insurance.service_medic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateClientDto {

    public String name;

    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    public String phone;

    @Email(message = "Email should be valid")
    public String email;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    public String password;
}

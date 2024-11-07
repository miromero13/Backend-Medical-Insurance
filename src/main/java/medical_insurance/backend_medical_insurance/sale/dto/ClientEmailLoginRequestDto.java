package medical_insurance.backend_medical_insurance.sale.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClientEmailLoginRequestDto {

    @NotBlank(message = "El correo electrónico es requerido")
    @Email(message = "Correo electrónico no válido")
    public String email;

    @NotBlank(message = "La contraseña es requerida")
    public String password;

    public ClientEmailLoginRequestDto() {
    }

    public ClientEmailLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

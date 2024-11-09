package medical_insurance.backend_medical_insurance.hospital.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO para la creación de un nuevo usuario")
public class CreateBranchDto {

    @Schema(description = "Nombre de la sucursal", example = "Sucursal 1", required = true)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    public String name;

    @Schema(description = "Correo electrónico de la sucursal", example = "sucursal1@gmail.com", required = true)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 100, message = "El correo electrónico no debe exceder los 100 caracteres")
    public String email;

    @Schema(description = "Número de teléfono de la sucursal", example = "78901234", required = false)
    @Size(min = 5, max = 15, message = "El número de teléfono debe tener entre 5 y 15 caracteres")
    @Pattern(regexp = "^\\+?[0-9. ()-]{5,15}$", message = "El número de teléfono es inválido")
    public String phone;

    @Schema(description = "Dirección de la sucursal", example = "Av. 6 de Agosto", required = true)
    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 1, max = 200, message = "La dirección debe tener entre 1 y 200 caracteres")
    public String address;
}

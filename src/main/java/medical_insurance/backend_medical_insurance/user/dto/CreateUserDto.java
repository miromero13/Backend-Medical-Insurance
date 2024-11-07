package medical_insurance.backend_medical_insurance.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import medical_insurance.backend_medical_insurance.common.enums.GenderEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO para la creación de un nuevo usuario")
public class CreateUserDto {

    @Schema(description = "Carnet de identidad del usuario", example = "11223344", required = true)
    @NotNull(message = "El CI es obligatorio")
    @Pattern(regexp = "^\\d{8,13}$", message = "El CI debe contener entre 8 y 13 dígitos")
    public String ci;

    @Schema(description = "Nombre del usuario", example = "Empleado", required = true)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    public String name;

    @Schema(description = "Correo electrónico del usuario", example = "empleado1@gmail.com", required = true)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 100, message = "El correo electrónico no debe exceder los 100 caracteres")
    public String email;

    @Schema(description = "Contraseña del usuario", example = "P@ssw0rd!", required = true)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 20, message = "La contraseña debe tener entre 6 y 20 caracteres")
    // @Pattern(
    // regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{6,20}$",
    // message = "La contraseña debe contener al menos una letra mayúscula, una
    // letra minúscula, un número y un carácter especial"
    // )
    public String password;

    @Schema(description = "Número de teléfono del usuario", example = "+1234567890", required = false)
    @Size(min = 5, max = 15, message = "El número de teléfono debe tener entre 5 y 15 caracteres")
    @Pattern(regexp = "^\\+?[0-9. ()-]{5,15}$", message = "El número de teléfono es inválido")
    public String phone;

    @Schema(description = "Género del usuario", example = "MASCULINO", required = false, allowableValues = {
            "MASCULINO", "FEMENINO", "OTRO" })
    public GenderEnum gender;

    @Schema(description = "ID del rol del usuario", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
    @NotNull(message = "El ID del rol es obligatorio")
    public UUID roleId;

    @Schema(
    description = "ID de la sucursal del usuario",
    example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    required = false
    )
    public UUID branchId;
}

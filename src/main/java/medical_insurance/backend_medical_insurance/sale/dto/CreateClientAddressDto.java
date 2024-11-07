package medical_insurance.backend_medical_insurance.sale.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateClientAddressDto {
    
    @NotBlank(message = "La dirección es obligatoria")
    @Schema(description = "Dirección del cliente", example = "123 Main St")
    public String address;

    @NotBlank(message = "El país es obligatorio")
    @Schema(description = "País del cliente", example = "Mexico")
    public String country;

    @Schema(description = "Código postal del cliente", example = "12345")
    public String zipcode;

    @Schema(description = "Teléfono del cliente", example = "+52 123 456 7890")
    public String phone;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del cliente", example = "John Doe")
    public String name;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del cliente", example = "123e4567-e89b-12d3-a456-426614174000")
    public UUID clientId;
}

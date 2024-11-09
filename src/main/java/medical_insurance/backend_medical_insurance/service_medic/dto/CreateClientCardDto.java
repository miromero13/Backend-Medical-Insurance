package medical_insurance.backend_medical_insurance.service_medic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CreateClientCardDto {
    
    @NotBlank(message = "El número de tarjeta es obligatorio")
    @Size(min = 16, max = 16, message = "El número de tarjeta debe tener 16 dígitos")
    @Schema(description = "Número de la tarjeta de crédito", example = "4111111111111111")
    public String number;

    @NotBlank(message = "El CVV es obligatorio")
    @Size(min = 3, max = 3, message = "El CVV debe tener 3 dígitos")
    @Schema(description = "Código de seguridad de la tarjeta", example = "123")
    public String cvv;

    @NotBlank(message = "La fecha de expiración es obligatoria")
    @Schema(description = "Fecha de expiración de la tarjeta", example = "12/25")
    public String expiration;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del cliente asociado a la tarjeta", example = "123e4567-e89b-12d3-a456-426614174000")
    public UUID clientId;
}

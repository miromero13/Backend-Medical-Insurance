package medical_insurance.backend_medical_insurance.service_medic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class CreatePaymentMethodDto {
    
    @NotBlank(message = "El tipo de método de pago es obligatorio")
    @Schema(description = "Tipo de método de pago", example = "Credit Card")
    public String type;

    @NotBlank(message = "La descripción es obligatoria")
    @Schema(description = "Descripción del método de pago", example = "Tarjeta de crédito Visa")
    public String description;
}

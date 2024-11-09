package medical_insurance.backend_medical_insurance.service_medic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreatePaymentDto {
    @NotBlank(message = "El estado es obligatorio")
    @Schema(description = "Estado del pago", example = "pending")
    public String status;

    @NotNull(message = "El ID de la orden es obligatorio")
    @Schema(description = "ID de la orden", example = "123e4567-e89b-12d3-a456-426614174005")
    public UUID orderId;

    @NotBlank(message = "La URL de éxito es obligatoria")
    @Schema(description = "URL de éxito de Stripe", example = "https://mi-sitio.com/success")
    public String successUrl;

    @NotBlank(message = "La URL de cancelación es obligatoria")
    @Schema(description = "URL de cancelación de Stripe", example = "https://mi-sitio.com/cancel")
    public String cancelUrl;
}

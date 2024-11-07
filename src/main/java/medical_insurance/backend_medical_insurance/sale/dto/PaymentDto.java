package medical_insurance.backend_medical_insurance.sale.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentDto {

    @Schema(description = "Monto del pago en la unidad más pequeña de la moneda (centavos si es USD)", example = "5000")
    @NotNull(message = "El monto no puede ser nulo")
    public Long amount;

    @Schema(description = "Moneda en la que se realiza el pago", example = "BOB")
    @NotBlank(message = "La moneda no puede estar en blanco")
    public String currency;

    @Schema(description = "Descripción del pago", example = "Compra de prueba")
    @NotBlank(message = "La descripción no puede estar en blanco")
    public String description;

    // Información adicional para la sesión de pago
    @Schema(description = "URL de redirección en caso de éxito", example = "https://mi-sitio.com/success")
    @NotBlank(message = "La URL de éxito no puede estar en blanco")
    public String successUrl;

    @Schema(description = "URL de redirección en caso de cancelación", example = "https://mi-sitio.com/cancel")
    @NotBlank(message = "La URL de cancelación no puede estar en blanco")
    public String cancelUrl;

    @Schema(description = "Tiempo de expiración en minutos", example = "60")
    @NotNull(message = "El tiempo de expiración no puede ser nulo")
    public Integer expirationMinutes;
}

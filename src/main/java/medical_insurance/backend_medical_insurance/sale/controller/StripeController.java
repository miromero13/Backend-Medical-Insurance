package medical_insurance.backend_medical_insurance.sale.controller;

import com.stripe.exception.StripeException;
import medical_insurance.backend_medical_insurance.sale.dto.PaymentDto;
import medical_insurance.backend_medical_insurance.sale.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    /**
     * Endpoint para crear una sesión de pago y devolver la URL de la sesión.
     *
     * @param paymentDTO Datos de la solicitud de pago (monto, moneda, descripción, URLs de éxito/cancelación, tiempo de expiración).
     * @return La URL de la sesión de pago de Stripe.
     */
    @PostMapping("/create-checkout-session")
    public ResponseEntity<String> createCheckoutSession(@RequestBody PaymentDto paymentDTO) {
        try {
            // Llama al servicio de Stripe para crear la sesión de pago y obtener la URL
            String checkoutUrl = stripeService.createCheckoutSession(paymentDTO);
            // Devuelve la URL en la respuesta
            return ResponseEntity.ok(checkoutUrl);
        } catch (StripeException e) {
            // Manejar excepciones de Stripe y devolver un error al cliente
            return ResponseEntity.status(500).body("Error creando la sesión de pago: " + e.getMessage());
        }
    }
}

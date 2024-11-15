package medical_insurance.backend_medical_insurance.service_medic.controller;

import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.tags.Tag;
import medical_insurance.backend_medical_insurance.service_medic.dto.CreatePaymentDto;
import medical_insurance.backend_medical_insurance.service_medic.entity.PaymentEntity;
import medical_insurance.backend_medical_insurance.service_medic.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Endpoint para crear un nuevo pago
    @PostMapping
    public ResponseEntity<PaymentEntity> createPayment(@RequestBody CreatePaymentDto createPaymentDto) {
        try {            
            PaymentEntity payment = paymentService.createPayment(createPaymentDto);
            return ResponseEntity.ok(payment);
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        } catch (RuntimeException e) {            
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint para obtener un pago por su ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentEntity> getPaymentById(@PathVariable UUID id) {
        PaymentEntity payment = paymentService.getOneById(id);
        return ResponseEntity.ok(payment);
    }

    // Endpoint para obtener todos los pagos
    @GetMapping
    public ResponseEntity<List<PaymentEntity>> getAllPayments() {
        List<PaymentEntity> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }
}

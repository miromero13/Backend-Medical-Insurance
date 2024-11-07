package medical_insurance.backend_medical_insurance.sale.service;

import com.stripe.exception.StripeException;
import medical_insurance.backend_medical_insurance.sale.dto.CreatePaymentDto;
import medical_insurance.backend_medical_insurance.sale.dto.PaymentDto;
import medical_insurance.backend_medical_insurance.sale.entity.PaymentEntity;
import medical_insurance.backend_medical_insurance.sale.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StripeService stripeService; 

    public PaymentEntity createPayment(CreatePaymentDto createPaymentDto) throws StripeException {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.amount = (long) 100.0;
        paymentDto.currency = "bob";
        paymentDto.description = "Pago de la servicio medico ";
        paymentDto.successUrl = createPaymentDto.successUrl;
        paymentDto.cancelUrl = createPaymentDto.cancelUrl;
        paymentDto.expirationMinutes = 30;

        String paymentSessionUrl = stripeService.createCheckoutSession(paymentDto);

        PaymentEntity payment = new PaymentEntity();
        payment.amount = 100.0;
        payment.status = "pending";
        payment.paymentUrl = paymentSessionUrl;

        paymentRepository.save(payment);

        return payment;
    }

    public PaymentEntity getOneById(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public List<PaymentEntity> getAllPayments() {
        return paymentRepository.findAll();
    }    
}

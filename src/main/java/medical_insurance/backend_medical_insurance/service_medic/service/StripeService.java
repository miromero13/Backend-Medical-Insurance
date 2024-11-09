package medical_insurance.backend_medical_insurance.service_medic.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import medical_insurance.backend_medical_insurance.service_medic.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String apiKey;

    public String createCheckoutSession(PaymentDto paymentDTO) throws StripeException {        
        Stripe.apiKey = apiKey;
        
        long currentTimestamp = System.currentTimeMillis() / 1000;
        long expiresAt = currentTimestamp + (paymentDTO.expirationMinutes * 60); 
      
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(paymentDTO.successUrl) 
                .setCancelUrl(paymentDTO.cancelUrl)   
                .setExpiresAt(expiresAt)              
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(paymentDTO.currency)
                                .setUnitAmount(paymentDTO.amount*100) 
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(paymentDTO.description)                                       
                                        .build())
                                .build())
                        .build())
                .build();
        
        Session session = Session.create(params);
        
        return session.getUrl();
    }
}

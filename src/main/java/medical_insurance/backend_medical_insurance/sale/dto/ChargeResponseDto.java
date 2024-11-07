package medical_insurance.backend_medical_insurance.sale.dto;

import com.stripe.model.Charge;

public class ChargeResponseDto {
    public String id;
    public Long amount;
    public String currency;
    public String description;
    public String status;

    // Constructor que acepta un objeto Charge
    public ChargeResponseDto(Charge charge) {
        this.id = charge.getId();
        this.amount = charge.getAmount();
        this.currency = charge.getCurrency();
        this.description = charge.getDescription();
        this.status = charge.getStatus();
    }
}

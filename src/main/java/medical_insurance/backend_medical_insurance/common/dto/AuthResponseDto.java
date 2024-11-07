package medical_insurance.backend_medical_insurance.common.dto;

public class AuthResponseDto {
    public String jwtToken;

    public AuthResponseDto(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}


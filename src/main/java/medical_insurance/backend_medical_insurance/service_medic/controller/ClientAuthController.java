package medical_insurance.backend_medical_insurance.service_medic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import medical_insurance.backend_medical_insurance.common.dto.AuthResponseDto;
import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.service_medic.dto.ClientEmailLoginRequestDto;
import medical_insurance.backend_medical_insurance.service_medic.dto.ClientLoginRequestDto;
import medical_insurance.backend_medical_insurance.service_medic.dto.VerifyCodeDto;
import medical_insurance.backend_medical_insurance.service_medic.service.ClientAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/client/auth")
@Tag(name = "Client Auth")
public class ClientAuthController {

    @Autowired
    private ClientAuthService clientAuthService;

    @PostMapping("/login/email")
    public ResponseMessage<AuthResponseDto> loginWithEmailAndPassword(
            @RequestBody ClientEmailLoginRequestDto clientLoginRequestDto) {
        return clientAuthService.authenticateWithEmailAndPassword(clientLoginRequestDto);
    }

    @PostMapping("/login/whatsapp")
    @Operation(security = @SecurityRequirement(name = ""))
    public ResponseMessage<String> login(@RequestBody ClientLoginRequestDto clientLoginRequestDto) {
        System.out.println("Número de teléfono recibido: " + clientLoginRequestDto.phone);
        return clientAuthService.sendVerificationCode(clientLoginRequestDto);
    }

    @PostMapping("/verify")
    public ResponseMessage<AuthResponseDto> verifyCode(@RequestBody VerifyCodeDto verifyCodeDto) {
        return clientAuthService.verifyCode(verifyCodeDto);
    }

    @PostMapping("/logout")
    public ResponseMessage<String> logout() {
        return ResponseMessage.success("User logged out successfully", "Logged out", 1);
    }
}

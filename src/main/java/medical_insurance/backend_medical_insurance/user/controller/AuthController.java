package medical_insurance.backend_medical_insurance.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.user.dto.UserLoginRequestDto;
import medical_insurance.backend_medical_insurance.user.service.UserAuthService;
import medical_insurance.backend_medical_insurance.user.service.UserAuthService.AuthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Users Auth")
public class AuthController {

    @Autowired
    private UserAuthService authService;

    @PostMapping("/login" )
    @Operation(security = @SecurityRequirement(name = ""))
    public ResponseMessage<AuthResponseDTO> login(@Valid @RequestBody UserLoginRequestDto loginRequestDto) {
        return authService.authenticateUser(loginRequestDto);
    }

    @GetMapping()
    public ResponseMessage<?> checkToken(@RequestParam String token) {
        return authService.checkToken(token);
    }
}

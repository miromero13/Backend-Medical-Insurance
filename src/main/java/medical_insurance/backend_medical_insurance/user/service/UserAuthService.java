package medical_insurance.backend_medical_insurance.user.service;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.user.dto.UserLoginRequestDto;
import medical_insurance.backend_medical_insurance.user.entity.UserEntity;
import medical_insurance.backend_medical_insurance.user.provider.UserJwtTokenProvider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserJwtTokenProvider jwtTokenProvider;

    // Método para autenticar un usuario
    public ResponseMessage<AuthResponseDTO> authenticateUser(UserLoginRequestDto loginRequestDto) {
        UserEntity userOptional = userService.getUserByEmail(loginRequestDto.email);

        if (userOptional == null) {
            return ResponseMessage.error("User not found with email: " + loginRequestDto.email, 404);
        }

        UserEntity user = userOptional;

        // Verificar la contraseña
        if (!passwordEncoder.matches(loginRequestDto.password, user.password)) {
            return ResponseMessage.error("Incorrect password", 401);
        }

        // Generar JWT
        String token = jwtTokenProvider.generateToken(user);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.accessToken = token;
        authResponseDTO.user = userOptional;
        return ResponseMessage.success(authResponseDTO, "Login successful", 1);
    }

    public ResponseMessage<?> checkToken(String token) {
        if (jwtTokenProvider.validateToken(token)) {
            // get user id from token
            String userId = jwtTokenProvider.getUserIdFromToken(token);
            // get user from db
            UserEntity user = userService.getUserById(UUID.fromString(userId));
            if (user == null) {
                return ResponseMessage.error("User not found", 404);
            }
            CheckTokenResponseDto response = new CheckTokenResponseDto();
            response.user = user;
            return ResponseMessage.success(response, "Token is valid", 1);
        } else {
            return ResponseMessage.error("Token is invalid", 0);
        }
    }

    public class CheckTokenResponseDto {
        public UserEntity user;
    }

    public class AuthResponseDTO {
        public String accessToken;
        public UserEntity user;
    }
}

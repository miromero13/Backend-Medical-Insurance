package medical_insurance.backend_medical_insurance.sale.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import medical_insurance.backend_medical_insurance.common.dto.AuthResponseDto;
import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.sale.dto.ClientEmailLoginRequestDto;
import medical_insurance.backend_medical_insurance.sale.dto.ClientLoginRequestDto;
import medical_insurance.backend_medical_insurance.sale.dto.VerifyCodeDto;
import medical_insurance.backend_medical_insurance.sale.repository.ClientRepository;
import medical_insurance.backend_medical_insurance.sale.entity.ClientEntity;
import medical_insurance.backend_medical_insurance.sale.provider.ClientJwtTokenProvider;

@Service
public class ClientAuthService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientJwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${whatsapp.api.url}")
    private String whatsappApiUrl;

    @Value("${whatsapp.instance.id}")
    private String whatsappInstanceId;

    @Value("${whatsapp.access.token}")
    private String whatsappAccessToken;

    // Método para autenticación con email y contraseña
    public ResponseMessage<AuthResponseDto> authenticateWithEmailAndPassword(
            ClientEmailLoginRequestDto loginRequestDto) {
        Optional<ClientEntity> clientOptional = clientRepository.findByEmail(loginRequestDto.email);

        if (clientOptional.isEmpty()) {
            return ResponseMessage.error("Correo electrónico no encontrado", 404);
        }

        ClientEntity client = clientOptional.get();

        // No es necesario validar, pero se comparará la contraseña
        if (!passwordEncoder.matches(loginRequestDto.password, client.password)) {
            return ResponseMessage.error("Contraseña incorrecta", 401);
        }

        // Generar token JWT
        String token = jwtTokenProvider.generateToken(client);

        return ResponseMessage.success(new AuthResponseDto(token), "Inicio de sesión exitoso", 1);
    }

    public ResponseMessage<String> sendVerificationCode(ClientLoginRequestDto clientLoginRequestDto) {
        System.out.println("Buscando número: " + clientLoginRequestDto.phone);
        Optional<ClientEntity> clientOptional = clientRepository.findByPhone(clientLoginRequestDto.phone);

        if (clientOptional.isEmpty()) {
            return ResponseMessage.error("Número de teléfono no encontrado", 404);
        }

        ClientEntity client = clientOptional.get();

        // Generar código de verificación y establecer fecha de creación
        String verificationCode = generateVerificationCode();
        client.verificationCode = verificationCode;
        client.verificationCodeGeneratedAt = LocalDateTime.now(); // Guardar la fecha actual
        clientRepository.save(client); // Guardar cambios en la entidad

        // Intentar enviar el código por WhatsApp
        try {
            sendWhatsAppMessage(clientLoginRequestDto.phone,
                    "Bienvenido de nuevo *" + client.name + "*.\nTu código de verificación es: *" + verificationCode
                            + "*.\nNo compartas este código con nadie.\nVálido por 5 minutos.");
            return ResponseMessage.success("Código de verificación enviado", "Code sent", 1);
        } catch (Exception e) {
            return ResponseMessage.error("No se pudo enviar el código de verificación", 500);
        }
    }

    public ResponseMessage<AuthResponseDto> verifyCode(VerifyCodeDto verifyCodeDto) {
        Optional<ClientEntity> clientOptional = clientRepository.findByPhone(verifyCodeDto.phone);

        if (clientOptional.isEmpty()) {
            return ResponseMessage.error("Número de teléfono no encontrado", 404);
        }

        ClientEntity client = clientOptional.get();

        if (client.verificationCode == null || !client.verificationCode.equals(verifyCodeDto.verificationCode) ||
                client.verificationCodeGeneratedAt.plusMinutes(5).isBefore(LocalDateTime.now())) {
            return ResponseMessage.error("Código de verificación inválido o expirado", 400);
        }

        // Generar token JWT
        String token = jwtTokenProvider.generateToken(client);

        // Limpiar el código de verificación después de su uso
        client.verificationCode = null;
        client.verificationCodeGeneratedAt = null;
        clientRepository.save(client);

        return ResponseMessage.success(new AuthResponseDto(token), "Inicio de sesión exitoso", 1);
    }

    private String generateVerificationCode() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    private void sendWhatsAppMessage(String phoneNumber, String message) {
        RestTemplate restTemplate = new RestTemplate();
        WhatsAppMessageRequest request = new WhatsAppMessageRequest(phoneNumber, "text", message, whatsappInstanceId,
                whatsappAccessToken);

        try {
            restTemplate.postForEntity(whatsappApiUrl, request, String.class);
        } catch (HttpClientErrorException e) {
            // Manejar errores HTTP (por ejemplo, cuando la API de WhatsApp falla)
            e.printStackTrace();
            throw new RuntimeException("Error enviando mensaje de WhatsApp: " + e.getMessage());
        }
    }

    // Clase interna para estructurar el mensaje de la API de WhatsApp
    static class WhatsAppMessageRequest {
        public String number;
        public String type;
        public String message;
        public String instance_id;
        public String access_token;

        public WhatsAppMessageRequest(String number, String type, String message, String instance_id,
                String access_token) {
            this.number = "591" + number;
            this.type = type;
            this.message = message;
            this.instance_id = instance_id;
            this.access_token = access_token;
        }
    }
}

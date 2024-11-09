package medical_insurance.backend_medical_insurance.service_medic.provider;

import medical_insurance.backend_medical_insurance.service_medic.entity.ClientEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;  // Import Keys
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class ClientJwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMillis;

    // Obtener la clave secreta como un SecretKey
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());  // Convertir el secret en un SecretKey
    }

    // Generar el token JWT
    public String generateToken(ClientEntity user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)  // Usar SecretKey para la firma
                .compact();
    }

    // Validar el token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // Usar SecretKey para la validación
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    // Obtener el ID del usuario desde el token
    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // Usar SecretKey para obtener claims
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

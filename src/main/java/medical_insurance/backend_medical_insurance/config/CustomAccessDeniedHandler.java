package medical_insurance.backend_medical_insurance.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {

    // Configura el código de estado como 403
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    // Crea un cuerpo de respuesta personalizado en formato JSON
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("status", HttpServletResponse.SC_FORBIDDEN);
    responseBody.put("error", "Forbidden");
    responseBody.put("message", "No tienes permisos para acceder a este recurso.");
    responseBody.put("path", request.getRequestURI());

    // Serializa el cuerpo a JSON y envía la respuesta
    response.setContentType("application/json");
    response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
  }
}

package medical_insurance.backend_medical_insurance.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    responseBody.put("error", "Unauthorized");
    responseBody.put("message", "No estás autenticado. Por favor, inicia sesión.");
    responseBody.put("path", request.getRequestURI());

    response.setContentType("application/json");
    response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
  }
}

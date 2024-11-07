package medical_insurance.backend_medical_insurance.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Documentación de la API de Ecommerce")
                        .version("0.1.0")
                        .description(
                                "En esta documentación se encuentra la información necesaria para poder interactuar con la API de Ecommerce"))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"))
                .paths(new Paths()
                        .addPathItem("/client/auth/login/email", new PathItem())
                        // .addPathItem("/client/auth/login/whatsapp", new PathItem())
                        .addPathItem("/client/auth/verify", new PathItem())
                        // .addPathItem("/client/auth/logout", new PathItem())
                        .addPathItem("/auth/login", new PathItem())
                        .addPathItem("/public/seeder", new PathItem())
                        .addPathItem("/client", new PathItem())
                        .addPathItem("/client/auth/login/whatsapp", new PathItem())
                        .addPathItem("/files/image", new PathItem())
                        .addPathItem("/files/{filename}", new PathItem())
                        .addPathItem("/files", new PathItem())
                        );
    }
}

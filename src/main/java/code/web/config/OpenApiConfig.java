package code.web.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "springdoc.api-docs.enabled", havingValue = "true")
@OpenAPIDefinition(
    info =
        @Info(
            title = "Device Catalogue API",
            version = "0.0.1",
            description = "API de dispositivos, catálogo y comentarios."))
public class OpenApiConfig {
  @Bean
  OpenAPI deviceOpenApi() {
    return new OpenAPI()
        .components(
            new Components()
                .addSecuritySchemes(
                    "bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("JWT obtenido en POST /api/auth/login.")))
        .info(
            new io.swagger.v3.oas.models.info.Info()
                .title("Device Catalogue API")
                .version("0.0.1")
                .contact(new Contact().name("Engineering Code")));
  }
}

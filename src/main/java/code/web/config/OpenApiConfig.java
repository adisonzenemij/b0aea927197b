package code.web.config;

import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@ConditionalOnProperty(name = "springdoc.api-docs.enabled", havingValue = "true")
@OpenAPIDefinition(info = @Info(title = "Device Catalogue API", version = "0.0.1", description = "API de dispositivos, catálogo y comentarios."))
public class OpenApiConfig {
    private static final String CURRENT_SERVER = "Current Server";
    private static final String GENERATED_SERVER = "Generated Server URL";

    @Bean
    OpenAPI deviceOpenApi(@Value("${spring.application.name}") String applicationName) {
        return new OpenAPI()
            .servers(
                List.of(
                    new Server()
                        .description(CURRENT_SERVER)
                        .url("/" + applicationName),
                    new Server()
                        .description(GENERATED_SERVER)
                        .url("/")))
            .components(
                new Components()
                    .addSecuritySchemes(
                        "bearerAuth",
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .description("JWT obtenido en POST /api/auth/login.")
                        )
                    )
            .info(
                new io.swagger.v3.oas.models.info.Info()
                    .title("Device Catalogue API")
                    .version("0.0.1")
                    .contact(new Contact().name("Engineering Code")));
    }

    @Bean
    OpenApiCustomizer currentRequestServerCustomizer(
        @Value("${spring.application.name}") String applicationName
    ) {
        return openApi -> {
            ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return;
            }

            String serverUrl = currentRequestUrl(attributes.getRequest());
            openApi.setServers(
                List.of(
                    new Server()
                        .description(CURRENT_SERVER)
                        .url("/" + applicationName),
                    new Server()
                        .description(GENERATED_SERVER)
                        .url(serverUrl)));
        };
    }

    private String currentRequestUrl(HttpServletRequest request) {
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null || scheme.isBlank()) {
            scheme = request.getScheme();
        }

        String host = request.getHeader("X-Forwarded-Host");
        if (host != null && !host.isBlank()) {
            return scheme + "://" + host;
        }

        return scheme + "://" + request.getServerName() + port(request, scheme);
    }

    private String port(HttpServletRequest request, String scheme) {
        int port = request.getServerPort();
        boolean defaultHttp = "http".equalsIgnoreCase(scheme) && port == 80;
        boolean defaultHttps = "https".equalsIgnoreCase(scheme) && port == 443;
        return defaultHttp || defaultHttps ? "" : ":" + port;
    }
}

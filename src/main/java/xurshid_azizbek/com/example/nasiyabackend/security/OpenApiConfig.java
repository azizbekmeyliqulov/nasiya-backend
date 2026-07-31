package xurshid_azizbek.com.example.nasiyabackend.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(url = "https://x-nasiya.uz/api", description = "Production Server (HTTPS)"),
                @Server(url = "http://localhost:8080/api", description = "Local Development")
        },
        info = @Info(title = "Nasiya", version = "v1",
                description = "This API just for learning Spring boot features",
                contact = @Contact(name = "Azizbek Java Backend ", url = "https://x-nasiya.uz", email = "admin@gmail.com"),
                license = @License(name = "Apache Foundation", url = "https://apache.org/")
        ),
        security = {
                @SecurityRequirement(name = "Bearer")
        }
)
@SecurityScheme(
        name = "Bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer"
)

public class OpenApiConfig {
}
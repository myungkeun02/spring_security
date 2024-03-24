package org.myungkeun.spring_security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "최명근",
                        email = "audrms3568@gmail.com",
                        url = "http://www.toss.com"
                ),
                description = "Open Api docu",
                title = "명근 API",
                version = "1.0",
                license = @License(
                        name = "최명근",
                        url = "http://www.audrms.com"
                ),
                termsOfService = "termsOfService"
        ),
        servers = {
                @Server(
                        description = "Development",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production",
                        url = "http://www.audrms.com"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        description = "Jwt auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}

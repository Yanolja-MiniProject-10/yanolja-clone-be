package com.ybe.mp10.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
@OpenAPIDefinition(
        servers = {
                @Server(url = "https://ybe-mini.site", description = "Server")
        },
        info = @Info(
                title = "야놀자 미니 프로젝트",
                description = "미니 프로젝트 API",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {
}

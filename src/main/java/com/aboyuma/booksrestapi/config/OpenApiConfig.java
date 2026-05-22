package com.aboyuma.booksrestapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Books REST API",
        version = "1.0.0",
        description = "Simple CRUD REST API using Spring Boot"
    )
)
public class OpenApiConfig {
}

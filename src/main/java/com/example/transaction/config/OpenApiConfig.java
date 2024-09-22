package com.example.transaction.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Microservice Supply",
                description = "This is Api Microservice Supply Second Module"
        )
)
public class OpenApiConfig {
}

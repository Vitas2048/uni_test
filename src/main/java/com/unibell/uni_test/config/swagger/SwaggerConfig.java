package com.unibell.uni_test.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Unibell test API",
                version = "v1.0",
                description = "RESTFUL API for unibell test"
        )
)
public class SwaggerConfig {
}

package com.healthylife.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Healthy Life API")
                        .version("1.0")
                        .description("API documentation for Healthy Life application")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Healthy Life Team")
                                .email("support@healthylife.com")
                                .url("https://healthylife.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
} 
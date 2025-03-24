package com.healthylife.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI healthyLifeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Healthy Life API")
                        .description("Sağlıklı yaşam takip uygulaması API dokümantasyonu")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Healthy Life Ekibi")
                                .email("info@healthylife.com")
                                .url("https://healthylife.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("JWT",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("JWT kimlik doğrulama token'ı. 'Bearer' öneki ile birlikte gönderin.")));
    }
} 
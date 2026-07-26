package com.divyaksh.cap.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI codingAssessmentAPI() {

        final String securitySchemeName = "Bearer Authentication";
        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(
                        new Info()
                                .title("Coding Assessment Platform API")
                                .version("1.0")
                                .description("""
                                        REST APIs for an Online Coding Assessment Platform.
                                        
                                        Features:
                                        • Authentication
                                        • Contest Management
                                        • Problem Management
                                        • Test Cases
                                        • Contest Registration
                                        • Code Submission
                                        • Leaderboard
                                        """)
                                .contact(
                                        new Contact()
                                                .name("Divyaksh Chachan")
                                                .email("divyaksh3@gmail.com")
                                                .url("https://github.com/DivyakshChachan")
                                )
                                .license(
                                        new License()
                                                .name("MIT")
                                )
                );
    }
}
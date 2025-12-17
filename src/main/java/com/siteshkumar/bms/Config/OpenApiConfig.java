package com.siteshkumar.bms.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI blogApi(){
        return new OpenAPI()
                    .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                    .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                            .name("Authorization")
                            .type(SecurityScheme.Type. HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            ))
                    .info(new Info()
                            .title("Blog Management System API")
                            .description("Rest APIs for Post and Comments with JWT Security")
                            .version("1.0.0"));
    }
}

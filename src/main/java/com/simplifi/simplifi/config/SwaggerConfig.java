package com.simplifi.simplifi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server; // 👈 required import
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simplifi API Documentation")
                        .version("v1.0")
                        .description("This is the backend API documentation for the Simplifi system.")
                        .contact(new Contact()
                                .name("Abdullah Imtiaz")
                                .email("abdullahimtiaz31@gmail.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server")));
    }

}

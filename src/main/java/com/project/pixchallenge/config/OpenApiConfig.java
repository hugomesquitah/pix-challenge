package com.project.pixchallenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private final String appVersion;

    public OpenApiConfig(@Value("${app.version}") String appVersion) {
        this.appVersion = appVersion;
    }

    @Bean
    public OpenAPI springOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pix Challenge API Documentation")
                        .description("API documentation to manage keys")
                        .version(appVersion));

    }
}

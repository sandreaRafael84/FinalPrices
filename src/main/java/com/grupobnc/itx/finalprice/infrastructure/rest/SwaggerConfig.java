package com.grupobnc.itx.finalprice.infrastructure.rest;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean public OpenAPI customOpenAPI() {
        return new OpenAPI() .info(new Info().title("Product API")
                .description("API for managing products")
                .version("v1.0"));
    }
}



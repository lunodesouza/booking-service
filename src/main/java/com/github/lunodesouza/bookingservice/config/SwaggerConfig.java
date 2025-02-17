package com.github.lunodesouza.bookingservice.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Property Management API")
                        .version("1.0")
                        .description("API for managing property bookings and blocks")
                        .contact(new Contact()
                                .name("API Support")
                                .email("lunodesouza@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }

    @Bean
    public GroupedOpenApi blocksApi() {
        return GroupedOpenApi.builder()
                .group("blocks")
                .pathsToMatch("/v1/blocks/**")
                .build();
    }

    @Bean
    public GroupedOpenApi bookingsApi() {
        return GroupedOpenApi.builder()
                .group("bookings")
                .pathsToMatch("/v1/bookings/**")
                .build();
    }
}

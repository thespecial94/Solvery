package ru.magomed.solverycourse.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private final BuildProperties buildProperties;

    public OpenApiConfig(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Schedule Trains API")
                        .version(buildProperties.getVersion())
                        .description(buildProperties.getArtifact() + " - API Swagger documentation")
                        .license(new License().name("Лицензия проекта").url("https://springdoc.org/"))
                        .contact(new Contact()
                                .name("Магомед Султаенахмедов"))

                );
    }
}

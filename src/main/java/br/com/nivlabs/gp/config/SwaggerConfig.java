package br.com.nivlabs.gp.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.owner.url}")
    private String ulrOwner;
    @Value("${swagger.owner.name}")
    private String nameOwner;

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("springshop-public")
                .pathsToMatch("/public/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(nameOwner + " Service Store")
                        .description("Este é o catálogo de serviços RESTFUL da " + nameOwner + ", para saber mais sobre acesse: ["
                                + nameOwner + "](" + ulrOwner + ").")
                        .version("v0.0.1")
                        .license(new License().name("MIT")
                                .url("https://github.com/NiV-Labs/gestao-de-prontuario-ui/blob/master/LICENSE")))
                .externalDocs(new ExternalDocumentation()
                        .description("NivLabs")
                        .url("https://www.nivlabs.com.br"));
    }

}
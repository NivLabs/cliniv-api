package br.com.nivlabs.cliniv.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.owner.url}")
    private String ulrOwner;
    @Value("${swagger.owner.name}")
    private String nameOwner;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(nameOwner + " Service Store")
                        .description("Este é o catálogo de serviços RESTFUL da " + nameOwner + ", para saber mais sobre acesse: ["
                                + nameOwner + "](" + ulrOwner + ").")
                        .version("v1.0.0")
                        .license(new License().name("MIT")
                                .url("https://github.com/NiV-Labs/cliniv-ui/blob/master/LICENSE")))
                .externalDocs(new ExternalDocumentation()
                        .description("NivLabs")
                        .url("https://www.nivlabs.com.br"));
    }

}
package br.com.nivlabs.gp.config.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configurações de perfis de propriedades para banco de dados
 * 
 * @author viniciosarodrigues
 *
 */
@Configuration
@ConfigurationProperties("spring.datasource")
public class DatabaseProfileConfiguration {

    @Profile("prod")
    @Bean
    public String getHerokuDb() {
        return "CONEXÃO DE BANCO DE DADOS DE PRODUÇÃO!";
    }

    @Profile("dev")
    @Bean
    public String getDevDb() {
        return "Conexão de banco de dados de desenvolvimento ou homologação";
    }

}

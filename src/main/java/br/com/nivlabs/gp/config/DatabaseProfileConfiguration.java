package br.com.nivlabs.gp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DatabaseProfileConfiguration {

    @Profile("prod")
    @Bean
    public String getHerokuDb() {
        return "DB Connection for Production deploy";
    }

    @Profile("dev")
    @Bean
    public String getDevDb() {
        return "DB Connection for Development deploy";
    }

}

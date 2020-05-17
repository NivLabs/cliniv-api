package br.com.tl.gdp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.datasource.url")
public class DatabaseProfileConfiguration {

    @Profile("heroku")
    @Bean
    public String getHerokuDb() {
        return "DB Connection for HEROKU deploy";
    }

    @Profile("dev")
    @Bean
    public String getDevDb() {
        return "DB Connection for DEV deploy";
    }

    @Profile("local")
    @Bean
    public String getLocalDb() {
        return "DB Connection for LOCAL deploy";
    }
}

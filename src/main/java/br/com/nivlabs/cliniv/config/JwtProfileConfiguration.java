package br.com.nivlabs.cliniv.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("jwt.secret")
public class JwtProfileConfiguration {

    @Profile("heroku")
    @Bean
    public String getHerokuJWT() {
        return "JWT settings for HEROKU deploy";
    }

    @Profile("dev")
    @Bean
    public String getDevJWT() {
        return "JWT settings for DEV deploy";
    }

    @Profile("local")
    @Bean
    public String getLocalJWT() {
        return "JWT settings for LOCAL deploy";
    }
}

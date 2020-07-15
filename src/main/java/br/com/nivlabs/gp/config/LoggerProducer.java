package br.com.nivlabs.gp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerProducer {

    @Bean
    public Logger getLogger(InjectionPoint p) {
        return LoggerFactory.getLogger(p.getClass().getCanonicalName());
    }
}
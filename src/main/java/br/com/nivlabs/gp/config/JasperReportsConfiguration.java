package br.com.nivlabs.gp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.nivlabs.gp.report.JasperReportsCreator;

@Configuration
public class JasperReportsConfiguration {

    @Bean
    public JasperReportsCreator createJasperReport() {
        return new JasperReportsCreator();
    }
}

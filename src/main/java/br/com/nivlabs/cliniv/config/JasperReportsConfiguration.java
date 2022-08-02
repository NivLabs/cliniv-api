package br.com.nivlabs.cliniv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.nivlabs.cliniv.report.JasperReportsCreator;

/**
 * Provider de configuração do JasperReports
 * 
 * @author viniciosarodrigues
 *
 */
@Configuration
public class JasperReportsConfiguration {

    /**
     * Instância do JasperReportsCreator
     * 
     * @return
     */
    @Bean
    public JasperReportsCreator createJasperReport() {
        return new JasperReportsCreator();
    }
}

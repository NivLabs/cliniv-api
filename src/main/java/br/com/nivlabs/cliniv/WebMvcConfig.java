package br.com.nivlabs.cliniv;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.nivlabs.cliniv.config.db.TenantInterceptor;

/**
 * Configurações da aplicação
 * 
 * @author viniciosarodrigues
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;

    /**
     * Adicionando tenant às configurações da aplicação
     * 
     * @param tenantInterceptor Interceptador de troca de schemas
     */
    public WebMvcConfig(TenantInterceptor tenantInterceptor) {
        this.tenantInterceptor = tenantInterceptor;
    }

    /**
     * Interceptador de requisições para troca de schema
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor);
    }
}

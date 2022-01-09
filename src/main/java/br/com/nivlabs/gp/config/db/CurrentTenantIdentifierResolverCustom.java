package br.com.nivlabs.gp.config.db;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

/**
 * Identificador customizado de Schema corrente
 * 
 * @author viniciosarodrigues
 * @since 08/01/2022
 *
 */
@Component
public class CurrentTenantIdentifierResolverCustom implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantContext.getCurrentTenant();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}

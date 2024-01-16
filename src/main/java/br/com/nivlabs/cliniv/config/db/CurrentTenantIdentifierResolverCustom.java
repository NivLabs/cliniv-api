package br.com.nivlabs.cliniv.config.db;

import br.com.nivlabs.cliniv.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

/**
 * Identificador customizado de Schema corrente
 *
 * @author viniciosarodrigues
 * @since 08/01/2022
 */
@Component
public class CurrentTenantIdentifierResolverCustom implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        return StringUtils.isNullOrEmpty(TenantContext.getCurrentTenant()) ? TenantContext.DEFAULT_TENANT : TenantContext.getCurrentTenant();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}

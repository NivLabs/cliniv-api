package br.com.nivlabs.cliniv.config.db;

/**
 * Contexto de conexão
 *
 * @author viniciosarodrigues
 * @since 08/01/2022
 */
public class TenantContext {

    final public static String DEFAULT_TENANT = "cliniv";

    private static final ThreadLocal<String> currentTenant = ThreadLocal.withInitial(() -> DEFAULT_TENANT);

    public static void setCurrentTenant(String tenant) {
        TenantContext.currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return TenantContext.currentTenant.get();
    }

    public static void clear() {
        TenantContext.currentTenant.remove();
    }
}

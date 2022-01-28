package br.com.nivlabs.gp.config.db;

/**
 * Contexto de conexão
 * 
 * @author viniciosarodrigues
 * @since 08/01/2022
 *
 */
public class TenantContext {

    final public static String DEFAULT_TENANT = "gpdefault";

    private static final ThreadLocal<String> currentTenant = ThreadLocal.withInitial(() -> DEFAULT_TENANT);

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
}

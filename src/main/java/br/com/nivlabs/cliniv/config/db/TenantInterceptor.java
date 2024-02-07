package br.com.nivlabs.cliniv.config.db;

import br.com.nivlabs.cliniv.util.SecurityContextUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Interceptador de requisições para criação de conexão com esquema de dados
 *
 * @author viniciosarodrigues
 * @since 08/01/2022
 */
@Component
@Order(1)
public class TenantInterceptor extends OncePerRequestFilter {

    @Autowired
    private Logger logger;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tenantName = request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY);
        if (!TenantContext.getCurrentTenant().equals(tenantName)) {
            logger.info("Requisição com troca de tenant recebida :: {} -> {}", TenantContext.getCurrentTenant(), tenantName);
            TenantContext.setCurrentTenant(tenantName);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}

package br.com.nivlabs.cliniv.config;

import br.com.nivlabs.cliniv.config.security.JwtUtils;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;
import br.com.nivlabs.cliniv.util.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Classe CorsFilter.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 18 de out de 2019
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsConfigurationFilter implements Filter {

    private final JwtUtils jwtUtils;

    @Autowired
    public CorsConfigurationFilter(final JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Credentials", "true");

        configMDC(req, resp);

        if ("OPTIONS".equals(req.getMethod())) {
            resp.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
            resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-Customer-Id, X-Operation-Id");
            resp.setHeader("Access-Control-Max-Age", "3600");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, resp);
        }
    }

    void configMDC(HttpServletRequest req, HttpServletResponse resp) {
        MDC.clear();

        final var operationId = req.getHeader(SecurityContextUtil.OPERATION_ID_HEADER_KEY) != null ? req.getHeader(SecurityContextUtil.OPERATION_ID_HEADER_KEY).trim() : null;
        final var customerId = req.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY) != null ? req.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY).trim() : null;

        MDC.put(SecurityContextUtil.OPERATION_ID_HEADER_KEY, operationId);
        MDC.put(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY, customerId);

        final var authorizationHeader = req.getHeader("Authorization");
        final var token = StringUtils.isNullOrEmpty(authorizationHeader) ? "" : authorizationHeader.replace("Bearer ", "");

        if (!StringUtils.isNullOrEmpty(token)) {
            if (jwtUtils.isValidToken(token, customerId)) {
                final String userName = jwtUtils.getUserName(token, customerId);
                if (!StringUtils.isNullOrEmpty(userName)) {
                    MDC.put("Autenticated-User", userName);
                }
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // Não é necessário implementar
    }

    @Override
    public void destroy() {
        // Não é necessário implementar
    }
}
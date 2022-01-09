package br.com.nivlabs.gp.config.db;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptador de requisições para criação de conexão com esquema de dados
 * 
 * @author viniciosarodrigues
 * @since 08/01/2022
 *
 */
@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final String CUSTOMER_ID_HEADER = "CUSTOMER_ID";
    @Autowired
    private Logger logger;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        Optional.ofNullable(req.getHeader(CUSTOMER_ID_HEADER)).ifPresent(TenantContext::setCurrentTenant);
        logger.info("Iniciando troca de schema para :: {}", req.getHeader(CUSTOMER_ID_HEADER));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        TenantContext.clear();
    }
}

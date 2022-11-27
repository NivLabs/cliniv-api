package br.com.nivlabs.cliniv.util;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.nivlabs.cliniv.config.security.UserOfSystem;

/**
 * 
 * Utilitário de contexto de segurança
 *
 * @author viniciosarodrigues
 * @since 16-09-2021
 *
 */
public class SecurityContextUtil {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    private SecurityContextUtil() {
    }

    public static UserOfSystem getAuthenticatedUser() {
        try {
            return (UserOfSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
package br.com.nivlabs.cliniv.util;

import java.util.stream.Collectors;

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
    
    public static final String CUSTOMER_ID_HEADER_KEY = "x-cutomer-id";

    private SecurityContextUtil() {
    }

    /**
     * Retorna o usuário logado no sistema
     * 
     * @return Usuário logado
     */
    public static UserOfSystem getAuthenticatedUser() {
        try {
            return (UserOfSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retorna se o usuário logado é usuário administrador ou não
     * 
     * @return TRUE - Administrado | FALSE - Não administrador
     */
    public static boolean isAdmin() {
        return !getAuthenticatedUser().getAuthorities().stream()
                .filter(role -> role.getAuthority().equals(SecurityContextUtil.ROLE_ADMIN))
                .collect(Collectors.toList()).isEmpty();
    }
}
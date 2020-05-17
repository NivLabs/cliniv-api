package br.com.tl.gdp.service.security;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.tl.gdp.config.security.UserOfSystem;

/**
 * Classe UserOfSystemService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
public class UserOfSystemService {
    public static UserOfSystem authenticated() {
        try {
            return (UserOfSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
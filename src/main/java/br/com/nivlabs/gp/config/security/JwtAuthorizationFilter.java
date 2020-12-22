package br.com.nivlabs.gp.config.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.nivlabs.gp.exception.HttpException;

/**
 * Classe AuthorizationFilter.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtils jwtUtils;
    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
            UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {

        String authorizationHeader = req.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            UsernamePasswordAuthenticationToken auth = null;
            try {
                auth = getAuthentication(authorizationHeader.substring(7));
            } catch (HttpException e) {
                if (e.getStatus() == HttpStatus.UNAUTHORIZED) {
                    resp.setStatus(401);
                    resp.setContentType("application/json;charset=utf-8");
                    resp.getWriter().append(json(req, e));
                    return;
                }
            }

            if (auth != null)
                SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(req, resp);
    }

    private String json(HttpServletRequest req, HttpException e) {
        String jsonResponse = """
                {
                   "timestamp": DATE,
                   "status": 401,
                   "error": "ERROR",
                   "message": "MESSAGE",
                   "path": "PATH"
                }
                """;
        jsonResponse = jsonResponse.replace("DATE", String.valueOf(new Date().getTime()));
        jsonResponse = jsonResponse.replace("ERROR", "Não autorizado");
        jsonResponse = jsonResponse.replace("MESSAGE", e.getMessage());
        jsonResponse = jsonResponse.replace("PATH", req.getServletPath());

        return jsonResponse;
    }

    /**
     * Pega informações do token
     * 
     * @param token
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtUtils.isValidToken(token)) {
            String userName = jwtUtils.getUserName(token);
            UserDetails user = userDetailsService.loadUserByUsername(userName);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }
}
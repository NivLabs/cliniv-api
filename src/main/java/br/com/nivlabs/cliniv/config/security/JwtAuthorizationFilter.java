package br.com.nivlabs.cliniv.config.security;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.nivlabs.cliniv.config.db.TenantContext;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.repository.UserRepository;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;

/**
 * Classe AuthorizationFilter.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Order(2)
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtils jwtUtils;
    private UserRepository userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
            UserRepository userDao) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {

        String authorizationHeader = req.getHeader("Authorization");
        String customerIdHeader = req.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            UsernamePasswordAuthenticationToken auth = null;
            try {
                if (customerIdHeader == null || customerIdHeader.isEmpty()) {
                    throw new HttpException(HttpStatus.UNAUTHORIZED, "Cabeçalho de identificação do cliente não enviado");
                }
                Optional.ofNullable(req.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY)).ifPresent(TenantContext::setCurrentTenant);
                auth = getAuthentication(authorizationHeader.substring(7), customerIdHeader);
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

    /**
     * Converte os erros tratador Http da camada de utilidades de Token para objetos de resposta do filtro
     * 
     * @param req Requisição http
     * @param e Exception lançada pelo utilitário
     * @return Json em String para setar no Stream da resposta do filtro
     */
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
        return ("" + jsonResponse)
                .replace("DATE", String.valueOf(new Date().getTime()))
                .replace("ERROR", "Não autorizado")
                .replace("MESSAGE", e.getMessage())
                .replace("PATH", req.getServletPath());
    }

    /**
     * Pega informações do token
     * 
     * @param token
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String token, String customerId) {
        if (jwtUtils.isValidToken(token, customerId)) {
            String userName = jwtUtils.getUserName(token);

            var user = userDetailsService.findByUserName(userName.trim().toUpperCase())
                    .orElseThrow(() -> new HttpException(HttpStatus.UNAUTHORIZED, "Não autorizado"));

            var userOfSystem =
                             new UserOfSystem(user.getUserName(), user.getPassword(), user.getPerson(), !user.isActive(), user.getRoles(),
                                     customerId);

            return new UsernamePasswordAuthenticationToken(userOfSystem, null, userOfSystem.getAuthorities());
        }
        return null;
    }
}
package br.com.nivlabs.cliniv.config.security;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.repository.UserRepository;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;
import br.com.nivlabs.cliniv.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Classe AuthorizationFilter.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 15 de set de 2019
 */
@Order(2)
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final UserRepository userDetailsService;
    private final List<String> listOfAllMatchs;
    private final List<String> listOfGetMatchs;
    private final List<String> listOfPostMatchs;

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            UserRepository userDao,
            final List<String> listOfAllMatchs,
            final List<String> listOfGetMatchs,
            final List<String> listOfPostMatchs) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDao;
        this.listOfAllMatchs = listOfAllMatchs;
        this.listOfGetMatchs = listOfGetMatchs;
        this.listOfPostMatchs = listOfPostMatchs;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        boolean requiredToken = true;
        try {
            requiredToken = validateTokenRequiredByURI(request);
        } catch (HttpException e) {
            setServletResponseException(request, response, e);
            return;
        }

        if (requiredToken) {
            final var tupleAuth = this.getRequestHeaders(request);
            UsernamePasswordAuthenticationToken auth = null;
            try {
                auth = getAuthentication(tupleAuth.getFirst(), tupleAuth.getSecond());
            } catch (HttpException e) {
                setServletResponseException(request, response, e);
                return;
            }
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);

    }

    private void setServletResponseException(HttpServletRequest request, HttpServletResponse response, HttpException e) throws IOException {
        SecurityContextHolder.clearContext();
        response.setStatus(e.getStatus().value());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().append(json(request, e));
    }

    private boolean validateTokenRequiredByURI(HttpServletRequest request) {
        final String requestURI = request.getRequestURI();
        boolean tokenRequired = true;

        if (request.getMethod().equals("GET")) {
            tokenRequired = checkUriAccessPermission(requestURI, tokenRequired, listOfGetMatchs);
        } else if (request.getMethod().equals("POST")) {
            tokenRequired = checkUriAccessPermission(requestURI, tokenRequired, listOfPostMatchs);
        }
        if (tokenRequired) {
            tokenRequired = checkUriAccessPermission(requestURI, tokenRequired, listOfAllMatchs);
        }
        final var tuple = getRequestHeaders(request);
        if (tokenRequired && getAuthentication(tuple.getFirst(), tuple.getSecond()) == null) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Cabeçalho de autenticação não informado!");
        }
        return tokenRequired;
    }

    private boolean checkUriAccessPermission(final String requestURI, boolean tokenRequired, List<String> URIs) {
        for (String uri : URIs) {
            if (uri.equals(requestURI)) {
                tokenRequired = false;
                break;
            } else if (uri.contains("/**") && requestURI.startsWith(uri.replace("/**", ""))) {
                tokenRequired = false;
                break;
            } else if (uri.contains("/*")) {
                String[] uriSplit = uri.split("/");
                String[] requestUriSplit = requestURI.split("/");
                if (uriSplit.length == requestUriSplit.length) {
                    for (int position = 0; position < uriSplit.length; position++) {
                        if (!uriSplit[position].equals("*") && !uriSplit[position].equals(requestUriSplit[position])) {
                            tokenRequired = true;
                            continue;
                        }
                        tokenRequired = false;
                    }
                }
            }
        }
        return tokenRequired;
    }

    /**
     * Converte os erros tratador Http da camada de utilidades de Token para objetos de resposta do filtro
     *
     * @param req Requisição http
     * @param e   Exception lançada pelo utilitário
     * @return Json em String para setar no Stream da resposta do filtro
     */
    private String json(HttpServletRequest req, HttpException e) {
        return """
                {
                   "timestamp": DATE,
                   "status": 401,
                   "error": "ERROR",
                   "message": "MESSAGE",
                   "path": "PATH"      
                }
                """
                .replace("DATE", String.valueOf(new Date().getTime()))
                .replace("ERROR", "Não autorizado")
                .replace("MESSAGE", e.getMessage())
                .replace("PATH", req.getServletPath());
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token, String customerId) {
        if (jwtUtils.isValidToken(token, customerId)) {
            String userName = jwtUtils.getUserName(token, customerId);

            var user = userDetailsService.findByUserName(userName.trim().toUpperCase())
                    .orElseThrow(() -> new HttpException(HttpStatus.UNAUTHORIZED, "Não autorizado"));

            var userOfSystem =
                    new UserOfSystem(user.getUserName(), user.getPassword(), user.getPerson(), !user.isActive(), user.getRoles(),
                            customerId);

            return new UsernamePasswordAuthenticationToken(userOfSystem, null, userOfSystem.getAuthorities());
        }
        return null;
    }

    private Pair<String, String> getRequestHeaders(HttpServletRequest request) {
        final String customerIdHeader = request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY);
        final String authorizationHeader = request.getHeader("Authorization");
        final String token = StringUtils.isNullOrEmpty(authorizationHeader) ? "" : authorizationHeader.replace("Bearer ", "");

        return Pair.of(token, StringUtils.isNullOrEmpty(customerIdHeader) ? "" : customerIdHeader);
    }
}
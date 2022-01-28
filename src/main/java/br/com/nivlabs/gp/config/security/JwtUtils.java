package br.com.nivlabs.gp.config.security;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Role;
import br.com.nivlabs.gp.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Classe JwtUtils.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Component
public class JwtUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(UserOfSystem user) {
        return Jwts.builder().setClaims(user.getInfo()).setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

    /**
     * Método que valida se o token é válido
     * 
     * @param token
     * @return
     */
    public boolean isValidToken(String token, String customerIdFromHeader) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String customerId = claims.get("customerId", String.class);
            String userName = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date dateNow = new Date(System.currentTimeMillis());

            if (userName != null && expirationDate != null && dateNow.before(expirationDate) && !StringUtils.isNullOrEmpty(customerId)
                    && customerId.equals(customerIdFromHeader)) {
                return true;
            }
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (SignatureException ex) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Assinatura do token inválida!");
        } catch (MalformedJwtException ex) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Token inválido!");
        } catch (ExpiredJwtException ex) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Token expirado!");
        } catch (UnsupportedJwtException ex) {
            logger.error("Erro de JWT não suportado: ", ex);
            return null;
        } catch (IllegalArgumentException ex) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "As claims JWT estão vazias");
        }
    }

    public String getUserName(String token) {
        Claims claims = getClaims(token);
        if (claims != null)
            return claims.getSubject();
        return null;
    }

    public List<Role> getRoles() {
        // TODO Auto-generated method stub
        return null;
    }
}
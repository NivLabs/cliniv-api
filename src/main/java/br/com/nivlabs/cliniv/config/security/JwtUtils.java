package br.com.nivlabs.cliniv.config.security;

import br.com.nivlabs.cliniv.ApplicationMain;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Classe JwtUtils.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 15 de set de 2019
 */
@Component
public class JwtUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;

    public String generateToken(UserOfSystem user) {
        final Key key = Keys.hmacShaKeyFor((user.getInfo().get(UserOfSystem.CUSTOMER_ID) + secret).getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(user.getInfo())
                .subject(user.getUsername())
                .expiration(this.getDateWithTimezone(ApplicationMain.AMERICA_SAO_PAULO, expiration))
                .signWith(key)
                .compact();
    }

    public Date getDateWithTimezone(final String timeZone, final Integer plusMinutes) {
        ZonedDateTime utc = ZonedDateTime.now(ZoneId.of(timeZone)).plusMinutes(plusMinutes);
        return Date.from(utc.toInstant());
    }

    public boolean isValidToken(final String token, final String customerIdFromHeader) {
        if (!StringUtils.isNullOrEmpty(token)) {
            Claims claims = getClaims(token, customerIdFromHeader);
            if (claims != null) {
                String customerId = claims.get("customerId", String.class);
                String userName = claims.getSubject();
                Date expirationDate = claims.getExpiration();
                Date dateNow = new Date(System.currentTimeMillis());

                return userName != null && expirationDate != null && dateNow.before(expirationDate) && !StringUtils.isNullOrEmpty(customerId)
                        && customerId.equals(customerIdFromHeader);
            }
        }
        return false;
    }

    private Claims getClaims(final String token, final String customerId) {
        final var key = Keys.hmacShaKeyFor((customerId + secret).getBytes(StandardCharsets.UTF_8));
        try {
            JwtParser parser = Jwts.parser().verifyWith(key).build();
            return parser.parseSignedClaims(token).getPayload();
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

    public String getUserName(final String token, final String customerId) {
        Claims claims = getClaims(token, customerId);
        if (claims != null)
            return claims.getSubject();
        return null;
    }
}
package br.com.nivlabs.gp.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.nivlabs.gp.repository.UserRepository;

/**
 * Classe SecurityConfig.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userDao;

    private static final String[] PUBLIC_MATCHES = {"/v2/**", "/webjars/**", "/swagger-ui.html",
                                                    "/swagger-resources/**"};

    private static final String[] PUBLIC_MATCHES_GET = {"/server/", "/server", "/actuator/**", "/status", "/dashboard"};

    private static final String[] PUBLIC_MATCHES_POST = {"/auth/forgot/**", "/auth"};

    /**
     * Configurações gerais de segurança da API
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains("test"))
            http.headers().frameOptions().disable();

        http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHES_GET).permitAll()
                .antMatchers(PUBLIC_MATCHES_POST).permitAll()
                .antMatchers(PUBLIC_MATCHES).permitAll().anyRequest()
                .authenticated();

        http.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtUtils, userDao));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Configura CORS
     * 
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configCors = new CorsConfiguration().applyPermitDefaultValues();
        configCors.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        configCors.setAllowedHeaders(Arrays.asList("*"));
        configCors.setAllowedOrigins(Arrays.asList("*"));
        source.registerCorsConfiguration("/**", configCors);
        return source;
    }

    /**
     * Define a estratégia de criptografia da API
     * 
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
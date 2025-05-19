package br.com.nivlabs.cliniv.config.security;

import br.com.nivlabs.cliniv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final Environment env;
    private final JwtUtils jwtUtils;
    private final UserRepository userDao;
    private static final String[] PUBLIC_MATCHES = {
            "/v2/**",
            "/webjars/**",
            "/swagger-ui/**",
            "/api-docs/**",
            "/swagger-resources/**"
    };

    private static final String[] PUBLIC_MATCHES_GET = {
            "/server/",
            "/server",
            "/actuator/**",
            "/status",
            "/customer/confirm/**"
    };

    private static final String[] PUBLIC_MATCHES_POST = {
            "/auth/forgot/**",
            "/auth",
            "/customer",
            "/patient/public"
    };

    @Autowired
    public SecurityConfig(final AuthenticationConfiguration authenticationConfiguration, final Environment env, final JwtUtils jwtUtils, final UserRepository userDao) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.env = env;
        this.jwtUtils = jwtUtils;
        this.userDao = userDao;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configurações gerais de segurança da API
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);


        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, PUBLIC_MATCHES_POST)
                .permitAll()
                .requestMatchers(HttpMethod.GET, PUBLIC_MATCHES_GET)
                .permitAll()
                .requestMatchers(PUBLIC_MATCHES)
                .permitAll()
                .anyRequest().authenticated());

        http.addFilterBefore(
                new JwtAuthorizationFilter(
                        authenticationManager(authenticationConfiguration),
                        jwtUtils,
                        userDao,
                        Arrays.asList(PUBLIC_MATCHES),
                        Arrays.asList(PUBLIC_MATCHES_GET),
                        Arrays.asList(PUBLIC_MATCHES_POST)),
                UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    /**
     * Define a estratégia de criptografia da API
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
package br.com.ft.gdp.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.ft.gdp.models.domain.Role;

/**
 * Classe UserOfSystem.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
public class UserOfSystem implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = 4402147670971545592L;

    private String username;
    private String password;
    private Boolean isExpired;

    private Collection<? extends GrantedAuthority> authorities;

    public UserOfSystem(String username, String password, Boolean isExpired,
            List<Role> roles) {
        super();
        this.username = username;
        this.password = password;
        this.isExpired = !isExpired;
        this.authorities = roles.stream().map(x -> new SimpleGrantedAuthority(x.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isExpired;
    }

    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<>();
        List<String> permissions = new ArrayList<>();
        this.authorities.iterator().forEachRemaining(item -> permissions.add(item.getAuthority()));
        info.put("user", this.username);
        info.put("authorized", permissions);
        return info;
    }
}

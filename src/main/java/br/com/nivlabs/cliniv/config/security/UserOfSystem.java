package br.com.nivlabs.cliniv.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.nivlabs.cliniv.models.domain.Person;
import br.com.nivlabs.cliniv.models.domain.Role;

/**
 * Classe UserOfSystem.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 15 de set de 2019
 */
public class UserOfSystem implements UserDetails {

    public static final String INFO_AUTHORIZED = "authorized";
    public static final String INFO_PERSON_NAME = "personName";
    public static final String INFO_USER_NAME = "user";
    public static final String CUSTOMER_ID = "customerId";

    private final String username;
    private final String password;
    private final String personName;
    private final Boolean isExpired;
    private final String customerId;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserOfSystem(String username, String password, Person person, Boolean isExpired, List<Role> roles, String customerId) {
        super();
        this.personName = getPersonName(person);
        this.username = username;
        this.password = password;
        this.isExpired = isExpired;
        this.authorities = roles.stream().map(x -> new SimpleGrantedAuthority(x.getName()))
                .collect(Collectors.toList());
        this.customerId = customerId;
    }

    private String getPersonName(Person person) {
        return person.getFullName();
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

    public String getPersonName() {
        return this.personName;
    }

    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<>();
        List<String> permissions = new ArrayList<>();
        this.authorities.iterator().forEachRemaining(item -> permissions.add(item.getAuthority()));
        info.put(INFO_USER_NAME, this.username);
        info.put(INFO_AUTHORIZED, permissions);
        info.put(INFO_PERSON_NAME, this.personName);
        info.put(CUSTOMER_ID, this.customerId);
        return info;
    }
}

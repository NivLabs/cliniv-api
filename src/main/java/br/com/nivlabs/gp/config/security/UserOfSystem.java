package br.com.nivlabs.gp.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.Role;
import br.com.nivlabs.gp.util.StringUtils;

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

    public static final String INFO_PERSON_NAME = "personName";
    public static final String INFO_USER_NAME = "user";

    private String username;
    private String password;
    private String personName;
    private Boolean isExpired;

    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Cria o usuário do sistema
     * 
     * @param username
     * @param password
     * @param person
     * @param isExpired
     * @param roles
     */
    public UserOfSystem(String username, String password, Person person, Boolean isExpired, List<Role> roles) {
        super();
        this.personName = getPersonName(person);
        this.username = username;
        this.password = password;
        this.isExpired = !isExpired;
        this.authorities = roles.stream().map(x -> new SimpleGrantedAuthority(x.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Constrói o nome completo do usuário logado
     * 
     * @param person
     * @return
     */
    private String getPersonName(Person person) {
        StringBuilder fullname = new StringBuilder();
        if (!StringUtils.isNullOrEmpty(person.getFirstName()))
            fullname.append(person.getFirstName());
        if (!StringUtils.isNullOrEmpty(person.getFirstName())) {
            fullname.append(" ");
            fullname.append(person.getLastName());
        }
        return new String(fullname);
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

    /**
     * Constrói as informações do token de acesso
     * 
     * @return
     */
    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<>();
        List<String> permissions = new ArrayList<>();
        this.authorities.iterator().forEachRemaining(item -> permissions.add(item.getAuthority()));
        info.put("user", this.username);
        info.put("authorized", permissions);
        info.put("personName", personName);
        return info;
    }
}

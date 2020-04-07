package br.com.ft.gdp.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.config.security.UserOfSystem;
import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.UserApplication;
import br.com.ft.gdp.repository.UserRepository;

/**
 * Classe UserDetailServiceImpl.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApplication user = userRepository.findByUserName(username).orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Username: " + username + ", tipo " + UserApplication.class.getName()));
        return new UserOfSystem(user.getUserName(), user.getPassword(), user.isActive(), user.getRoles());
    }
}
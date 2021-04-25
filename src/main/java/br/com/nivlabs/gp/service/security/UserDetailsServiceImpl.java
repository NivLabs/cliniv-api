package br.com.nivlabs.gp.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.config.security.UserOfSystem;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.UserApplication;
import br.com.nivlabs.gp.repository.UserRepository;

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
        UserApplication user = userRepository.findByUserName(username)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        "Usuário não encontrado! Username: " + username + ", tipo " + UserApplication.class.getName()));
        return new UserOfSystem(user.getUserName(), user.getPassword(), user.getPerson(), user.isActive(), user.getRoles());
    }
    
    public static void main(String[] args) {
        var enc = new BCryptPasswordEncoder();
        var s1 = enc.encode("admin");
        var s2 = enc.encode("admin");
        
        System.out.println(enc.matches("admin", s2));
        // $2a$10$iVFuX3xsA9Kxus.kl1WAo.7lcjeQVBKdE9rvuPhQ4udGlKbV/JLgO

    }
}
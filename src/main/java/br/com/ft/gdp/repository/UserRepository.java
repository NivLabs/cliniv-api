package br.com.ft.gdp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ft.gdp.models.domain.UserApplication;

/**
 * Classe UserRepository.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Repository
public interface UserRepository extends JpaRepository<UserApplication, Long> {

    public Optional<UserApplication> findByUsername(String username);

    /**
     * @param usernameOrEmail
     * @return
     */
    public Optional<UserApplication> findByUsernameOrEmail(String username, String email);
}

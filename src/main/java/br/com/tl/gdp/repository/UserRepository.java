package br.com.tl.gdp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tl.gdp.models.domain.UserApplication;
import br.com.tl.gdp.repository.custom.user.UserRepositoryCustom;

/**
 * Classe UserRepository.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Repository
public interface UserRepository extends JpaRepository<UserApplication, Long>, UserRepositoryCustom {

    public Optional<UserApplication> findByUserName(String username);

    /**
     * @param usernameOrEmail
     * @return
     */
    public Optional<UserApplication> findByUserNameOrEmail(String username, String email);

    @Query("from UserApplication where person.cpf = :cpf")
    public Optional<UserApplication> findByCpf(String cpf);
}

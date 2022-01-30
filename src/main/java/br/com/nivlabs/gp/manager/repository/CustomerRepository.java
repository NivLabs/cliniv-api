package br.com.nivlabs.gp.manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.gp.manager.models.Customer;

/**
 * 
 * Repositório para controle de clientes
 *
 * @author <a href="viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 *
 * @since 29 de jan de 2022
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Busca cliente por CGC
     * 
     * @param cgc Cadastro geral do contribuinte
     * @return Cliente encontrado
     */
    Optional<Customer> findByCgc(String cgc);

    /**
     * Busca cliente por UUID
     * 
     * @param uuid Identificador universal único do cliente
     * @return Cliente encontrado
     */
    Optional<Customer> findByUuid(String uuid);

}

package br.com.ft.gdp.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Classe genérica base com CRUD para a criação da camada de serviços
 * 
 * @author Vinícios Rodrigues
 * @since 11/02/2019
 * @version 1.0
 *
 *
 * @param <E> Tipo da entidade
 * @param <PK> Tipo da chave primária
 * 
 * @see Pageable
 *      <p>
 *      Ver documentação do objeto de paginação do Spring
 *      </p>
 */
public abstract class GenericService<E extends Serializable, PK extends Serializable> {

    /**
     * Realiza a busca da lista paginada da entidade, o método recebe como parâmetro um objeto do tipo Pageable do Spring.
     * 
     * @author Vinícios Rodrigues
     * @since 11-07-2019
     * @param pageRequest
     * @return Objeto paginado com a Entidade
     * @see Pageable
     */
    public abstract Page<E> searchEntityPage(Pageable pageRequest);

    /**
     * Realiza a busca da entidade por ID (Chave primária, podendo ser composta ou não)
     * 
     * @param id
     * @return Entidade
     */
    public abstract E findById(PK id);

    /**
     * Atualiza a entidade em questão, o ID deve ser informado e diferente de nulo para que possa ser feita a implementação correta baseada
     * nas especificações RESTFUL + SpringBoot
     * 
     * @param id
     * @param entity
     * @return
     */
    public abstract E update(PK id, E entity);

    /**
     * Realiza o delete. Este método pode ser implementado tanto com um delete físico quanto por um delete lógico, vai depender da regra de
     * negócio.
     * 
     * @param entity
     */
    public abstract void delete(E entity);

    /**
     * Realiza o delete por chave primária. Este método só deve ser usado por entidades sem depedências de integridade física. Desta forma
     * um delete lógico pode ser implementado sem problemas de restrição.
     * 
     * @param id
     */
    public abstract void deleteById(PK id);

    /**
     * Método usado para persistir entidades. Por definição arquitetural, as entidades de inserção devem sempre conter ID nulo.
     * 
     * @param entity
     * @return
     */
    public abstract E persist(E entity);
}

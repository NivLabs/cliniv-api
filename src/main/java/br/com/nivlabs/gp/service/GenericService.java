package br.com.nivlabs.gp.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.exception.HttpException;

/**
 * Classe genérica base com CRUD para a criação da camada de serviços
 * 
 * @author Vinícios Rodrigues
 * @since 11/02/2019
 * @version 1.0
 *
 *
 * @param <E>  Tipo da entidade
 * @param <PK> Tipo da chave primária
 * 
 * @see Pageable
 *      <p>
 *      Ver documentação do objeto de paginação do Spring
 *      </p>
 */
public interface GenericService<E extends Serializable, PK extends Serializable> {

	/**
	 * Realiza a busca da lista paginada da entidade, o método recebe como parâmetro
	 * um objeto do tipo Pageable do Spring.
	 * 
	 * @author Vinícios Rodrigues
	 * @since 11-07-2019
	 * @param pageRequest
	 * @return Objeto paginado com a Entidade
	 * @see Pageable
	 */
	public default Page<E> searchEntityPage(Pageable pageRequest) {
		throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Operação inválida");
	}

	/**
	 * Realiza a busca da entidade por ID (Chave primária, podendo ser composta ou
	 * não)
	 * 
	 * @param id
	 * @return Entidade
	 */
	public default E findById(PK id) {
		throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Operação inválida");
	}

	/**
	 * Atualiza a entidade em questão, o ID deve ser informado e diferente de nulo
	 * para que possa ser feita a implementação correta baseada nas especificações
	 * RESTFUL + SpringBoot
	 * 
	 * @param id
	 * @param entity
	 * @return
	 */
	public default E update(PK id, E entity) {
		throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Operação inválida");
	}

	/**
	 * Realiza o delete. Este método pode ser implementado tanto com um delete
	 * físico quanto por um delete lógico, vai depender da regra de negócio.
	 * 
	 * @param entity
	 */
	public default void delete(E entity) {
		throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Operação inválida");
	}

	/**
	 * Realiza o delete por chave primária. Este método só deve ser usado por
	 * entidades sem depedências de integridade física. Desta forma um delete lógico
	 * pode ser implementado sem problemas de restrição.
	 * 
	 * @param id
	 */
	public default void deleteById(PK id) {
		throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Operação inválida");
	}

	/**
	 * Método usado para persistir entidades. Por definição arquitetural, as
	 * entidades de inserção devem sempre conter ID nulo.
	 * 
	 * @param entity
	 * @return
	 */
	public default E persist(E entity) {
		throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED, "Operação inválida");
	}
}

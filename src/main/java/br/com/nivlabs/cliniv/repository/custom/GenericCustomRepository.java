package br.com.nivlabs.cliniv.repository.custom;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Classe gerénica para implementação de repositórios customizados com paginação
 * 
 * @author viniciosarodrigues
 *
 * @param <T> Tipo da entidade principal
 * @param <R> Tipo da entidade resumida
 */
public abstract class GenericCustomRepository<T extends Serializable, R extends Serializable> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    private Class<T> getPersistentClass(final Class<?> clazz) {
        final Type type = clazz.getGenericSuperclass();

        ParameterizedType paramType;

        if (type instanceof ParameterizedType) {
            paramType = (ParameterizedType) type;
        } else {
            paramType = (ParameterizedType) ((Class<T>) type).getGenericSuperclass();
        }
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }

    /**
     * Inicializa a entidade de persistência
     * 
     * @return
     */
    protected Class<T> getDelegateClass() {
        if (this.persistentClass == null) {
            this.persistentClass = this.getPersistentClass(this.getClass());
        }
        return this.persistentClass;
    }

    /**
     * Construtor privado Inicializa a estrutura básica do JPA
     */
    protected GenericCustomRepository() {
        this.getDelegateClass();
    }

    /**
     * Cria a query de pesquisa
     * 
     * @param criteria
     * @param pageSettings
     * @return
     */
    protected TypedQuery<R> createQuery(CriteriaQuery<R> criteria, Pageable pageSettings) {
        TypedQuery<R> query = entityManager.createQuery(criteria);
        createPaginationRestrictions(query, pageSettings);
        return query;
    }

    /**
     * Busca o total de elementos da pesquisa
     * 
     * @param filters
     * @return
     */
    protected Long getTotalElements(CustomFilters filters) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(persistentClass);

        Predicate[] predicates = createRestrictions(filters, builder, root);
        criteriaQuery.where(predicates);
        criteriaQuery.select(builder.count(root));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    /**
     * Devolve a página solicitada à partir dos filtros
     * 
     * @param filters
     * @param pageSettings
     * @return
     */
    protected Page<R> getPage(CustomFilters filters, CriteriaBuilder builder, CriteriaQuery<R> criteria,
                              Root<T> root) {

        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());

        if (!StringUtils.isNullOrEmpty(filters.getOrderBy())) {
            if (!filters.getDirection().equals(Direction.ASC.name()))
                criteria.orderBy(builder.desc(root.get(filters.getOrderBy())));
            else
                criteria.orderBy(builder.asc(root.get(filters.getOrderBy())));
        }

        criteria.where(createRestrictions(filters, builder, root));
        TypedQuery<R> query = createQuery(criteria, pageSettings);

        return new PageImpl<>(query.getResultList(), pageSettings, getTotalElements(filters));
    }

    /**
     * Cria restrições de paginação
     * 
     * @param query
     * @param pageSettings
     */
    protected void createPaginationRestrictions(TypedQuery<R> query, Pageable pageSettings) {
        query.setFirstResult(pageSettings.getPageNumber() * pageSettings.getPageSize());
        query.setMaxResults(pageSettings.getPageSize());

    }

    /**
     * Método usado para criar restrições de consulta com filtros
     * 
     * @param customFilters
     * @param builder
     * @param root
     * @return
     */
    protected abstract Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<T> root);

}

package br.com.ft.gdp.repository.custom;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * Classe genérica para criação de repositórios customizados.
 * 
 * @author viniciosarodrigues
 *
 */
public class GenericCustomRepository<T extends Serializable> {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Realiza uma busca paginada baseada em Expressões
     * 
     * @param attributes
     * @param pageSettings
     * @return
     */
    public Page<T> pagination(List<IExpression<T>> attributes, Pageable pageSettings, Class<T> persistentClass) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
        Root<T> root = criteria.from(persistentClass);

        Predicate predicates = createRestrictions(attributes, builder, root);

        criteria.where(predicates);

        TypedQuery<T> query = entityManager.createQuery(criteria);

        addPageRestrinctions(query, pageSettings.getPageNumber(), pageSettings.getPageSize());
        List<T> resultList = query.getResultList();

        return setPageSettings(attributes, pageSettings, resultList, persistentClass);
    }

    private Page<T> setPageSettings(List<IExpression<T>> attributes, Pageable pageSettings,
                                    List<T> resultList, Class<T> persistentClass) {
        return new PageImpl<>(resultList, pageSettings, getCount(attributes, persistentClass));
    }

    /**
     * Realiza o select count para usar na paginação
     * 
     * @param attributes
     * @return
     */
    private Integer getCount(List<IExpression<T>> attributes, Class<T> persistentClass) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(persistentClass);

        Predicate predicates = createRestrictions(attributes, builder, root);

        criteriaQuery.where(predicates);
        criteriaQuery.select(builder.count(root));

        return entityManager.createQuery(criteriaQuery).getSingleResult().intValue();
    }

    /**
     * Realiza as restrições de paginação
     * 
     * @param query
     * @param currentPage
     * @param totalItems
     */
    private void addPageRestrinctions(TypedQuery<T> query, Integer currentPage, Integer totalItems) {
        query.setFirstResult(currentPage * totalItems);
        query.setMaxResults(totalItems);
    }

    /**
     * Cria as restrições baseadas nas exmpressões
     * 
     * @param attributes
     * @param builder
     * @param root
     * @return
     */
    private Predicate createRestrictions(List<IExpression<T>> attributes, CriteriaBuilder builder, Root<T> root) {
        Predicate predicate = builder.conjunction();
        if (attributes != null)
            for (IExpression<T> iExpression : attributes) {
                predicate = builder.and(predicate, iExpression.expression(builder, root));
            }
        return predicate;
    }

}

package br.com.tl.gdp.repository.custom;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Objeto utilizado para a criação de Criterias complexas
 * 
 * @author viniciosarodrigues
 *
 * @param <T> Tipo do objeto
 */
@FunctionalInterface
public interface IExpression<T extends Serializable> {
    public Predicate expression(CriteriaBuilder cb, Root<T> from);
}

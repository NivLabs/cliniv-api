package br.com.nivlabs.cliniv.models;

import jakarta.persistence.MappedSuperclass;

/**
 * Classe BaseObject.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 6 de set de 2019
 */
@MappedSuperclass
public abstract class BaseObjectWithId<T> extends BaseObject {

    public abstract T getId();
}

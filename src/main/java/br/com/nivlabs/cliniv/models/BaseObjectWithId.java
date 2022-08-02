package br.com.nivlabs.cliniv.models;

import javax.persistence.MappedSuperclass;

/**
 * Classe BaseObject.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 6 de set de 2019
 */
@MappedSuperclass
public abstract class BaseObjectWithId extends BaseObject {

    private static final long serialVersionUID = -9208215308257385331L;

    public abstract Long getId();
}

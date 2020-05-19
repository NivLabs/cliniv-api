package br.com.nivlabs.gp.models;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * Classe BaseObject.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 6 de set de 2019
 */
@MappedSuperclass
public abstract class BaseObject implements Serializable {

    private static final long serialVersionUID = -9208215308257385331L;

    public abstract Long getId();
}

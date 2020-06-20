package br.com.nivlabs.gp.models;

/**
 * Classe VersionedBaseObject.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 6 de set de 2019
 */
public abstract class VersionedBaseObject extends BaseObjectWithId {

    /**
     * 
     */
    private static final long serialVersionUID = -3515546183518030541L;

    public abstract Long getVersion();
}

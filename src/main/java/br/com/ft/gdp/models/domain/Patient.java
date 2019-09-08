package br.com.ft.gdp.models.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.ForeignKey;

/**
 * Classe Patient.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 6 de set de 2019
 */
public class Patient implements Serializable {

    private static final long serialVersionUID = 3114141863299939006L;
    
    @ElementCollection
    @CollectionTable(name = "TELEFONE", foreignKey = @ForeignKey(name = "FK_TELEFONE_USUARIO"))
    @Column(name = "TELEFONE")
    private Set<String> phones = new HashSet<>();

}

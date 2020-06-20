package br.com.nivlabs.gp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ALERGIA")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@IdClass(AllergyID.class)
public class Allergy extends BaseObject {

    private static final long serialVersionUID = 7282160459125369684L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Id
    @Column(name = "DESCRICAO")
    private String description;

    /**
     * Construtor criado para consultar alergias por descrição
     * 
     * @param description
     */
    public Allergy(String description) {
        super();
        this.description = description;
    }

}

package br.com.ft.gdp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.dto.ResponsibleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe Reponsible.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Entity
@Table(name = "RESPONSAVEL")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Responsible extends BaseObject {

    private static final long serialVersionUID = 6649135435429790655L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String name;

    private String cpf;

    @Column(name = "REGISTRO_PROFISSIONAL")
    private String professionalIdentity;

    @JsonIgnore
    public ResponsibleDTO getResponsibleDTOFromDomain() {
        ResponsibleDTO dtoEntity = new ResponsibleDTO();

        dtoEntity.setId(getId());
        dtoEntity.setCpf(getCpf());
        dtoEntity.setName(getName());
        dtoEntity.setProfessionalIdentity(getProfessionalIdentity());

        return dtoEntity;
    }
}

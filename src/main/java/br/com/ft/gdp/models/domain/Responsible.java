package br.com.ft.gdp.models.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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

    @OneToOne(fetch = FetchType.EAGER)
    private Person person;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ESPECIALIDADE_RESPONSAVEL", joinColumns = @JoinColumn(name = "ID_RESPONSAVEL"), inverseJoinColumns = @JoinColumn(name = "ID_ESPECIALIDADE"))
    private List<Speciality> specialty = new ArrayList<>();

    @Column(name = "REGISTRO_PROFISSIONAL")
    private String professionalIdentity;

    @JsonIgnore
    public ResponsibleDTO getResponsibleDTOFromDomain() {
        ResponsibleDTO dtoEntity = new ResponsibleDTO();
        dtoEntity.setId(getId());
        dtoEntity.setName(getPerson().getFirstName());
        dtoEntity.setLastName(getPerson().getLastName());
        dtoEntity.setProfessionalIdentity(getProfessionalIdentity());
        dtoEntity.setSpecialty(getSpecialty());

        return dtoEntity;
    }

    public Responsible(Long id) {
        super();
        this.id = id;
    }
}

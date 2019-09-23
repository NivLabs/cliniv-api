package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.domain.Responsible;
import br.com.ft.gdp.models.domain.Specialty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe ResponsibleDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ResponsibleDTO implements Serializable {
    private static final long serialVersionUID = -5141572031863436326L;

    private Long id;

    @NotBlank(message = "Informar o NOME é obrigatório")
    @Size(min = 3, message = "O nome deve conter ao menos três letras")
    private String name;

    @NotBlank(message = "Informar o CPF é obrigatório")
    @Size(min = 11, max = 11, message = "Você deve informar os 11 dígitos do CPF")
    private String cpf;

    private List<Specialty> specialty = new ArrayList<>();

    private String professionalIdentity;

    @JsonIgnore
    public Responsible getResponsibleDomainFromDTO() {
        Responsible domain = new Responsible();

        domain.setId(getId());
        domain.setCpf(getCpf());
        domain.setName(getName());
        domain.setProfessionalIdentity(getProfessionalIdentity());
        domain.setSpecialty(getSpecialty());

        return domain;
    }
}

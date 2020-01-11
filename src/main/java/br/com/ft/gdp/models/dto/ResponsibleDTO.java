package br.com.ft.gdp.models.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.domain.Person;
import br.com.ft.gdp.models.domain.Responsible;
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
    private String firstName;

    @NotBlank(message = "Informar o SOBRENOME é obrigatório")
    @Size(min = 1, message = "O nome deve conter ao menos uma letra")
    private String lastName;

    private String professionalIdentity;

    @JsonIgnore
    public Responsible getResponsibleDomainFromDTO() {
        Responsible domain = new Responsible();
        domain.setId(id);
        domain.setProfessionalIdentity(professionalIdentity);

        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);

        domain.setPerson(person);

        return domain;
    }
}

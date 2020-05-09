package br.com.ft.gdp.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.domain.EventType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * Classe EventTypeDTO.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Event Type")
public class EventTypeDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 2370290606342755763L;

    private Long id;

    @NotBlank(message = "Informar o NOME  do evento é obrigatório")
    @Size(min = 3, max = 45, message = "O nome do evento deve conter ao menos três letras")
    private String name;

    @Size(min = 3, max = 200, message = "A descricao do evento deve conter ao menos três letras")
    private String description;

    @JsonIgnore
    public EventType getEventTypeDomainFromDTO() {
        EventType domain = new EventType();

        domain.setId(getId());
        domain.setName(getName());
        domain.setDescription(getDescription());
        return domain;
    }
}

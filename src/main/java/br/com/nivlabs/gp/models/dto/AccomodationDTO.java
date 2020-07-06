package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

import br.com.nivlabs.gp.models.enums.AccomodationType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Sala (ambulatório) ou Leito")
public class AccomodationDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 1574668174638044648L;

    private Long id;

    @NotNull(message = "Informe o identificador do setor de cadastro")
    private Long sectorId;

    @NotNull(message = "Informe a descrição da sala ou leito")
    private String description;

    @NotNull(message = "Informe se é sala (ambulatório) ou leito")
    private AccomodationType type;

    public AccomodationDTO(Long id) {
        super();
        this.id = id;
    }

}

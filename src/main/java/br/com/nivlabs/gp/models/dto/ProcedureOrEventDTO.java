package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Procedimento ou Evento")
public class ProcedureOrEventDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4991985626952633251L;

    private Long id;

    private String description;

    private boolean active;
}

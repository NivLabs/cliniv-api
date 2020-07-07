package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import br.com.nivlabs.gp.enums.DigitalDocumentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Documento digital de eventos de atendimento
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("Documento Digital")
public class DigitalDocumentDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -870502165996369141L;

    @ApiModelProperty("Identificador único do documento")
    private Long id;

    @ApiModelProperty("Identificador do evento de atendimento")
    private Long attendanceEventId;

    @ApiModelProperty("Tipo do documento")
    private DigitalDocumentType type;

    @ApiModelProperty("Base64 do documento")
    private String base64;

    @ApiModelProperty("Nome ou título do documento")
    private String name;

    @ApiModelProperty("Data da criação do documento")
    private LocalDateTime createdAt;

}

package br.com.tl.gdp.models.dto;

import java.time.LocalDateTime;

import br.com.tl.gdp.models.domain.EventType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Objeto que representa um evento do atendimento
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Visit Event Information")
public class VisitEventInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4230965183543396561L;

    private Long id;
    private Long patientId;
    private Long responsibleId;
    private Long visitId;
    private Long documentId;
    private String title;
    private String observations;
    private LocalDateTime eventDateTime;
    private EventType type;
}

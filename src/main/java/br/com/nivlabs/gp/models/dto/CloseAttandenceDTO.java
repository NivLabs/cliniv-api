package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Objeto externo que representa o encerramento de um atendimento
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Encerramento de atendimento")
public class CloseAttandenceDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4058913578939379862L;

    @ApiModelProperty("Tipo do evento de encerramento - ex: Alta médica por melhora")
    @NotNull(message = "Informe o tipo de evento de encerramento")
    private Long eventTypeId;

    @ApiModelProperty("Data/Hora do encerramento do atendimento")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    @NotNull(message = "Informe a data e hora do evento e encerramento")
    private LocalDateTime datetime;

    @ApiModelProperty("Observações do encerramento (se houver)")
    private String observations;

}

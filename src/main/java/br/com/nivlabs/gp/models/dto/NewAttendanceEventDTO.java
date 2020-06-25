package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Evento de Atendimento")
public class NewAttendanceEventDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 9069758427814475745L;

    @ApiModelProperty("Identificador do Atendimento")
    @NotNull(message = "O código do atendimeno é obrigatório")
    private Long attendanceId;

    @ApiModelProperty("Data/Hora do evento")
    @DateTimeFormat(iso = ISO.DATE)
    @NotNull(message = "A data do evento não pode ser nula")
    private LocalDateTime eventDateTime;

    @ApiModelProperty("Dovumentos do evento (se houver)")
    private List<DigitalDocumentDTO> documents = new ArrayList<>();

    @ApiModelProperty("Tipo do evento")
    @NotNull(message = "Informar o tipo do evento é obrigatório")
    private EventTypeDTO eventType;

    @ApiModelProperty("Obsevações (se houver)")
    private String observations;

    @ApiModelProperty("Procedimento do evento (se houver)")
    private ProcedureDTO procedure;

    @ApiModelProperty("Responsável por gerar o evento")
    @NotNull(message = "Obrigado informar um responsável")
    private ResponsibleDTO responsible;

    @NotNull(message = "Informar a sala ou leito em que o evento foi realizado é obrigatório")
    @ApiModelProperty("Sala ou leito em que o evento ocorreu")
    private RoomOrBedDTO roomOrBed;

}

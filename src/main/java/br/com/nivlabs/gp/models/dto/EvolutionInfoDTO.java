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
 * Classe EvolutionInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 5 de dez de 2019
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Evolução Clínica")
public class EvolutionInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -6201888790941764512L;

    @ApiModelProperty("Identificador únimco da evolução clínica")
    private Long id;

    @ApiModelProperty("Identificador únimco do atendimento (Obrigatório)")
    @NotNull(message = "Informe o código do atendimento")
    private Long attendanceId;

    @ApiModelProperty("Identificador únimco da acomodação do paciente (Opcional se já houver registro com acomodação)")
    private Long accomodationId;

    @ApiModelProperty("Descrição da evolução (Obrigatório)")
    @NotNull(message = "Informe a descrição da evolução clínica")
    private String description;

    @ApiModelProperty("Data/Hora da leitura da evolução")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime datetime;

}

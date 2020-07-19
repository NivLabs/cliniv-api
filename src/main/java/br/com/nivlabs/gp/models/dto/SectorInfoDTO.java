package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe SectorDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 13 de dez de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("Informações do Setor")
public class SectorInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -8018406138528606923L;

    @ApiModelProperty("Identificador único do setor")
    private Long id;

    @ApiModelProperty("Descrição do setor")
    private String description;

    @ApiModelProperty("Lista de salas e leitos do setor (Acomodações)")
    private List<AccomodationDTO> listOfRoomsOrBeds = new ArrayList<>();

    @ApiModelProperty("Data / Hora de criação do setor")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDateTime createdAt;

}

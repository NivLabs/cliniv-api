package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
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
@ApiModel("Sector")
public class SectorDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -8018406138528606923L;

    private Long id;

    private String description;

    private SectorDTO superSector;

    private LocalDateTime createdAt;

    @JsonIgnoreProperties({"sectors", "superSector"})
    private List<SectorDTO> sectors = new ArrayList<>();

}

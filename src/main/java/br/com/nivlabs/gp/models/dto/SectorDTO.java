package br.com.nivlabs.gp.models.dto;

import java.util.ArrayList;
import java.util.List;

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

	private List<RoomOrBedDTO> listOfRoomsOrBeds = new ArrayList<>();

}

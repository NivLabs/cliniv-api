package br.com.nivlabs.gp.models.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Prescrição do paciente")
public class PrescriptionInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 6743345972601222205L;

    @ApiModelProperty("Identificador único da prescrição")
    private Long id;

    @ApiModelProperty("Identificador único do atendimento")
    private Long attendanceId;

    @ApiModelProperty("Identificador único do responsável da prescrição (Quem criou)")
    private Long responsibleId;

    @ApiModelProperty("Lista de itens da prescrição")
    private List<PrescriptionItemDTO> item = new ArrayList<>();

    @ApiModelProperty("Indica uma prescrição especial com duas vias para medicamentos de receita obrigatória")
    private boolean special;

    @ApiModelProperty("Indica que a prescrição deve ser usada no prontuário para controle de checagem de medicamento")
    private boolean insertToMedicalRecords;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(Long attendanceId) {
		this.attendanceId = attendanceId;
	}

	public Long getResponsibleId() {
		return responsibleId;
	}

	public void setResponsibleId(Long responsibleId) {
		this.responsibleId = responsibleId;
	}

	public List<PrescriptionItemDTO> getItem() {
		return item;
	}

	public void setItem(List<PrescriptionItemDTO> item) {
		this.item = item;
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
	}

	public boolean isInsertToMedicalRecords() {
		return insertToMedicalRecords;
	}

	public void setInsertToMedicalRecords(boolean insertToMedicalRecords) {
		this.insertToMedicalRecords = insertToMedicalRecords;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attendanceId == null) ? 0 : attendanceId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (insertToMedicalRecords ? 1231 : 1237);
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((responsibleId == null) ? 0 : responsibleId.hashCode());
		result = prime * result + (special ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrescriptionInfoDTO other = (PrescriptionInfoDTO) obj;
		if (attendanceId == null) {
			if (other.attendanceId != null)
				return false;
		} else if (!attendanceId.equals(other.attendanceId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insertToMedicalRecords != other.insertToMedicalRecords)
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (responsibleId == null) {
			if (other.responsibleId != null)
				return false;
		} else if (!responsibleId.equals(other.responsibleId))
			return false;
		 if (special != other.special)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PrescriptionInfoDTO [id=" + id + ", attendanceId=" + attendanceId + ", responsibleId=" + responsibleId
				+ ", item=" + item + ", special=" + special + ", insertToMedicalRecords=" + insertToMedicalRecords
				+ "]";
	}
    
}
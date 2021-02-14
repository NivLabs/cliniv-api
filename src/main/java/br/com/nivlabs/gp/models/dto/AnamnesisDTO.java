package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.models.domain.Anamnesis;
import br.com.nivlabs.gp.models.domain.Attendance;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Anamnese")
public class AnamnesisDTO extends DataTransferObjectBase {
    private static final long serialVersionUID = -7700694137849034946L;

    @ApiModelProperty("Identificador da resposta da anamnese, não é obrigatório para a inserção")
    private Long id;

    @NotBlank(message = "Informar o atendimento é obrigatório.")
    @ApiModelProperty("Identificador do atendimento")
    private Long attendanceId;

    @NotBlank(message = "Informar o item da pergunta da anamnese é obrigatório.")
    @ApiModelProperty("Item da anamese com pertunga e tipo da resposta")
    private DynamicFormQuestionDTO anamnesisItem;

    @NotBlank(message = "Informar a resposta é obrigatório.")
    @ApiModelProperty("Resposta")
    private String response;

    public AnamnesisDTO() {
        super();
    }

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

    public DynamicFormQuestionDTO getAnamnesisItem() {
        return anamnesisItem;
    }

    public void setAnamnesisItem(DynamicFormQuestionDTO anamnesisItem) {
        this.anamnesisItem = anamnesisItem;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((anamnesisItem == null) ? 0 : anamnesisItem.hashCode());
        result = prime * result + ((attendanceId == null) ? 0 : attendanceId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((response == null) ? 0 : response.hashCode());
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
        AnamnesisDTO other = (AnamnesisDTO) obj;
        if (anamnesisItem == null) {
            if (other.anamnesisItem != null)
                return false;
        } else if (!anamnesisItem.equals(other.anamnesisItem))
            return false;
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
        if (response == null) {
            if (other.response != null)
                return false;
        } else if (!response.equals(other.response))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AnamnesisDTO [id=" + id + ", attendanceId=" + attendanceId + ", anamnesisItem=" + anamnesisItem + ", response=" + response
                + "]";
    }

    @JsonIgnore
    public Anamnesis getAnamnesesDomainFromDTO() {
        Anamnesis domain = new Anamnesis();

        domain.setId(id);
        domain.setAttendance(new Attendance(attendanceId));
        domain.setQuestion(anamnesisItem.getQuestion());
        domain.setResponse(response);

        return domain;
    }

}

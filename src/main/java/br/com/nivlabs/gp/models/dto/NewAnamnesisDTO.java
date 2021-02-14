package br.com.nivlabs.gp.models.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Requisição de Anamnese")
public class NewAnamnesisDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 1489473679769549274L;

    @NotNull(message = "Informe o código do atendimento")
    @ApiModelProperty("Identificador do atendimento")
    private Long attendanceId;

    @ApiModelProperty("Sala ou leito onde a anamnese foi realizada")
    private Long accommodationId;

    private Set<AnamnesisDTO> listOfResponse = new HashSet<>();

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public Set<AnamnesisDTO> getListOfResponse() {
        return listOfResponse;
    }

    public void setListOfResponse(Set<AnamnesisDTO> listOfResponse) {
        this.listOfResponse = listOfResponse;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accommodationId == null) ? 0 : accommodationId.hashCode());
        result = prime * result + ((attendanceId == null) ? 0 : attendanceId.hashCode());
        result = prime * result + ((listOfResponse == null) ? 0 : listOfResponse.hashCode());
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
        NewAnamnesisDTO other = (NewAnamnesisDTO) obj;
        if (accommodationId == null) {
            if (other.accommodationId != null)
                return false;
        } else if (!accommodationId.equals(other.accommodationId))
            return false;
        if (attendanceId == null) {
            if (other.attendanceId != null)
                return false;
        } else if (!attendanceId.equals(other.attendanceId))
            return false;
        if (listOfResponse == null) {
            if (other.listOfResponse != null)
                return false;
        } else if (!listOfResponse.equals(other.listOfResponse))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NewAnamnesisDTO [attendanceId=" + attendanceId + ", accommodationId=" + accommodationId
                + ", listOfResponse=" + listOfResponse + "]";
    }

}

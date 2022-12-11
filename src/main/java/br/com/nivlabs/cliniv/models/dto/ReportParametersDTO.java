package br.com.nivlabs.cliniv.models.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Parâmetros do relatório de atendimentos")
public class ReportParametersDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -480968729180432661L;

    @Min(value = 1, message = "Informe um mês válido")
    @Max(value = 12, message = "Informe um mês válido")
    private Integer month;
    @NotNull(message = "Informe o ano de vigência")
    private Integer year;
    private Long responsibleId;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ReportParametersDTO [month=");
        builder.append(month);
        builder.append(", year=");
        builder.append(year);
        builder.append(", responsibleId=");
        builder.append(responsibleId);
        builder.append("]");
        return builder.toString();
    }

}

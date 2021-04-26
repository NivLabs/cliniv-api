package br.com.nivlabs.gp.models.dto;

import java.math.BigDecimal;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import br.com.nivlabs.gp.enums.TimeIntervalType;
import br.com.nivlabs.gp.models.domain.UnitOfMeasurement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Item da prescrição do paciente")
public class PrescriptionItemDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 2268618756493323347L;

    @ApiModelProperty("Sequencial do item da prescrição")
    @NotNull(message = "Informe o sequencial do item da prescrição")
    private Long sequential;

    @ApiModelProperty("Descrição do item da prescrição")
    @NotNull(message = "Informe a descrição do item da prescrição")
    private String description;

    @ApiModelProperty("Observações/Instruções para o item da prescrição")
    private String observations;

    @ApiModelProperty("Via de administração da medicação")
    private String routeOfAdministration;

    @ApiModelProperty("Dose da medicação à ser administrada")
    private BigDecimal dosage;

    @ApiModelProperty("Unidade de medida para a dosagem do medicamento")
    private UnitOfMeasurement unitOfMeasurement;

    @ApiModelProperty("Intervalo de tempo entre o uso do medicamento/procedimento")
    private Integer timeInterval;

    @ApiModelProperty("Tipo do intervalo de tempo")
    @Enumerated(EnumType.STRING)
    private TimeIntervalType timeIntervalType;

    public Long getSequential() {
        return sequential;
    }

    public void setSequential(Long sequential) {
        this.sequential = sequential;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getRouteOfAdministration() {
        return routeOfAdministration;
    }

    public void setRouteOfAdministration(String routeOfAdministration) {
        this.routeOfAdministration = routeOfAdministration;
    }

    public BigDecimal getDosage() {
        return dosage;
    }

    public void setDosage(BigDecimal dosage) {
        this.dosage = dosage;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public TimeIntervalType getTimeIntervalType() {
        return timeIntervalType;
    }

    public void setTimeIntervalType(TimeIntervalType timeIntervalType) {
        this.timeIntervalType = timeIntervalType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PrescriptionItemDTO [sequential=");
        builder.append(sequential);
        builder.append(", description=");
        builder.append(description);
        builder.append(", observations=");
        builder.append(observations);
        builder.append(", routeOfAdministration=");
        builder.append(routeOfAdministration);
        builder.append(", dosage=");
        builder.append(dosage);
        builder.append(", unitOfMeasurement=");
        builder.append(unitOfMeasurement);
        builder.append(", timeInterval=");
        builder.append(timeInterval);
        builder.append(", timeIntervalType=");
        builder.append(timeIntervalType);
        builder.append("]");
        return builder.toString();
    }

}

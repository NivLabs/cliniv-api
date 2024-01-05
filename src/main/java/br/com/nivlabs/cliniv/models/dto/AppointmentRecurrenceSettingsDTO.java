package br.com.nivlabs.cliniv.models.dto;

import br.com.nivlabs.cliniv.enums.IntervalType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Configurações de repetição de agenda")
public class AppointmentRecurrenceSettingsDTO extends DataTransferObjectBase {

    @Schema(description = "Número de ocorrências")
    private int numberOfOccurrences;
    @Schema(description = "Tipo de intervalo")
    private IntervalType intervalType;

    @Schema(description = "Flag para definição de apenas dias úteis")
    private boolean businessDaysOnly;

    public int getNumberOfOccurrences() {
        return numberOfOccurrences;
    }

    public void setNumberOfOccurrences(int numberOfOccurrences) {
        this.numberOfOccurrences = numberOfOccurrences;
    }

    public IntervalType getIntervalType() {
        return intervalType;
    }

    public void setIntervalType(IntervalType intervalType) {
        this.intervalType = intervalType;
    }

    public boolean getBusinessDaysOnly() {
        return businessDaysOnly;
    }

    public void setBusinessDaysOnly(boolean businessDaysOnly) {
        this.businessDaysOnly = businessDaysOnly;
    }

    @Override
    public String toString() {
        return "AppointmentRecurrenceSettingsDTO{" +
                "numberOfOccurrences=" + numberOfOccurrences +
                ", interval=" + intervalType +
                ", businessDaysOnly=" + businessDaysOnly +
                '}';
    }
}

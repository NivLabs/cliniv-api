package br.com.nivlabs.gp.controller.filters;

import java.time.LocalDate;

import br.com.nivlabs.gp.repository.custom.CustomFilters;

/**
 * Filtro de pesquisa dinâmica para Agendamento
 * 
 * @author viniciosarodrigues
 *
 */
public class ScheduleFilters extends CustomFilters {

    private static final long serialVersionUID = 5552517752886764329L;

    private String professionalId;

    private LocalDate selectedDate;

    private Boolean isConfirmed;

    private Boolean isMissed;

    private Boolean isCanceled;

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public Boolean getIsMissed() {
        return isMissed;
    }

    public void setIsMissed(Boolean isMissed) {
        this.isMissed = isMissed;
    }

    public Boolean getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(Boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    @Override
    public String toString() {
        return "ScheduleFilters [professionalId=" + professionalId + ", selectedDate=" + selectedDate + ", isConfirmed=" + isConfirmed
                + ", isMissed=" + isMissed + ", isCanceled=" + isCanceled + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((isCanceled == null) ? 0 : isCanceled.hashCode());
        result = prime * result + ((isConfirmed == null) ? 0 : isConfirmed.hashCode());
        result = prime * result + ((isMissed == null) ? 0 : isMissed.hashCode());
        result = prime * result + ((professionalId == null) ? 0 : professionalId.hashCode());
        result = prime * result + ((selectedDate == null) ? 0 : selectedDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ScheduleFilters other = (ScheduleFilters) obj;
        if (isCanceled == null) {
            if (other.isCanceled != null)
                return false;
        } else if (!isCanceled.equals(other.isCanceled))
            return false;
        if (isConfirmed == null) {
            if (other.isConfirmed != null)
                return false;
        } else if (!isConfirmed.equals(other.isConfirmed))
            return false;
        if (isMissed == null) {
            if (other.isMissed != null)
                return false;
        } else if (!isMissed.equals(other.isMissed))
            return false;
        if (professionalId == null) {
            if (other.professionalId != null)
                return false;
        } else if (!professionalId.equals(other.professionalId))
            return false;
        if (selectedDate == null) {
            if (other.selectedDate != null)
                return false;
        } else if (!selectedDate.equals(other.selectedDate))
            return false;
        return true;
    }

}

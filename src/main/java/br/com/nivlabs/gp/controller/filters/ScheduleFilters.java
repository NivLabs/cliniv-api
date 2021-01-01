package br.com.nivlabs.gp.controller.filters;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate selectedDate;

    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ScheduleFilters [professionalId=" + professionalId + ", selectedDate=" + selectedDate + ", status=" + status + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((professionalId == null) ? 0 : professionalId.hashCode());
        result = prime * result + ((selectedDate == null) ? 0 : selectedDate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
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
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        return true;
    }

}

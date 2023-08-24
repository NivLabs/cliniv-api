package br.com.nivlabs.cliniv.controller.filters;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.cliniv.repository.custom.CustomFilters;

/**
 * Filtro de pesquisa dinâmica para Agendamento
 * 
 * @author viniciosarodrigues
 *
 */
public class AppointmentFilters extends CustomFilters {

    private static final long serialVersionUID = 5552517752886764329L;

    private String professionalId;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate startDate = LocalDate.now();

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate endDate = LocalDate.now();

    private String status;

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AppointmentFilters [professionalId=");
        builder.append(professionalId);
        builder.append(", startDate=");
        builder.append(startDate);
        builder.append(", endDate=");
        builder.append(endDate);
        builder.append(", status=");
        builder.append(status);
        builder.append("]");
        return builder.toString();
    }

}

package br.com.nivlabs.cliniv.controller.filters;

import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDate;

/**
 * Filtro de pesquisa dinâmica para Agendamento
 *
 * @author viniciosarodrigues
 */
public class AppointmentFilters extends CustomFilters {

    private String professionalId;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate startDate = LocalDate.now();

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate endDate = LocalDate.now();

    private Long patientId;

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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AppointmentFilters{" +
                "professionalId='" + professionalId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", patientId=" + patientId +
                ", status='" + status + '\'' +
                '}';
    }

}

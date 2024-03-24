package br.com.nivlabs.cliniv.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "Requisição de agendamentos do paciente")
public class PatientAppointmentsReportRequestDTO extends DataTransferObjectBase {

    @Schema(description = "Data inicial de filtragem")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate initDate;
    @Schema(description = "Data final de filtragem")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    public PatientAppointmentsReportRequestDTO() {
    }

    public PatientAppointmentsReportRequestDTO(LocalDate initDate, LocalDate endDate) {
        this.initDate = initDate;
        this.endDate = endDate;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "PatientAppointmentsReportRequestDTO{" +
                "initDate=" + initDate +
                ", endDate=" + endDate +
                '}';
    }
}

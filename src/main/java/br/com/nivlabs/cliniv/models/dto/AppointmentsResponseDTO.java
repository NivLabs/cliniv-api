package br.com.nivlabs.cliniv.models.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informações da agenda por filtro")
public class AppointmentsResponseDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -9068171305217428958L;

    @Schema(description = "Dias com agendamento marcado")
    private Set<Integer> daysWithAppointment = new HashSet<>();
    @Schema(description = "Agendamentos do dia")
    private List<AppointmentDTO> content = new ArrayList<>();

    public Set<Integer> getDaysWithAppointment() {
        return daysWithAppointment;
    }

    public void setDaysWithAppointment(Set<Integer> daysWithAppointment) {
        this.daysWithAppointment = daysWithAppointment;
    }

    public List<AppointmentDTO> getContent() {
        return content;
    }

    public void setContent(List<AppointmentDTO> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AppointmentsResponseDTO [daysWithAppointment=");
        builder.append(daysWithAppointment);
        builder.append(", content=");
        builder.append(content);
        builder.append("]");
        return builder.toString();
    }

}

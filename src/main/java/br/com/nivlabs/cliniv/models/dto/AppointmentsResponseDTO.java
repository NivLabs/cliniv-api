package br.com.nivlabs.cliniv.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Informações da agenda por filtro")
public class AppointmentsResponseDTO extends DataTransferObjectBase {

    @Serial
    private static final long serialVersionUID = -9068171305217428958L;

    @Schema(description = "Agendamentos do período")
    private List<AppointmentDTO> content = new ArrayList<>();

    public List<AppointmentDTO> getContent() {
        return content;
    }

    public void setContent(List<AppointmentDTO> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AppointmentsResponseDTO [content=");
        builder.append(content);
        builder.append("]");
        return builder.toString();
    }

}

package br.com.nivlabs.cliniv.enums;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.util.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * Enumerado para situação do agendamento
 *
 * @author viniciosarodrigues
 */
public enum AppointmentStatus {
    WAITING_CONFIRMATION("WAITING_CONFIRMATION", "Aguardando confirmação do paciente"),
    CONFIRMED("CONFIRMED", "Paciente confirmou"),
    COMPLETED("COMPLETED", "Paciente compareceu"),
    CANCELED("CANCELED", "Paciente cancelou"),
    MISSED("MISSED", "Paciente faltou"),
    RESCHEDULED("RESCHEDULED", "Paciente remarcou");

    final private String name;
    final private String description;

    private AppointmentStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public AppointmentStatus toEnum(String statusName) {
        if (StringUtils.isNullOrEmpty(statusName))
            return null;
        else
            for (AppointmentStatus status : AppointmentStatus.values())
                if (status.name.equalsIgnoreCase(statusName))
                    return status;
        throw new HttpException(HttpStatus.BAD_REQUEST, "Situação não encontrada: ".concat(statusName));
    }

}

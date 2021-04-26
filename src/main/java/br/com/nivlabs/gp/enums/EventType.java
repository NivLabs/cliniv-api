package br.com.nivlabs.gp.enums;

import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Tipos de eventos de atendimento utilizados tanto em prontuários quanto agenda
 * 
 * @author viniciosarodrigues
 *
 */
public enum EventType {
    // Consulta médica
    MEDICAL_APPOINTMENT("MEDICAL_APPOINTMENT", "Consulta médica"),
    // Consulta médica remota
    REMOTE_MEDICAL_APPOINTMENT("REMOTE_MEDICAL_APPOINTMENT", "Consulta médica remota"),
    // Controle médico
    MEDICAL_CONTROL("MEDICAL_CONTROL", "Controle médico"),
    // Anamnese
    ANAMESIS("ANAMNESIS", "Anamese"),
    // Urgência médica
    MEDICAL_EMERGENCY("MEDICAL_EMERGENCY", "Urgência médica"),
    // Pós operatório
    POSTOPERATIVE("POSTOPERATIVE", "Pós operatório"),
    // Primeira consulta
    FIRST_CONSULTATION("FIRST_CONSULTATION", "Primeira consulta"),
    // Procedimento
    PROCEDURE("PROCEDURE", "Procedimento"),
    // Retorno
    RETURN("RETURN", "Retorno"),
    // Sessão
    SESSION("SESSION", "Sessão"),
    // Relatório
    REPORT("REPORT", "Relatório"),
    // Evolução
    EVOLUTION("EVOLUTION", "Evolução"),
    // Medicação
    MEDICINE("MEDICINE", "Medicação"),
    // Entrada
    ENTRY("ENTRY", "Entrada"),
    // Fim do atendimento/consulta
    EXIT("EXIT", "Fim do atendimento/consulta"),
    // Precrição
    PRESCRIPTION("PRESCRIPTION", "Prescrição");

    private String name;
    private String description;

    private EventType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public EventType toEnum(String level) {
        if (StringUtils.isNullOrEmpty(level))
            return null;
        else
            for (EventType type : EventType.values())
                if (type.name.equalsIgnoreCase(level))
                    return type;
        throw new HttpException(HttpStatus.BAD_REQUEST, "Tipo de evento não encontrado: ".concat(level));
    }

}

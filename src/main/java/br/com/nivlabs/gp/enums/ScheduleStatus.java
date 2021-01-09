package br.com.nivlabs.gp.enums;

/**
 * Enumerado para situação do agendamento
 * 
 * @author viniciosarodrigues
 *
 */
public enum ScheduleStatus {
    WAITING_CONFIRMATION, // Aguardando confirmação do paciente
    CONFIRMED,  // Paciente confirmou
    COMPLETED,  // Paciente compareceu
    CANCELED,   // Paciente cancelou
    MISSED,     // Paciente faltou
    RESCHEDULED // Paciente remarcou
}

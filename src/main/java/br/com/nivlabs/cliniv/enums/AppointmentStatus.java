package br.com.nivlabs.cliniv.enums;

/**
 * Enumerado para situação do agendamento
 * 
 * @author viniciosarodrigues
 *
 */
public enum AppointmentStatus {
    WAITING_CONFIRMATION, // Aguardando confirmação do paciente
    CONFIRMED,  // Paciente confirmou
    COMPLETED,  // Paciente compareceu
    CANCELED,   // Paciente cancelou
    MISSED,     // Paciente faltou
    RESCHEDULED // Paciente remarcou
}

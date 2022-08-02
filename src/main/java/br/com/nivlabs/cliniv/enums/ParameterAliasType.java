package br.com.nivlabs.cliniv.enums;

/**
 * Apelidos dos parâmetros para consultas mais efetivas
 * 
 * @author viniciosarodrigues
 * @since 23/10/2021
 *
 */
public enum ParameterAliasType {

    // Habilita busca por CEP
    ENABLE_SEARCH_BY_ZIP_CODE,
    // Fornecedor de API de CEP
    ZIP_CODE_SEARCH_PROVIDER,
    // Hora de abertura da agenda
    AGENDA_OPENING_TIME,
    // Hora de encerramento da agenda
    AGENDA_CLOSING_TIME,
    // Intervalos entre horários da agenda
    BREAK_TIME_BETWEEN_SCHEDULE_TIMES,
    // Bloquear agendamento fora do horário
    BLOCK_OUT_OF_SCHEDULE_APPOINTMENT,
    // Bloquear leitura de prontuário sem atendimento ativo
    BLOCKS_READING_THE_MEDICAL_RECORD_WITHOUT_ACTIVE_SERVICE,
    // Habilitar compartilhamento de atendimento
    ENABLE_SERVICE_SHARING;

}

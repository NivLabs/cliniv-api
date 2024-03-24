package br.com.nivlabs.cliniv.service.patient;

import br.com.nivlabs.cliniv.controller.filters.PatientFilters;
import br.com.nivlabs.cliniv.models.dto.*;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.patient.business.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Camada de serviços com todos os processos existentes de manipulação do paciente
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 */
@Service
public class PatientService implements BaseService {

    final SearchPatientBusinessHandler patientSearchBusinessHandler;
    final CreatePatientBusinessHandler createPatientBusinessHandler;
    final UpdatePatientBusinessHandler updatePatientBusinessHandler;
    final UpdatePatientAllergiesBusinessHandler updatePatientAllergiesBusinessHandler;
    final GenerateAppointmentsBusinessHandler generateAppointmentsBusinessHandler;

    @Autowired
    public PatientService(final SearchPatientBusinessHandler patientSearchBusinessHandler,
                          final CreatePatientBusinessHandler createPatientBusinessHandler,
                          final UpdatePatientBusinessHandler updatePatientBusinessHandler,
                          final UpdatePatientAllergiesBusinessHandler updatePatientAllergiesBusinessHandler,
                          final GenerateAppointmentsBusinessHandler generateAppointmentsBusinessHandler) {
        this.patientSearchBusinessHandler = patientSearchBusinessHandler;
        this.createPatientBusinessHandler = createPatientBusinessHandler;
        this.updatePatientBusinessHandler = updatePatientBusinessHandler;
        this.updatePatientAllergiesBusinessHandler = updatePatientAllergiesBusinessHandler;
        this.generateAppointmentsBusinessHandler = generateAppointmentsBusinessHandler;
    }

    /**
     * Busca uma página de informações resumidas de pacientes
     *
     * @param filters Filtros de busca
     * @return Página com informações resumidas de pacientes
     */
    public Page<PatientDTO> getPage(PatientFilters filters) {
        return patientSearchBusinessHandler.getPage(filters);
    }

    /**
     * Busca informações detalhadas do paciente
     *
     * @param id Identificador único do paciente
     * @return Informações detalhadas do paciente
     */
    public PatientInfoDTO findByPatientId(Long id) {
        return patientSearchBusinessHandler.getById(id);
    }

    /**
     * Busca informações do paciente
     * <p>
     * OBS: Se o paciente não for encontrado, uma busca por informações de pessoa física é realizada e retornada
     *
     * @param cpf CPF do paciente
     * @return Informações detalhadas do paciente ou da pessoa física
     */
    public PatientInfoDTO findByCpf(String cpf) {
        return patientSearchBusinessHandler.getByCpf(cpf);
    }

    /**
     * Busca informações do paciente baseado no Código da Carteira Nacional de Saúde
     *
     * @param cnsCode Código da Carteira Nacional de Saúde
     * @return Informações detalhadas do paciente
     */
    public PatientInfoDTO findByCnsNumber(String cnsCode) {
        return patientSearchBusinessHandler.getByCnsNumber(cnsCode);
    }

    /**
     * Atualiza informações de um paciente já existente na base de dados
     *
     * @param id      Identificador único do paciente
     * @param request Objeto de transferência com informações do paciente
     * @return Informações atualizadas do paciente
     */
    public PatientInfoDTO update(Long id, PatientInfoDTO request) {
        request.setId(id);
        return updatePatientBusinessHandler.update(request);
    }

    /**
     * Cadastra um novo paciente na aplicação
     *
     * @param request Objeto de transferência com informações detalhadas do paciente
     * @return Informações do paciente pós insert com códigos de criação
     */
    public PatientInfoDTO persist(PatientInfoDTO request) {
        return createPatientBusinessHandler.create(request);
    }

    /**
     * Atualiza as alergias do paciente
     *
     * @param patientId Identificador único do paciente
     * @param request   Requisição com informações das alergias do paciente
     */
    public void updateAllergies(Long patientId, PatientAllergiesDTO request) {
        updatePatientAllergiesBusinessHandler.update(patientId, request);
    }

    /**
     * Gera um relatório com todos os agendamentos do paciente no range de datas informado na requisição
     *
     * @param patientId Identificador único do paciente
     * @param request   Requisição com range de datas
     * @return Documento digital
     */
    public DigitalDocumentDTO generateAppointmentsReport(final Long patientId, final PatientAppointmentsReportRequestDTO request) {
        return generateAppointmentsBusinessHandler.execute(patientId, request);
    }
}

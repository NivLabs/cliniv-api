package br.com.nivlabs.gp.service.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.PatientFilters;
import br.com.nivlabs.gp.models.dto.PatientAllergiesDTO;
import br.com.nivlabs.gp.models.dto.PatientDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.patient.business.CreatePatientBusinessHandler;
import br.com.nivlabs.gp.service.patient.business.SearchPatientBusinessHandler;
import br.com.nivlabs.gp.service.patient.business.UpdatePatientAllergiesBusinessHandler;
import br.com.nivlabs.gp.service.patient.business.UpdatePatientBusinessHandler;

/**
 * 
 * Camada de serviços com todos os processos existentes de manipulação do paciente
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Service
public class PatientService implements BaseService {

    @Autowired
    SearchPatientBusinessHandler patientSearchBusinessHandler;
    @Autowired
    CreatePatientBusinessHandler createPatientBusinessHandler;
    @Autowired
    UpdatePatientBusinessHandler updatePatientBusinessHandler;
    @Autowired
    UpdatePatientAllergiesBusinessHandler updatePatientAllergiesBusinessHandler;

    /**
     * Busca uma página de informações resumidas de pacientes
     * 
     * @param filters Filtros de busca
     * @param pageRequest Configurações da paginação
     * @return Página com informações resumidas de pacientes
     */
    public Page<PatientDTO> getPage(PatientFilters filters, Pageable pageRequest) {
        return patientSearchBusinessHandler.getPage(filters, pageRequest);
    }

    /**
     * Busca informações detalhadas do paciente
     * 
     * @param id Identificador único do paciente
     * @return Informações detalhadas do paciente
     * 
     */
    public PatientInfoDTO findByPatientId(Long id) {
        return patientSearchBusinessHandler.getById(id);
    }

    /**
     * Busca informações do paciente
     * 
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
     * @param CNS Código da Carteira Nacional de Saúde
     * @return Informações detalhadas do paciente
     */
    public PatientInfoDTO findByCnsNumber(String cnsCode) {
        return patientSearchBusinessHandler.getByCnsNumber(cnsCode);
    }

    /**
     * Atualiza informações de um paciente já existente na base de dados
     * 
     * @param id Identificador único do paciente
     * @param request Objeto de transferência com informações do paciente
     * @return Informações atualizadas do paciente
     */
    public PatientInfoDTO update(Long id, PatientInfoDTO entity) {
        entity.setId(id);
        return updatePatientBusinessHandler.update(entity);
    }

    /**
     * Cadastra um novo paciente na aplicação
     * 
     * @param request Objeto de transferência com informações detalhadas do paciente
     * @return Informações do paciente pós insert com códigos de criação
     */
    public PatientInfoDTO persist(PatientInfoDTO request) {
        return createPatientBusinessHandler.persist(request);
    }

    /**
     * Atualiza as alergias do paciente
     * 
     * @param patientId Identificador único do paciente
     * @param request Requisição com informações das alergias do paciente
     */
    public void updateAllergies(Long patientId, PatientAllergiesDTO request) {
        updatePatientAllergiesBusinessHandler.update(patientId, request);
    }

}

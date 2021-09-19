package br.com.nivlabs.gp.service.attendance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.AttendanceFilters;
import br.com.nivlabs.gp.models.dto.AttendanceDTO;
import br.com.nivlabs.gp.models.dto.CloseAttandenceDTO;
import br.com.nivlabs.gp.models.dto.MedicalRecordDTO;
import br.com.nivlabs.gp.models.dto.NewAttandenceDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.attendance.business.CloseAttendanceBusinessHandler;
import br.com.nivlabs.gp.service.attendance.business.CreateNewAttendanceBusinessHandler;
import br.com.nivlabs.gp.service.attendance.business.SearchAttendanceBusinessHandler;
import br.com.nivlabs.gp.service.attendance.business.SearchMedicalRecordBusinessHandler;

/**
 * 
 * Camada de serviço com todas os métodos referentes á atendimento
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Service
public class AttendanceService implements BaseService {

    @Autowired
    SearchAttendanceBusinessHandler searchAttendanceBusinessHandler;
    @Autowired
    SearchMedicalRecordBusinessHandler searchMedicalRecordBusinessHandler;
    @Autowired
    CreateNewAttendanceBusinessHandler createNewAttendanceBusinessHandler;
    @Autowired
    CloseAttendanceBusinessHandler closeAttendanceBusinessHandler;

    /**
     * 
     * Realiza a busca de uma página de atendimentos
     * 
     * @param filters Filtros de pesquisa
     * @param pageRequest Configurações de paginação
     * @return Página de atendimentos
     */
    public Page<AttendanceDTO> getAttendancesPage(AttendanceFilters filters, Pageable pageRequest) {
        return searchAttendanceBusinessHandler.getPage(filters, pageRequest);
    }

    /**
     * 
     * Busca histórico de Atendimentos por Paciente
     * 
     * @param patientId Identificador único do paciente
     * @return Lista de atendimentos do paciente
     */
    public List<AttendanceDTO> getAttandenceByPatientId(Long patientId) {
        return searchAttendanceBusinessHandler.getListByPatientId(patientId);
    }

    /**
     * Busca Prontuário do atendimento por Identificador único do atendimento
     *
     * @param id Identificador único do atendimento
     * @return MedicalRecord Informações do prontuário de atendimento
     */
    public MedicalRecordDTO findMedicalRecordByAttendanceId(Long id) {
        return searchMedicalRecordBusinessHandler.findByAttendanceId(id);
    }

    /**
     * Realiza a criação de um novo atendimento à partir do DTO
     *
     * @param NewAttandenceDTO Novo atendimento
     * @return MedicalRecordDTO
     */
    public MedicalRecordDTO createNewAttendance(NewAttandenceDTO request) {
        return createNewAttendanceBusinessHandler.create(request);
    }

    /**
     * Realiza a busca de um prontuário ativo por código de paciente
     * 
     * @param patientId Identificador único do paciente
     * @return Prontuário de atendimento ativo do paciente
     */
    public MedicalRecordDTO getActiveMedicalRecord(Long patientId) {
        return searchMedicalRecordBusinessHandler.getActiveMedicalRecord(patientId);
    }

    /**
     * Realiza alta de paciente com atendimento ativo
     *
     * @param id Identificador único do atendimento
     * @param request Objeto de requisição de fechamento de atendimento
     */
    public void closeAttendance(Long id, CloseAttandenceDTO request) {
        closeAttendanceBusinessHandler.closeAttendance(id, request);
    }

}

package br.com.nivlabs.cliniv.service.attendance.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.controller.filters.AttendanceFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Attendance;
import br.com.nivlabs.cliniv.models.domain.Patient;
import br.com.nivlabs.cliniv.models.dto.AttendanceDTO;
import br.com.nivlabs.cliniv.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.cliniv.models.dto.UserInfoDTO;
import br.com.nivlabs.cliniv.repository.AttendanceRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.responsible.ResponsibleService;
import br.com.nivlabs.cliniv.service.userservice.UserService;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;

/**
 * 
 * Camada de negócio para buscas relacionadas à atendimento
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Component
public class SearchAttendanceBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private AttendanceRepository attendanceRepo;
    @Autowired
    private ResponsibleService responsibleService;
    @Autowired
    private UserService userService;

    /**
     * 
     * Realiza a busca de uma página de atendimentos
     * 
     * @param filters Filtros de pesquisa
     * @param pageRequest Configurações de paginação
     * @return Página de atendimentos
     */
    public Page<AttendanceDTO> getPage(AttendanceFilters filters) {
        if (!SecurityContextUtil.isAdmin()) {
            UserInfoDTO userInfo = userService.findByUserName(SecurityContextUtil.getAuthenticatedUser().getUsername());
            filters.setProfissionalId(getResponsibleFromUser(userInfo).getId().toString());
        }
        logger.info("Iniciando uma busca de atendimentos paginada com filtros :: {}", filters);
        return attendanceRepo.resumedList(filters);
    }

    /**
     * 
     * Busca histórico de Atendimentos por Paciente
     * 
     * @param patientId Identificador único do paciente
     * @return Lista de atendimentos do paciente
     */
    public List<AttendanceDTO> getListByPatientId(Long patientId) {
        logger.info("Iniciando busca de lista de atendimentos por paciente :: ID Paciente: {}", patientId);
        List<Attendance> list = attendanceRepo.findByPatient(new Patient(patientId));

        if (list.isEmpty())
            throw new HttpException(HttpStatus.NOT_FOUND,
                    String.format("Não existe atendimento para o paciente %s", patientId));

        List<AttendanceDTO> listOfDto = new ArrayList<>();
        list.forEach(attendance -> {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(attendance.getId());
            dto.setEntryDatetime(attendance.getEntryDateTime());
            dto.setEntryCause(attendance.getReasonForEntry());
            dto.setIsFinished(Boolean.valueOf(attendance.getExitDateTime() != null));
            listOfDto.add(dto);
        });
        return listOfDto;
    }

    /**
     * Busca o responsável pela criação de evento de atendimento
     * 
     * @param requestOwner Usuário da solicitação
     * @return Responsável logado
     */
    private ResponsibleInfoDTO getResponsibleFromUser(UserInfoDTO requestOwner) {
        logger.info("Iniciando busca de responsável pelo usuário da requisição...");
        ResponsibleInfoDTO responsibleInformations = responsibleService.findByCpf(requestOwner.getDocument().getValue());
        if (responsibleInformations.getId() == null)
            throw new HttpException(HttpStatus.FORBIDDEN, "Sem presmissão! Você não tem um profissional vinculado ao seu usuário.");
        logger.info("Profissional encontrado :: {}", responsibleInformations.getFullName());

        return responsibleInformations;
    }

}

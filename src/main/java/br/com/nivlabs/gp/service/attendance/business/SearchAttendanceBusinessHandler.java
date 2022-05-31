package br.com.nivlabs.gp.service.attendance.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.controller.filters.AttendanceFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.dto.AttendanceDTO;
import br.com.nivlabs.gp.repository.AttendanceRepository;
import br.com.nivlabs.gp.repository.PatientRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

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
    private PatientRepository patientRepository;

    /**
     * 
     * Realiza a busca de uma página de atendimentos
     * 
     * @param filters Filtros de pesquisa
     * @param pageRequest Configurações de paginação
     * @return Página de atendimentos
     */
    public Page<AttendanceDTO> getPage(AttendanceFilters filters, Pageable pageRequest) {
        logger.info("Iniciando uma busca de atendimentos paginada com filtros :: {}", filters);
        return attendanceRepo.resumedList(filters, pageRequest);
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

}

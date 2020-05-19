package br.com.nivlabs.gp.repository.custom.attendance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.controller.filters.AttendanceFilters;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.dto.AttendanceDTO;
import br.com.nivlabs.gp.models.enums.ActiveType;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Implementação de repositório customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class AttendanceRepositoryCustomImpl extends GenericCustomRepository<Attendance> implements AttendanceRepositoryCustom {

    @Override
    public Page<AttendanceDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        Page<Attendance> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

        List<AttendanceDTO> listOfDTO = new ArrayList<>();

        pageFromDatabase.forEach(attendance -> {
            AttendanceDTO attendanceConverted = new AttendanceDTO();
            attendanceConverted.setId(attendance.getId());
            attendanceConverted.setFirstName(attendance.getPatient().getPerson().getFirstName());
            attendanceConverted.setLastName(attendance.getPatient().getPerson().getLastName());
            attendanceConverted.setEntryCause(attendance.getReasonForEntry());
            attendanceConverted.setEntryDatetime(attendance.getDateTimeEntry());
            attendanceConverted.setPatientId(attendance.getPatient().getId());
            attendanceConverted.setSusNumber(attendance.getPatient().getSusNumber());
            attendanceConverted.setType(attendance.getEntryType());
            attendanceConverted.setPatientType(attendance.getPatient().getType());
            attendanceConverted.setIsFinished(attendance.getDateTimeExit() != null);
            attendanceConverted.setSectorDescription(attendance.getSector().getDescription());
            listOfDTO.add(attendanceConverted);
        });
        return new PageImpl<>(listOfDTO, pageSettings, pageFromDatabase.getTotalElements());
    }

    @Override
    protected List<IExpression<Attendance>> createRestrictions(CustomFilters customFilters) {
        AttendanceFilters filters = (AttendanceFilters) customFilters;

        List<IExpression<Attendance>> attributes = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            attributes.add((cb, from) -> cb.equal(from.get("patient").get("person").get("cpf"), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFirstName())) {
            attributes.add((cb, from) -> cb.like(from.get("patient").get("person").get("firstName"), filters.getFirstName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getLastName())) {
            attributes.add((cb, from) -> cb.like(from.get("patient").get("person").get("lastName"), filters.getLastName()));
        }
        if (filters.getPatientType() != null) {
            attributes.add((cb, from) -> cb.equal(from.get("patient").get("type"), filters.getPatientType()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSectorId()) && !StringUtils.isNullOrEmpty(StringUtils.getDigits(filters.getSectorId()))) {
            attributes.add((cb, from) -> cb.equal(from.get("sector").get("id"), filters.getSectorId()));
        }
        if (filters.getEntryType() != null) {
            attributes.add((cb, from) -> cb.equal(from.get("entryType"), filters.getEntryType()));
        }
        if (filters.getActiveType() != null) {
            if (filters.getActiveType() == ActiveType.ACTIVE)
                attributes.add((cb, from) -> cb.isNull(from.get("dateTimeExit")));
            else
                attributes.add((cb, from) -> cb.isNotNull(from.get("dateTimeExit")));
        }

        return attributes;
    }

}

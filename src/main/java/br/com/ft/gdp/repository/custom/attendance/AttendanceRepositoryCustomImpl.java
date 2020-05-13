package br.com.ft.gdp.repository.custom.attendance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.controller.filters.AttendanceFilters;
import br.com.ft.gdp.models.domain.Attendance;
import br.com.ft.gdp.models.dto.AttendanceDTO;
import br.com.ft.gdp.models.enums.ActiveType;
import br.com.ft.gdp.repository.custom.CustomFilters;
import br.com.ft.gdp.repository.custom.GenericCustomRepository;
import br.com.ft.gdp.repository.custom.IExpression;
import br.com.ft.gdp.util.StringUtils;

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
            attendanceConverted.setIsFinished(attendance.getDateTimeExit() != null);
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

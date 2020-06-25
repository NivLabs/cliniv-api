package br.com.nivlabs.gp.repository.custom.attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.controller.filters.AttendanceFilters;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.AttendanceEvent;
import br.com.nivlabs.gp.models.domain.Attendance_;
import br.com.nivlabs.gp.models.domain.Patient_;
import br.com.nivlabs.gp.models.domain.Person_;
import br.com.nivlabs.gp.models.domain.Sector_;
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
public class AttendanceRepositoryCustomImpl extends GenericCustomRepository<Attendance>
        implements AttendanceRepositoryCustom {

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
            if (!attendance.getEvents().isEmpty()) {
                List<AttendanceEvent> eventsWithSector = attendance.getEvents().stream()
                        .filter(event -> event.getRoomOrBed() != null).collect(Collectors.toList());
                if (!eventsWithSector.isEmpty()) {
                    eventsWithSector.sort((first, second) -> first.getId().compareTo(second.getId()));
                    attendanceConverted.setSectorDescription(eventsWithSector.get(0).getRoomOrBed().getDescription());
                }
            }

            listOfDTO.add(attendanceConverted);
        });
        return new PageImpl<>(listOfDTO, pageSettings, pageFromDatabase.getTotalElements());

    }

    @Override
    protected List<IExpression<Attendance>> createRestrictions(CustomFilters customFilters) {
        AttendanceFilters filters = (AttendanceFilters) customFilters;

        List<IExpression<Attendance>> attributes = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            attributes.add((cb, from) -> cb.equal(from.get(Attendance_.patient).get(Patient_.person).get(Person_.cpf), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFirstName())) {
            attributes.add((cb, from) -> cb.like(from.get(Attendance_.patient).get(Patient_.person).get(Person_.firstName),
                                                 filters.getFirstName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getLastName())) {
            attributes.add((cb, from) -> cb.like(from.get(Attendance_.patient).get(Patient_.person).get(Person_.lastName),
                                                 filters.getLastName()));
        }
        if (filters.getPatientType() != null) {
            attributes.add((cb, from) -> cb.equal(from.get(Attendance_.patient).get(Patient_.type), filters.getPatientType()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSectorId())
                && !StringUtils.isNullOrEmpty(StringUtils.getDigits(filters.getSectorId()))) {
            attributes.add((cb, from) -> cb.equal(from.get(Attendance_.currentSector).get(Sector_.id), filters.getSectorId()));
        }
        if (filters.getEntryType() != null) {
            attributes.add((cb, from) -> cb.equal(from.get(Attendance_.entryType), filters.getEntryType()));
        }
        if (filters.getActiveType() != null) {
            if (filters.getActiveType() == ActiveType.ACTIVE)
                attributes.add((cb, from) -> cb.isNull(from.get(Attendance_.dateTimeExit)));
            else
                attributes.add((cb, from) -> cb.isNotNull(from.get(Attendance_.dateTimeExit)));
        }

        return attributes;
    }

}

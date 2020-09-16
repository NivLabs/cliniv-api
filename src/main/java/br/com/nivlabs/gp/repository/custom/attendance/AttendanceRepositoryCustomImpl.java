package br.com.nivlabs.gp.repository.custom.attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.controller.filters.AttendanceFilters;
import br.com.nivlabs.gp.enums.ActiveType;
import br.com.nivlabs.gp.models.domain.Accomodation_;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.AttendanceEvent;
import br.com.nivlabs.gp.models.domain.Attendance_;
import br.com.nivlabs.gp.models.domain.Patient_;
import br.com.nivlabs.gp.models.domain.Person_;
import br.com.nivlabs.gp.models.dto.AttendanceDTO;
import br.com.nivlabs.gp.models.dto.SectorInfoDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.service.SectorService;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Implementação de repositório customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class AttendanceRepositoryCustomImpl extends GenericCustomRepository<Attendance> implements AttendanceRepositoryCustom {

    @Autowired
    private SectorService sectorService;

    @Override
    public Page<AttendanceDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        Page<Attendance> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

        List<AttendanceDTO> listOfDTO = new ArrayList<>();

        pageFromDatabase.forEach(attendance -> {
            AttendanceDTO attendanceConverted = new AttendanceDTO();
            attendanceConverted.setId(attendance.getId());
            attendanceConverted.setFullName(attendance.getPatient().getPerson().getFullName());
            attendanceConverted.setSocialName(attendance.getPatient().getPerson().getSocialName());
            attendanceConverted.setEntryCause(attendance.getReasonForEntry());
            attendanceConverted.setEntryDatetime(attendance.getEntryDateTime());
            attendanceConverted.setPatientId(attendance.getPatient().getId());
            attendanceConverted.setSusNumber(attendance.getPatient().getSusNumber());
            attendanceConverted.setType(attendance.getEntryType());
            attendanceConverted.setPatientType(attendance.getPatient().getType());
            attendanceConverted.setIsFinished(attendance.getExitDateTime() != null);
            attendanceConverted.setLevel(attendance.getLevel());

            // Pega o setor atual do atendimento
            if (!attendance.getEvents().isEmpty()) {
                List<AttendanceEvent> eventsWithSector = attendance.getEvents().stream()
                        .filter(event -> event.getAccomodation() != null).collect(Collectors.toList());
                if (!eventsWithSector.isEmpty()) {
                    eventsWithSector.sort((first, second) -> first.getId().compareTo(second.getId()));
                    attendanceConverted.setSectorDescription(eventsWithSector.get(0).getAccomodation().getDescription());
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
        if (!StringUtils.isNullOrEmpty(filters.getFullName())) {
            attributes.add((cb, from) -> cb.like(from.get(Attendance_.patient).get(Patient_.person).get(Person_.fullName),
                                                 filters.getFullName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSocialName())) {
            attributes.add((cb, from) -> cb.like(from.get(Attendance_.patient).get(Patient_.person).get(Person_.socialName),
                                                 filters.getSocialName()));
        }
        if (filters.getPatientType() != null) {
            attributes.add((cb, from) -> cb.equal(from.get(Attendance_.patient).get(Patient_.type), filters.getPatientType()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSectorId()) && !StringUtils.isNullOrEmpty(StringUtils.getDigits(filters.getSectorId()))) {
            attributes.add((cb, from) -> {
                In<Long> inClause = cb.in(from.get(Attendance_.currentAccomodation).get(Accomodation_.id));
                return getAccomodationsFormSectorId(inClause, filters.getSectorId());
            });
        }
        if (filters.getEntryType() != null) {
            attributes.add((cb, from) -> cb.equal(from.get(Attendance_.entryType), filters.getEntryType()));
        }
        if (filters.getActiveType() != null) {
            if (filters.getActiveType() == ActiveType.ACTIVE)
                attributes.add((cb, from) -> cb.isNull(from.get(Attendance_.exitDateTime)));
            else
                attributes.add((cb, from) -> cb.isNotNull(from.get(Attendance_.exitDateTime)));
        }

        return attributes;
    }

    /**
     * Busca todas as acomodações do setor filtrado
     * 
     * @param inClause
     * @param sectorId
     * @return
     */
    private Predicate getAccomodationsFormSectorId(In<Long> inClause, String sectorId) {
        SectorInfoDTO sector = sectorService.findInfoById(Long.parseLong(sectorId));
        sector.getListOfRoomsOrBeds().forEach(accomodation -> inClause.value(accomodation.getId()));
        return inClause;
    }

}

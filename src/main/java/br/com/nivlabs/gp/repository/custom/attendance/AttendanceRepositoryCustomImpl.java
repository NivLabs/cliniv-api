package br.com.nivlabs.gp.repository.custom.attendance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.controller.filters.AttendanceFilters;
import br.com.nivlabs.gp.enums.ActiveType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Accommodation_;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.Attendance_;
import br.com.nivlabs.gp.models.domain.Patient_;
import br.com.nivlabs.gp.models.domain.Person_;
import br.com.nivlabs.gp.models.domain.Sector_;
import br.com.nivlabs.gp.models.dto.AttendanceDTO;
import br.com.nivlabs.gp.models.dto.SectorInfoDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.service.SectorService;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Implementação de repositório customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class AttendanceRepositoryCustomImpl extends GenericCustomRepository<Attendance, AttendanceDTO>
        implements AttendanceRepositoryCustom {

    @Autowired
    private SectorService sectorService;

    @Override
    public Page<AttendanceDTO> resumedList(CustomFilters filters, Pageable pageSettings) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<AttendanceDTO> criteria = builder.createQuery(resumedClass);
        Root<Attendance> root = criteria.from(persistentClass);

        criteria.select(builder.construct(resumedClass,
                                          root.get(Attendance_.id),
                                          root.get(Attendance_.patient).get(Patient_.person).get(Person_.fullName),
                                          root.get(Attendance_.patient).get(Patient_.person).get(Person_.socialName),
                                          root.get(Attendance_.entryDateTime),
                                          root.get(Attendance_.reasonForEntry),
                                          root.get(Attendance_.entryType),
                                          root.get(Attendance_.patient).get(Patient_.id),
                                          root.get(Attendance_.currentAccommodation).get(Accommodation_.sector).get(Sector_.description),
                                          root.get(Attendance_.patient).get(Patient_.susNumber),
                                          root.get(Attendance_.level)));
        return getPage(filters, pageSettings, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Attendance> root) {
        if (!(customFilters instanceof AttendanceFilters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de atendimento");
        }
        AttendanceFilters filters = (AttendanceFilters) customFilters;
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            predicates.add(builder.equal(root.get(Attendance_.patient).get(Patient_.person).get(Person_.cpf), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFullName())) {
            predicates.add(builder.like(root.get(Attendance_.patient).get(Patient_.person).get(Person_.fullName),
                                        filters.getFullName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSocialName())) {
            predicates.add(builder.like(root.get(Attendance_.patient).get(Patient_.person).get(Person_.socialName),
                                        filters.getSocialName()));
        }
        if (filters.getPatientType() != null) {
            predicates.add(builder.equal(root.get(Attendance_.patient).get(Patient_.type), filters.getPatientType()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSectorId()) && !StringUtils.isNullOrEmpty(StringUtils.getDigits(filters.getSectorId()))) {
            In<Long> inClause = builder.in(root.get(Attendance_.currentAccommodation).get(Accommodation_.id));
            predicates.add(getAccommodationsFormSectorId(inClause, filters.getSectorId()));
        }
        if (filters.getEntryType() != null) {
            predicates.add(builder.equal(root.get(Attendance_.entryType), filters.getEntryType()));
        }
        if (filters.getActiveType() != null) {
            if (filters.getActiveType() == ActiveType.ACTIVE)
                predicates.add(builder.isNull(root.get(Attendance_.exitDateTime)));
            else
                predicates.add(builder.isNotNull(root.get(Attendance_.exitDateTime)));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    /**
     * Busca todas as acomodações do setor filtrado
     * 
     * @param inClause
     * @param sectorId
     * @return
     */
    private Predicate getAccommodationsFormSectorId(In<Long> inClause, String sectorId) {
        SectorInfoDTO sector = sectorService.findInfoById(Long.parseLong(sectorId));
        sector.getListOfRoomsOrBeds().forEach(accommodation -> inClause.value(accommodation.getId()));
        return inClause;
    }

}

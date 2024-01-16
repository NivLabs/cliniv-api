package br.com.nivlabs.cliniv.repository.custom.attendance;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.AttendanceFilters;
import br.com.nivlabs.cliniv.enums.ActiveType;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Accommodation_;
import br.com.nivlabs.cliniv.models.domain.Attendance;
import br.com.nivlabs.cliniv.models.domain.Attendance_;
import br.com.nivlabs.cliniv.models.domain.Patient_;
import br.com.nivlabs.cliniv.models.domain.Person;
import br.com.nivlabs.cliniv.models.domain.Person_;
import br.com.nivlabs.cliniv.models.domain.Responsible;
import br.com.nivlabs.cliniv.models.domain.Responsible_;
import br.com.nivlabs.cliniv.models.domain.Sector_;
import br.com.nivlabs.cliniv.models.dto.AttendanceDTO;
import br.com.nivlabs.cliniv.models.dto.SectorInfoDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.service.sector.SectorService;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Implementação de repositório customizado
 *
 * @author viniciosarodrigues
 */
public class AttendanceRepositoryCustomImpl extends GenericCustomRepository<Attendance, AttendanceDTO>
        implements AttendanceRepositoryCustom {

    @Autowired
    private SectorService sectorService;

    @Override
    public Page<AttendanceDTO> resumedList(CustomFilters filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<AttendanceDTO> criteria = builder.createQuery(AttendanceDTO.class);
        Root<Attendance> root = criteria.from(Attendance.class);
        Join<Responsible, Person> responsibleEntityJoin = root.join("professional", JoinType.LEFT)
                .join("person", JoinType.LEFT);

        criteria.select(builder.construct(AttendanceDTO.class,
                root.get("id"),
                root.get("patient").get("person").get("fullName"),
                root.get("patient").get("person").get("socialName"),
                root.get("entryDateTime"),
                root.get("exitDateTime"),
                root.get("reasonForEntry"),
                root.get("entryType"),
                root.get("patient").get("id"),
                root.get("currentAccommodation").get("sector").get("description"),
                root.get("patient").get("cnsNumber"),
                root.get("level"),
                responsibleEntityJoin.get("fullName")));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Attendance> root) {
        if (!(customFilters instanceof AttendanceFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de atendimento");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            predicates.add(builder.equal(root.get("patient").get("person").get("cpf"), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFullName())) {
            predicates.add(builder.like(root.get("patient").get("person").get("fullName"),
                    filters.getFullName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSocialName())) {
            predicates.add(builder.like(root.get("patient").get("person").get("socialName"),
                    filters.getSocialName()));
        }
        if (filters.getPatientType() != null) {
            predicates.add(builder.equal(root.get("patient").get("type"), filters.getPatientType()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSectorId()) && !StringUtils.isNullOrEmpty(StringUtils.getDigits(filters.getSectorId()))) {
            In<Long> inClause = builder.in(root.get("currentAccommodation").get("id"));
            predicates.add(getAccommodationsFormSectorId(inClause, filters.getSectorId()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getProfissionalId())) {
            predicates.add(builder.or(builder.equal(root.get("professional").get("id"),
                            Long.parseLong(filters.getProfissionalId())),
                    builder.isNull(root.get("professional").get("id"))));
        }
        if (filters.getEntryType() != null) {
            predicates.add(builder.equal(root.get("entryType"), filters.getEntryType()));
        }
        if (filters.getActiveType() != null) {
            if (filters.getActiveType() == ActiveType.ACTIVE)
                predicates.add(builder.isNull(root.get("exitDateTime")));
            else if (filters.getActiveType() == ActiveType.NOT_ACTIVE)
                predicates.add(builder.isNotNull(root.get("exitDateTime")));
        }

        return predicates.toArray(new Predicate[0]);
    }

    private Predicate getAccommodationsFormSectorId(In<Long> inClause, String sectorId) {
        SectorInfoDTO sector = sectorService.findInfoById(Long.parseLong(sectorId));
        sector.getListOfRoomsOrBeds().forEach(accommodation -> inClause.value(accommodation.getId()));
        return inClause;
    }

}

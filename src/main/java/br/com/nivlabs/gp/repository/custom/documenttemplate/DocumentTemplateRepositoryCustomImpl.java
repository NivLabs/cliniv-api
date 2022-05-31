package br.com.nivlabs.gp.repository.custom.documenttemplate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.controller.filters.DocumentTemplateFilter;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DocumentTemplate;
import br.com.nivlabs.gp.models.domain.DocumentTemplatePK_;
import br.com.nivlabs.gp.models.domain.DocumentTemplate_;
import br.com.nivlabs.gp.models.dto.DocumentTemplateDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório customizado para Templates de documentos
 * 
 * @author viniciosarodrigues
 *
 */
public class DocumentTemplateRepositoryCustomImpl extends GenericCustomRepository<DocumentTemplate, DocumentTemplateDTO>
        implements DocumentTemplateRepositoryCustom {

    @Override
    public Page<DocumentTemplateDTO> resumedList(CustomFilters filters, Pageable pageSettings) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentTemplateDTO> criteria = builder.createQuery(DocumentTemplateDTO.class);
        Root<DocumentTemplate> root = criteria.from(DocumentTemplate.class);

        criteria.select(builder.construct(DocumentTemplateDTO.class,
                                          root.get(DocumentTemplate_.pk).get(DocumentTemplatePK_.id),
                                          root.get(DocumentTemplate_.description)));
        return getPage(filters, pageSettings, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<DocumentTemplate> root) {
        if (!(customFilters instanceof DocumentTemplateFilter filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de templates de documentos");
        }

        List<Predicate> predicates = new ArrayList<>();

        if (filters.getUserId() != null) {
            predicates.add(builder.equal(root.get(DocumentTemplate_.pk).get(DocumentTemplatePK_.userId), filters.getUserId()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getDescription())) {
            predicates.add(builder.like(root.get(DocumentTemplate_.description), filters.getDescription()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}

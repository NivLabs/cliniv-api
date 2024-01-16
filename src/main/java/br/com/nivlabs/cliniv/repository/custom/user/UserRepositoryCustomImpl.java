package br.com.nivlabs.cliniv.repository.custom.user;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.UserFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Person_;
import br.com.nivlabs.cliniv.models.domain.UserApplication;
import br.com.nivlabs.cliniv.models.domain.UserApplication_;
import br.com.nivlabs.cliniv.models.dto.UserDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Repositório de usuário customizado
 *
 * @author viniciosarodrigues
 */
public class UserRepositoryCustomImpl extends GenericCustomRepository<UserApplication, UserDTO> implements UserRepositoryCustom {

    @Override
    public Page<UserDTO> resumedList(CustomFilters filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<UserDTO> criteria = builder.createQuery(UserDTO.class);
        Root<UserApplication> root = criteria.from(UserApplication.class);

        criteria.select(builder.construct(UserDTO.class,
                root.get("id"),
                root.get("person").get("fullName"),
                root.get("person").get("socialName"),
                root.get("person").get("cpf"),
                root.get("person").get("bornDate"),
                root.get("person").get("principalNumber"),
                root.get("person").get("gender"),
                root.get(UserApplication_.userName)));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<UserApplication> root) {
        if (!(customFilters instanceof UserFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de usuários");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getUserName())) {
            predicates.add(builder.like(root.get("userName"), filters.getUserName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            predicates.add(builder.equal(root.get("person").get("cpf"), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFullName())) {
            predicates.add(builder.like(root.get("person").get("fullName"), filters.getFullName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSocialName())) {
            predicates.add(builder.like(root.get("person").get("socialName"), filters.getSocialName()));
        }
        if (filters.getGender() != null) {
            predicates.add(builder.equal(root.get("person").get("gender"), filters.getGender()));
        }

        return predicates.toArray(new Predicate[0]);
    }
}

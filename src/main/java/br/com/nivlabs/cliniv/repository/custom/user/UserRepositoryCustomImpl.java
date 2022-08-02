package br.com.nivlabs.cliniv.repository.custom.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.UserFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.UserApplication;
import br.com.nivlabs.cliniv.models.dto.UserDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;
import br.com.nivlabs.cliniv.models.domain.Person_;
import br.com.nivlabs.cliniv.models.domain.UserApplication_;

/**
 * Repositório de usuário customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class UserRepositoryCustomImpl extends GenericCustomRepository<UserApplication, UserDTO> implements UserRepositoryCustom {

    @Override
    public Page<UserDTO> resumedList(CustomFilters filters, Pageable pageSettings) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<UserDTO> criteria = builder.createQuery(UserDTO.class);
        Root<UserApplication> root = criteria.from(UserApplication.class);

        criteria.select(builder.construct(UserDTO.class,
                                          root.get(UserApplication_.id),
                                          root.get(UserApplication_.person).get(Person_.fullName),
                                          root.get(UserApplication_.person).get(Person_.socialName),
                                          root.get(UserApplication_.person).get(Person_.cpf),
                                          root.get(UserApplication_.person).get(Person_.bornDate),
                                          root.get(UserApplication_.person).get(Person_.principalNumber),
                                          root.get(UserApplication_.person).get(Person_.gender),
                                          root.get(UserApplication_.userName)));
        return getPage(filters, pageSettings, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<UserApplication> root) {
        if (!(customFilters instanceof UserFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de usuários");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getUserName())) {
            predicates.add(builder.like(root.get(UserApplication_.userName), filters.getUserName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            predicates.add(builder.equal(root.get(UserApplication_.person).get(Person_.cpf), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFullName())) {
            predicates.add(builder.like(root.get(UserApplication_.person).get(Person_.fullName), filters.getFullName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSocialName())) {
            predicates.add(builder.like(root.get(UserApplication_.person).get(Person_.socialName), filters.getSocialName()));
        }
        if (filters.getGender() != null) {
            predicates.add(builder.equal(root.get(UserApplication_.person).get(Person_.gender), filters.getGender()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}

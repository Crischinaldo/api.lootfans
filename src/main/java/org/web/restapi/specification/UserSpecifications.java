package org.web.restapi.specification;

import org.springframework.data.jpa.domain.Specification;
import org.web.restapi.model.User;
import org.web.restapi.model.User_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecifications {

    public static Specification<User> getUsersByFirstName(String firstName) {
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(User_.firstName), firstName);
            }
        };
        return specification;
    }

    public static Specification<User> getUserByID(Long id) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(User_.id), id);
            }
        };
    }

}

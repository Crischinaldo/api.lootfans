package de.lootfans.restapi.specification;

import org.springframework.data.jpa.domain.Specification;
import de.lootfans.restapi.model.User;
import de.lootfans.restapi.model.User_;

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

    public static Specification<User> getUserByUserName(String userName) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(User_.userName), userName);
            }
        };
    }

    public static Specification<User> userExists(String userName, String email) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.or(criteriaBuilder.like(root.get(User_.userName), userName),
                        criteriaBuilder.like(root.get(User_.email), email));
            }
        };
    }

}

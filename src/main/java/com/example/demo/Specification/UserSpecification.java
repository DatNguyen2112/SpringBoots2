package com.example.demo.Specification;

import com.example.demo.Data.Entity.User;
import com.example.demo.Data.Request.UserRequest;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSpecification {
    public Specification<User> getUsers(UserRequest request) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("email"), request.getEmail()));
            }
            if (request.getName() != null && !request.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + request.getName().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}

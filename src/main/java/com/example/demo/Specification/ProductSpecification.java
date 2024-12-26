package com.example.demo.Specification;

import com.example.demo.Data.Entity.Product;
import com.example.demo.Data.Request.ProductRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductSpecification {
    public Specification<Product> getProducts(ProductRequest request) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getCode() != null && !request.getCode().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("code"), request.getCode()));
            }
            if (request.getName() != null && !request.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + request.getName().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}

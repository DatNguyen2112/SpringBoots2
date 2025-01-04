package com.example.demo.Repository;

import com.example.demo.Data.Entity.Product;
import com.example.demo.Modal.ProductCreateModal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Product save(ProductCreateModal modal);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    List<Product> findAll(Specification<Product> spec);
    Product getProductById(Long id);

    // Check code ton tai trong db
    @Query("select case when count(c)> 0 then true else false end from Product c where lower(c.code) like lower(:code)")
    boolean existsCodeInDatabase(@Param("code") String code);
}

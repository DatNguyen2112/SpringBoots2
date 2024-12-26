package com.example.demo.controller;

import com.common.demo.PaginationResponse;
import com.example.demo.Data.Entity.Product;
import com.example.demo.Data.Request.ProductRequest;
import com.example.demo.Data.Request.UserRequest;
import com.example.demo.Data.Response.ProductResponse;
import com.example.demo.Data.Response.ResponseHandler;
import com.example.demo.Modal.ProductCreateModal;
import com.example.demo.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> Create(@RequestBody ProductCreateModal model){
        ProductResponse productResult = productService.createProduct(model);
        return ResponseHandler.generateResponse("Tạo sản phẩm thành công", HttpStatus.OK, productResult);
    }

    @PostMapping("/getProducts")
    public ResponseEntity<Object> getUsersList(@RequestBody ProductRequest request) {
        PaginationResponse obj = productService.getProductList(request);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, obj );
    }
}

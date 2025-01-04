package com.example.demo.controller;

import com.common.demo.PaginationResponse;
import com.example.demo.Data.Entity.Product;
import com.example.demo.Data.Request.ProductRequest;
import com.example.demo.Data.Response.ProductResponse;
import com.example.demo.Data.Response.Response;
import com.example.demo.Modal.ProductCreateModal;
import com.example.demo.Service.ProductService;
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
    public Response<ProductResponse> Create(@RequestBody ProductCreateModal model){
        return productService.createProduct(model);
    }

    @PostMapping("/getProducts")
    public PaginationResponse<Product> getProductsList(@RequestBody ProductRequest request) {
        return productService.getProductList(request);
    }

    @GetMapping("/product/{id}")
    public Response<ProductResponse> getProductsById(@PathVariable Long id) {
       return productService.getProduct(id);
    }

    @PutMapping("/product/update/{id}")
    public Response<ProductResponse> updateProductsById(@PathVariable Long id, @RequestBody ProductCreateModal modal) {
        return productService.updateProduct(id, modal);
    }
}

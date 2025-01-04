package com.example.demo.Service;

import com.common.demo.PaginationResponse;
import com.example.demo.Data.Entity.Product;
import com.example.demo.Data.Request.ProductRequest;
import com.example.demo.Data.Response.ProductResponse;
import com.example.demo.Data.Response.Response;
import com.example.demo.Modal.ProductCreateModal;

public interface ProductService {
    Response<ProductResponse> createProduct(ProductCreateModal modal);
    PaginationResponse<Product> getProductList(ProductRequest productRequest);
    Response<ProductResponse> getProduct(long id);
    Response<ProductResponse> updateProduct(long id, ProductCreateModal modal);
}

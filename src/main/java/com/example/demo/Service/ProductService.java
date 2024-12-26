package com.example.demo.Service;

import com.common.demo.PaginationResponse;
import com.example.demo.Data.Request.ProductRequest;
import com.example.demo.Data.Response.ProductResponse;
import com.example.demo.Modal.ProductCreateModal;

public interface ProductService {
    ProductResponse createProduct(ProductCreateModal modal);
    PaginationResponse getProductList(ProductRequest productRequest);
    ProductResponse getProduct(long id);
}

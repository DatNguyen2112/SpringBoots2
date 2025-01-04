package com.example.demo.Impl;

import com.common.demo.PaginationResponse;
import com.example.demo.Data.Entity.Product;
import com.example.demo.Data.Request.ProductRequest;
import com.example.demo.Data.Response.ProductResponse;
import com.example.demo.Data.Response.Response;
import com.example.demo.Modal.ProductCreateModal;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.ProductService;
import com.example.demo.Specification.ProductSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ModelMapper modelMapper;
    private ProductRepository productRepository;
    private ProductSpecification productSpecification;

    // check neu khong co pageNumber thi set default bang 1
    @Value("${pagination.page.size.default}")
    private Integer defaultPageSize;

    public ProductServiceImpl(ModelMapper modelMapper,
                              ProductRepository productRepository,
                              ProductSpecification productSpecification) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.productSpecification = productSpecification;
    }

    @Override
    public Response<ProductResponse> createProduct(ProductCreateModal modal) {
        try {
            Product product = modelMapper.map(modal, Product.class);

            boolean IsExistCode = productRepository.existsCodeInDatabase(modal.getCode());

            if (IsExistCode) {
                return new Response<ProductResponse>(HttpStatus.BAD_REQUEST, "Mã sản phẩm đã tồn tại trong hệ thống", false, null);
            }

            List<Product> productList = productRepository.findAll();

            product.setName(product.getName());
            product.setDescription(product.getDescription());
            product.setPrice(product.getPrice());
            product.setQuantity(product.getQuantity());
            productRepository.save(product);

            //mapper
            ProductResponse resultProductEntity = modelMapper.map(product, ProductResponse.class);

            return new Response<ProductResponse>(HttpStatus.OK, "Thêm mới thành công", true, resultProductEntity);
        } catch (Exception ex) {
            return new Response<ProductResponse>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), false, null);
        }
    }

    @Override
    public PaginationResponse<Product> getProductList(ProductRequest productRequest) {
        List<Product> list = null;
        Page<Product> pages = null;

        if (productRequest.getPageNumber() == 0) {
            pages = new PageImpl<>(productRepository.findAll(productSpecification.getProducts(productRequest)));
        } else {
            if (productRequest.getPageSize() == 0) {
                productRequest.setPageSize(defaultPageSize);
            }
            Pageable paging = PageRequest.of(productRequest.getPageNumber() - 1, productRequest.getPageSize());
            pages = productRepository.findAll(productSpecification.getProducts(productRequest), paging);
        }
        if (pages != null && pages.getContent() != null) {
            list = pages.getContent();
            if (list != null && list.size() > 0) {
                PaginationResponse respList = new PaginationResponse();
                respList.setPages(pages.getTotalPages());
                respList.setCount(pages.getTotalElements());
                respList.setCurrentPage(pages.getNumber() + 1);
                respList.setItems(new ArrayList<Product>());
                for (Product products : list) {
                    ProductResponse obj = new ProductResponse();
                    obj.setId(products.getId());
                    obj.setName(products.getName());
                    obj.setPrice(products.getPrice());
                    obj.setQuantity(products.getQuantity());
                    obj.setCode(products.getCode());
                    respList.getItems().add(obj);
                }

                return respList;
            }
        }
        return null;
    }

    @Override
    public Response<ProductResponse> getProduct(long id) {
        try {
            Product productEntity = productRepository.getProductById(id);

            if (productEntity == null) {
                return new Response<ProductResponse>(HttpStatus.BAD_REQUEST, "Khong tim thay san pham nao", false, null);
            }

            ProductResponse productEntityMapper = modelMapper.map(productEntity, ProductResponse.class);

            return new Response<ProductResponse>(HttpStatus.OK, "", true, productEntityMapper);
        } catch (Exception ex) {
            return new Response<ProductResponse>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), false, null);
        }
    }

    @Override
    public Response<ProductResponse> updateProduct(long id, ProductCreateModal modal) {
        try {
            Product productEntity = productRepository.getProductById(id);

            if (productEntity == null) {
                return new Response<ProductResponse>(HttpStatus.BAD_REQUEST, "Khong tim thay san pham", false, null);
            }

            ProductResponse productEntityMapper = modelMapper.map(productEntity, ProductResponse.class);

            productEntityMapper.setName(modal.getName());;
            productEntityMapper.setPrice(modal.getPrice());
            productEntityMapper.setCode(modal.getCode());
            productEntityMapper.setQuantity(modal.getQuantity());
            productEntityMapper.setPrice(modal.getPrice());
            productRepository.save(productEntity);

            return new Response<ProductResponse>(HttpStatus.OK, "Chinh sua san pham thanh cong", true, productEntityMapper);
        } catch (Exception ex) {
            return new Response<ProductResponse>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), false, null);
        }
    }
}

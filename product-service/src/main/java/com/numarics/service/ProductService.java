package com.numarics.service;

import com.numarics.dto.ProductDTO;
import com.numarics.model.ProductEntity;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductEntity createProduct(ProductDTO productDTO);

    ProductEntity getProductById(Long id);

    Page<ProductEntity> searchProducts(String name, String description, String category, int page, int size);

    ProductEntity updateProduct(Long id, ProductDTO productDetails);

    void deleteProduct(Long id);
}

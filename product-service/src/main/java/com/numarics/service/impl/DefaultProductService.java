package com.numarics.service.impl;

import com.numarics.dto.ProductDTO;
import com.numarics.enums.Category;
import com.numarics.model.ProductEntity;
import com.numarics.repository.ProductRepository;
import com.numarics.service.ProductService;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductEntity createProduct(ProductDTO productDTO) {
        validateIfProductExists(productDTO.getName(), productDTO.getCategory(), productDTO.getPrice());
        ProductEntity product = ProductEntity.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .category(productDTO.getCategory())
                .price(productDTO.getPrice())
                .build();
        return productRepository.save(product);
    }

    @Override
    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    }

    @Override
    public Page<ProductEntity> searchProducts(String name, String description, String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.searchProducts(name, description, category, pageable);
    }

    @Override
    public ProductEntity updateProduct(Long id, ProductDTO productDTO) {
        ProductEntity product = getProductById(id);
        if (isProductDataChanged(product, productDTO)) {
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setCategory(productDTO.getCategory());
            product.setPrice(productDTO.getPrice());
            return productRepository.save(product);
        }
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        ProductEntity product = getProductById(id);
        if (Objects.nonNull(product)) {
            productRepository.deleteById(id);
        }
    }

    private void validateIfProductExists(String name, Category category, BigDecimal price) {
        if (productRepository.existsByNameAndCategoryAndPrice(name, category, price)) {
            throw new IllegalArgumentException("Product with this name, category and price already exists!");
        }
    }
    private boolean isProductDataChanged(ProductEntity originalProduct, ProductDTO newProductData) {
        return !originalProduct.getName().equals(newProductData.getName()) ||
                !Objects.equals(originalProduct.getDescription(), newProductData.getDescription()) ||
                !originalProduct.getCategory().equals(newProductData.getCategory()) ||
                !originalProduct.getPrice().equals(newProductData.getPrice());
    }
}

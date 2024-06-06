package com.numarics.service;

import com.numarics.dto.ProductDTO;
import com.numarics.model.ProductEntity;
import org.springframework.data.domain.Page;

/**
 * Service interface for managing products
 */
public interface ProductService {

    /**
     * Creates a new product
     *
     * @param productDTO The product DTO containing information about the product to be created
     * @return The created product entity
     */
    ProductEntity createProduct(ProductDTO productDTO);

    /**
     * Retrieves a product by its ID
     *
     * @param id The ID of the product to fetch
     * @return The product entity with the specified ID
     */
    ProductEntity getProductById(Long id);

    /**
     * Products based on the provided criteria
     *
     * @param name        The name of the product to search for
     * @param description The description of the product to search for
     * @param category    The category of the product to search for
     * @param page        The page number of the search results
     * @param size        The size of each page in the search results
     * @return A page containing the search results
     */
    Page<ProductEntity> searchProducts(String name, String description, String category, int page, int size);

    /**
     * Updates an existing product.
     *
     * @param id             The ID of the product to update
     * @param productDetails The product DTO containing new data
     * @return The updated product entity
     */
    ProductEntity updateProduct(Long id, ProductDTO productDetails);

    /**
     * Deletes a product by its ID
     *
     * @param id The ID of the product to delete
     */
    void deleteProduct(Long id);
}

package com.numarics.controller;

import static com.numarics.util.ApiDocumentation.CREATE_PRODUCT_OPERATION_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.CREATE_PRODUCT_OPERATION_SUMMARY;
import static com.numarics.util.ApiDocumentation.CREATE_PRODUCT_RESPONSE_200_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.CREATE_PRODUCT_RESPONSE_400_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.DELETE_PRODUCT_OPERATION_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.DELETE_PRODUCT_OPERATION_SUMMARY;
import static com.numarics.util.ApiDocumentation.DELETE_PRODUCT_RESPONSE_200_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.GET_PRODUCT_BY_ID_OPERATION_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.GET_PRODUCT_BY_ID_OPERATION_SUMMARY;
import static com.numarics.util.ApiDocumentation.GET_PRODUCT_BY_ID_RESPONSE_200_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.PRODUCTS_TAG;
import static com.numarics.util.ApiDocumentation.PRODUCT_ID_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.RESPONSE_401_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.RESPONSE_403_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.RESPONSE_404_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.SEARCH_PRODUCTS_OPERATION_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.SEARCH_PRODUCTS_OPERATION_SUMMARY;
import static com.numarics.util.ApiDocumentation.SEARCH_PRODUCTS_RESPONSE_200_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.UPDATE_PRODUCT_OPERATION_DESCRIPTION;
import static com.numarics.util.ApiDocumentation.UPDATE_PRODUCT_OPERATION_SUMMARY;
import static com.numarics.util.ApiDocumentation.UPDATE_PRODUCT_RESPONSE_200_DESCRIPTION;

import com.numarics.dto.ProductDTO;
import com.numarics.model.ProductEntity;
import com.numarics.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = CREATE_PRODUCT_OPERATION_SUMMARY,
            description = CREATE_PRODUCT_OPERATION_DESCRIPTION,
            tags = {PRODUCTS_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = CREATE_PRODUCT_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = "400", description = CREATE_PRODUCT_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = "401", description = RESPONSE_401_DESCRIPTION),
            @ApiResponse(responseCode = "403", description = RESPONSE_403_DESCRIPTION),
    })
    public ResponseEntity<ProductEntity> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductEntity createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/{id}")
    @Operation(summary = GET_PRODUCT_BY_ID_OPERATION_SUMMARY,
            description = GET_PRODUCT_BY_ID_OPERATION_DESCRIPTION,
            tags = {PRODUCTS_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = GET_PRODUCT_BY_ID_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = "404", description = RESPONSE_404_DESCRIPTION),
            @ApiResponse(responseCode = "401", description = RESPONSE_401_DESCRIPTION),
            @ApiResponse(responseCode = "403", description = RESPONSE_403_DESCRIPTION),
    })
    public ResponseEntity<ProductEntity> getProductById(@Parameter(description = PRODUCT_ID_DESCRIPTION) @PathVariable Long id) {
        ProductEntity product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = SEARCH_PRODUCTS_OPERATION_SUMMARY,
            description = SEARCH_PRODUCTS_OPERATION_DESCRIPTION,
            tags = {PRODUCTS_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = SEARCH_PRODUCTS_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = "401", description = RESPONSE_401_DESCRIPTION),
            @ApiResponse(responseCode = "403", description = RESPONSE_403_DESCRIPTION),
    })
    @GetMapping("/search")
    public ResponseEntity<Page<ProductEntity>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProductEntity> products = productService.searchProducts(name, description, category, page, size);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @Operation(summary = UPDATE_PRODUCT_OPERATION_SUMMARY,
            description = UPDATE_PRODUCT_OPERATION_DESCRIPTION,
            tags = {PRODUCTS_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = UPDATE_PRODUCT_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = "404", description = RESPONSE_404_DESCRIPTION),
            @ApiResponse(responseCode = "401", description = RESPONSE_401_DESCRIPTION),
            @ApiResponse(responseCode = "403", description = RESPONSE_403_DESCRIPTION),
    })
    public ResponseEntity<ProductEntity> updateProduct(
            @Parameter(description = PRODUCT_ID_DESCRIPTION) @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        ProductEntity updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = DELETE_PRODUCT_OPERATION_SUMMARY,
            description = DELETE_PRODUCT_OPERATION_DESCRIPTION,
            tags = {PRODUCTS_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = DELETE_PRODUCT_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = "404", description = RESPONSE_404_DESCRIPTION),
            @ApiResponse(responseCode = "401", description = RESPONSE_401_DESCRIPTION),
            @ApiResponse(responseCode = "403", description = RESPONSE_403_DESCRIPTION),
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = PRODUCT_ID_DESCRIPTION) @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

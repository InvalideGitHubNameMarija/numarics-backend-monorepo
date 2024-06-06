package com.numarics.client;

import com.numarics.client.dto.ProductDTO;
import com.numarics.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", configuration = FeignClientConfig.class)
public interface ProductServiceClient {

    @GetMapping("/api/v1/products/{id}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id);
}

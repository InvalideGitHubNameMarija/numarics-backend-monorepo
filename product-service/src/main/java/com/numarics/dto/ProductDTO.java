package com.numarics.dto;

import com.numarics.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {

    @NotBlank(message = "Product name can't be empty.")
    private String name;

    private String description;

    @NotNull(message = "Product category can't be null.")
    private Category category;

    @NotNull(message = "Product price can't be null.")
    @Positive(message = "Product price must be a positive number.")
    private BigDecimal price;
}

package com.numarics.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @NotEmpty(message = "List of product IDs cannot be empty")
    @NotNull(message = "List of product IDs cannot be null")
    private List<Long> productIds;
}

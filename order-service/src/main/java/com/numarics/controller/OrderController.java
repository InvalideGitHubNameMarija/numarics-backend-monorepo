package com.numarics.controller;

import static com.numarics.util.OrderApiDocumentation.*;

import com.numarics.dto.OrderDTO;
import com.numarics.model.OrderEntity;
import com.numarics.service.AuthorizationService;
import com.numarics.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final AuthorizationService authorizationService;

    @PostMapping
    @Operation(summary = CREATE_ORDER_SUMMARY,
            description = CREATE_ORDER_DESCRIPTION,
            tags = {ORDERS_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_200, description = CREATE_ORDER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_400, description = CREATE_ORDER_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_401, description = RESPONSE_401_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_403, description = RESPONSE_403_DESCRIPTION)
    })
    public OrderEntity createOrder(@Valid @RequestBody OrderDTO orderDTO, Principal principal) {
        String userId = principal.getName();
        return orderService.createOrder(orderDTO, userId);
    }

    @GetMapping
    @Operation(summary = GET_ALL_ORDERS_SUMMARY,
            description = GET_ALL_ORDERS_DESCRIPTION,
            tags = {ORDERS_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_200, description = GET_ALL_ORDERS_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_401, description = RESPONSE_401_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_403, description = RESPONSE_403_DESCRIPTION)
    })
    public Page<OrderEntity> getAllOrders(
            Principal principal,
            @RequestParam(defaultValue = PAGE_DEFAULT) int page,
            @RequestParam(defaultValue = SIZE_DEFAULT) int size) {
        String userId = principal.getName();
        if(authorizationService.isAdmin(userId)){
            return orderService.getAllOrders(page, size);
        } else {
            return orderService.getOrdersByUserId(Long.parseLong(userId), page, size);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = GET_ORDER_BY_ID_SUMMARY,
            description = GET_ORDER_BY_ID_DESCRIPTION,
            tags = {ORDERS_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_200, description = GET_ORDER_BY_ID_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_401, description = RESPONSE_401_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_403, description = RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_404, description = RESPONSE_404_DESCRIPTION)
    })
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {
        Optional<OrderEntity> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user")
    @Operation(summary = GET_ORDERS_BY_USER_ID_SUMMARY,
            description = GET_ORDERS_BY_USER_ID_DESCRIPTION,
            tags = {ORDERS_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_200, description = GET_ORDERS_BY_USER_ID_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_401, description = RESPONSE_401_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_403, description = RESPONSE_403_DESCRIPTION)
    })
    public Page<OrderEntity> getOrdersByUserId(
            @RequestParam(name = USER_ID_PARAM, required = false) Long userId,
            @RequestParam(defaultValue = PAGE_DEFAULT) int page,
            @RequestParam(defaultValue = SIZE_DEFAULT) int size) {
        return orderService.getOrdersByUserId(userId, page, size);
    }

    @PutMapping("/{id}")
    @Operation(summary = UPDATE_ORDER_SUMMARY,
            description = UPDATE_ORDER_DESCRIPTION,
            tags = {ORDERS_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_200, description = UPDATE_ORDER_RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_400, description = UPDATE_ORDER_RESPONSE_400_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_401, description = RESPONSE_401_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_403, description = RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_404, description = UPDATE_ORDER_RESPONSE_404_DESCRIPTION)
    })
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        OrderEntity updatedOrder = orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = DELETE_ORDER_SUMMARY,
            description = DELETE_ORDER_DESCRIPTION,
            tags = {ORDERS_TAG})
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_204, description = DELETE_ORDER_RESPONSE_204_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_401, description = RESPONSE_401_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_403, description = RESPONSE_403_DESCRIPTION),
            @ApiResponse(responseCode = STATUS_404, description = DELETE_ORDER_RESPONSE_404_DESCRIPTION)
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}

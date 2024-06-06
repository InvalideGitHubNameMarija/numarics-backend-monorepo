package com.numarics.service.impl;

import com.numarics.client.ProductServiceClient;
import com.numarics.dto.OrderDTO;
import com.numarics.exception.ServiceUnavailableException;
import com.numarics.model.OrderEntity;
import com.numarics.repository.OrderRepository;
import com.numarics.service.OrderService;
import feign.FeignException;
import jakarta.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;

    @Override
    public OrderEntity createOrder(OrderDTO orderDTO, String userId) {
        Long id = validateUserId(userId);
        validateProductIds(orderDTO.getProductIds());

        OrderEntity order = OrderEntity.builder()
                .userId(id)
                .productIds(orderDTO.getProductIds())
                .build();
        return orderRepository.save(order);
    }

    @Override
    public Optional<OrderEntity> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Page<OrderEntity> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }

    @Override
    public OrderEntity updateOrder(Long id, OrderDTO orderDTO) {
        validateIfOrderExists(id);

        Optional<OrderEntity> optionalOrder = getOrderById(id);
        if (optionalOrder.isPresent()) {
            OrderEntity order = optionalOrder.get();
            if (isOrderDataChanged(order, orderDTO)) {
                validateProductIds(orderDTO.getProductIds());
                order.setProductIds(orderDTO.getProductIds());
                return orderRepository.save(order);
            }
            return order;
        }
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        validateIfOrderExists(id);
        orderRepository.deleteById(id);
    }

    @Override
    public Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findByUserId(userId, pageable);
    }

    public void validateIfOrderExists(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new NotFoundException("Order not found with id: " + id);
        }
    }

    private Long validateUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new BadRequestException("User ID is null or empty");
        }
        try {
            return Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Invalid user ID: " + userId);
        }
    }

    private void validateProductIds(List<Long> productIds) {
        for (Long productId : productIds) {
            log.info("Validating product id: {}", productId);
            try {
                productServiceClient.getProductById(productId);
            } catch (FeignException e) {
                log.error("FeignException: {}", e.getMessage());
                if (e.status() == HttpStatus.UNAUTHORIZED.value()) {
                    throw new RuntimeException("Unauthorized: Invalid token");
                }
                if (e.status() == HttpStatus.NOT_FOUND.value()) {
                    throw new NotFoundException("Product not found with id: " + productId);
                }
                if (e.status() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
                    throw new ServiceUnavailableException("Service unavailable: Unable to reach product service");
                }
            } catch (Exception e) {
                log.error("Exception: {}", e.getMessage());
                throw new RuntimeException("Internal server error");
            }
        }
    }

    private boolean isOrderDataChanged(OrderEntity originalOrder, OrderDTO newOrderData) {
        List<Long> originalProductIds = originalOrder.getProductIds();
        List<Long> newProductIds = newOrderData.getProductIds();

        if (originalProductIds.size() != newProductIds.size()) {
            return true;
        }

        for (Long newProductId : newProductIds) {
            if (!originalProductIds.contains(newProductId)) {
                return true;
            }
        }
        return false;
    }
}


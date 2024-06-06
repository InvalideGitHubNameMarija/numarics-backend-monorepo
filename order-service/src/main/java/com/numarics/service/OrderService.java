package com.numarics.service;

import com.numarics.dto.OrderDTO;
import com.numarics.model.OrderEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface OrderService {

    OrderEntity createOrder(OrderDTO order, String userId);

    Optional<OrderEntity> getOrderById(Long id);

    Page<OrderEntity> getAllOrders(int page, int size);

    Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size);

    OrderEntity updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);
}

package com.numarics.service;

import com.numarics.dto.OrderDTO;
import com.numarics.model.OrderEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;

/**
 * Service interface for managing orders
 */
public interface OrderService {

    /**
     * Creates a new order
     *
     * @param order  The order DTO containing information about the order to be created
     * @param userId The ID of the user who is creating the order
     * @return The created OrderEntity
     */
    OrderEntity createOrder(OrderDTO order, String userId);

    /**
     * Retrieves an order by ID
     *
     * @param id The ID of the order
     * @return An Optional containing the found OrderEntity, or empty if not found
     */
    Optional<OrderEntity> getOrderById(Long id);

    /**
     * Retrieves a paginated list of all orders
     *
     * @param page The page number to retrieve
     * @param size The number of orders per page
     * @return A Page containing the orders
     */
    Page<OrderEntity> getAllOrders(int page, int size);

    /**
     * Retrieves a paginated list of orders by a specific user ID
     *
     * @param userId The ID of the user
     * @param page   The page number to retrieve
     * @param size   The number of orders per page
     * @return A Page containing the orders
     */
    Page<OrderEntity> getOrdersByUserId(Long userId, int page, int size);

    /**
     * Updates an existing order.
     *
     * @param id       The ID of the order to update
     * @param orderDTO The new order DTO containing new data
     * @return the updated OrderEntity
     */
    OrderEntity updateOrder(Long id, OrderDTO orderDTO);

    /**
     * Deletes an order by its ID.
     *
     * @param id The ID of the order to delete
     */
    void deleteOrder(Long id);
}

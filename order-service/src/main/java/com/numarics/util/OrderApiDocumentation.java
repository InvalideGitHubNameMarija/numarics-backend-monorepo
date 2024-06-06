package com.numarics.util;

public class OrderApiDocumentation {
    public static final String ORDERS_TAG = "Orders";
    public static final String USER_ID_PARAM = "userId";

    public static final String STATUS_200 = "200";
    public static final String STATUS_204 = "204";
    public static final String STATUS_400 = "400";
    public static final String STATUS_401 = "401";
    public static final String STATUS_403 = "403";
    public static final String STATUS_404 = "404";

    public static final String PAGE_DEFAULT = "0";
    public static final String SIZE_DEFAULT = "10";

    public static final String CREATE_ORDER_SUMMARY = "Create a new order";
    public static final String CREATE_ORDER_DESCRIPTION = "This endpoint allows for creating a new order.";
    public static final String CREATE_ORDER_RESPONSE_200_DESCRIPTION = "Order successfully created";
    public static final String CREATE_ORDER_RESPONSE_400_DESCRIPTION = "Validation error occurred during order creation";

    public static final String GET_ALL_ORDERS_SUMMARY = "Get all orders";
    public static final String GET_ALL_ORDERS_DESCRIPTION = "This endpoint returns a list of all orders.";
    public static final String GET_ALL_ORDERS_RESPONSE_200_DESCRIPTION = "Orders successfully retrieved";

    public static final String GET_ORDER_BY_ID_SUMMARY = "Get order by ID";
    public static final String GET_ORDER_BY_ID_DESCRIPTION = "This endpoint returns an order by its ID.";
    public static final String GET_ORDER_BY_ID_RESPONSE_200_DESCRIPTION = "Order successfully retrieved";

    public static final String GET_ORDERS_BY_USER_ID_SUMMARY = "Get orders by user ID";
    public static final String GET_ORDERS_BY_USER_ID_DESCRIPTION = "This endpoint returns a list of orders for a specific user.";
    public static final String GET_ORDERS_BY_USER_ID_RESPONSE_200_DESCRIPTION = "Orders successfully retrieved";

    public static final String UPDATE_ORDER_SUMMARY = "Update an order";
    public static final String UPDATE_ORDER_DESCRIPTION = "This endpoint allows for updating an existing order.";
    public static final String UPDATE_ORDER_RESPONSE_200_DESCRIPTION = "Order successfully updated";
    public static final String UPDATE_ORDER_RESPONSE_400_DESCRIPTION = "Validation error occurred during order update";
    public static final String UPDATE_ORDER_RESPONSE_404_DESCRIPTION = "Order not found";

    public static final String DELETE_ORDER_SUMMARY = "Delete an order";
    public static final String DELETE_ORDER_DESCRIPTION = "This endpoint allows for deleting an existing order.";
    public static final String DELETE_ORDER_RESPONSE_204_DESCRIPTION = "Order successfully deleted";
    public static final String DELETE_ORDER_RESPONSE_404_DESCRIPTION = "Order not found";

    public static final String RESPONSE_401_DESCRIPTION = "Unauthorized: User not authenticated";
    public static final String RESPONSE_403_DESCRIPTION = "Forbidden: User does not have permission";
    public static final String RESPONSE_404_DESCRIPTION = "Order not found with the provided ID.";
}

package com.numarics.util;

public class ProductApiDocumentation {
    public static final String PRODUCTS_TAG = "Products";
    public static final String PRODUCT_ID_DESCRIPTION = "ID of the product";

    public static final String STATUS_200 = "200";
    public static final String STATUS_204 = "204";
    public static final String STATUS_400 = "400";
    public static final String STATUS_401 = "401";
    public static final String STATUS_403 = "403";
    public static final String STATUS_404 = "404";

    public static final String PAGE_DEFAULT = "0";
    public static final String SIZE_DEFAULT = "10";

    public static final String CREATE_PRODUCT_OPERATION_SUMMARY = "Create a new product";
    public static final String CREATE_PRODUCT_OPERATION_DESCRIPTION =
            "This endpoint allows for creating a new product.";
    public static final String CREATE_PRODUCT_RESPONSE_200_DESCRIPTION = "Product successfully created";
    public static final String CREATE_PRODUCT_RESPONSE_400_DESCRIPTION = "Validation error occurred during creating";

    public static final String GET_PRODUCT_BY_ID_OPERATION_SUMMARY = "Retrieve a product by ID";
    public static final String GET_PRODUCT_BY_ID_OPERATION_DESCRIPTION = "Retrieves product details based on the provided ID.";
    public static final String GET_PRODUCT_BY_ID_RESPONSE_200_DESCRIPTION = "Successfully retrieved the product.";

    public static final String SEARCH_PRODUCTS_OPERATION_SUMMARY = "Search for products";
    public static final String SEARCH_PRODUCTS_OPERATION_DESCRIPTION = "Searches for products based on the provided criteria.";
    public static final String SEARCH_PRODUCTS_RESPONSE_200_DESCRIPTION = "Successfully retrieved the list of products.";

    public static final String UPDATE_PRODUCT_OPERATION_SUMMARY = "Update an existing product";
    public static final String UPDATE_PRODUCT_OPERATION_DESCRIPTION = "Update an existing product by providing its ID and new data.";
    public static final String UPDATE_PRODUCT_RESPONSE_200_DESCRIPTION = "Product successfully updated";

    public static final String DELETE_PRODUCT_OPERATION_SUMMARY = "Delete an existing product";
    public static final String DELETE_PRODUCT_OPERATION_DESCRIPTION = "Delete a product by providing its ID.";
    public static final String DELETE_PRODUCT_RESPONSE_204_DESCRIPTION = "Product successfully deleted";

    public static final String RESPONSE_404_DESCRIPTION = "Product not found with the provided ID.";
    public static final String RESPONSE_401_DESCRIPTION = "Unauthorized: User not authenticated";
    public static final String RESPONSE_403_DESCRIPTION = "Forbidden: User does not have permission";
}

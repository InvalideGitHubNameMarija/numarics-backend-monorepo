# API Gateway

## Overview

The API Cloud Gateway acts as a centralized entry point for client requests. This application uses Spring Cloud Gateway for routing and filtering requests to microservices:

1. user-service 
2. product-service
3. order-service


## Setup

Setting up this application locally involves following these steps:

- Clone root repository on your local instance
- Navigate to the api-gateway folder
- From root of your repository execute `mvn compile` to download packages and build application
- Start application with `mvn spring-boot:run` command

The gateway will start up on port 9100 by default.

## Configuration

### Overview

Configuration options for the gateway, including routes, JWT authentication, server settings, and service endpoints, are specified in the application.yml file.

### JWT Authentication

This API gateway supports JWT (JSON Web Token) authentication using a secret key. The JwtAuthenticationGatewayFilterFactory intercepts incoming requests, extracts JWT tokens from the Authorization header, and verifies their validity using the provided secret.

### Routes

Routes are configured in the application.yml file under the spring.cloud.gateway.routes section. Each route specifies the destination URI and optional predicates and filters for request matching and processing.

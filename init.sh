#!/bin/bash


CURRENT_DIR=$(pwd)


# build the order-service with jib
echo "Building order-service..."
cd "$CURRENT_DIR/e-commerce-api/order-service"
mvn compile jib:dockerBuild

# build the product-service with jib
echo "Building product-service..."
cd "$CURRENT_DIR/e-commerce-api/product-service"
mvn compile jib:dockerBuild

# build the eureka-service with jib
echo "Building eureka-service..."
cd "$CURRENT_DIR/e-commerce-api/eureka-service"
mvn compile jib:dockerBuild

# build the image-service with jib
echo "Building image-service..."
cd "$CURRENT_DIR/e-commerce-api/image-service"
mvn compile jib:dockerBuild

# build the gateway-service with jib
echo "Building gateway-service..."
cd "$CURRENT_DIR/e-commerce-api/gateway-service"
mvn compile jib:dockerBuild


docker compose up

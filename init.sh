#!/bin/bash

# build the eureka-service with jib
#cd e-commerce-api/eureka-service
#mvn compile jib:dockerBuild

# build the order-service with jib
cd e-commerce-api/order-service
mvn compile jib:dockerBuild

# build the product-service with jib
#cd e-commerce-api/product-service
#mvn compile jib:dockerBuild

# build the admin-service with jib
#cd e-commerce-api/admin-service
#mvn compile jib:dockerBuild

# build the gateway-service with jib
#cd e-commerce-api/gateway-service
#mvn compile jib:dockerBuild

# build the image-service with jib
#cd e-commerce-api/image-service
#mvn compile jib:dockerBuild

docker compose up

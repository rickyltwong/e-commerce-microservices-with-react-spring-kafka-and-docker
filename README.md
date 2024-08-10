# e-commerce-microservices-with-react-spring-kafka-and-docker

By [Kajanan Sivarajah](https://github.com/kajanan16), [Ricky Wong](https://github.com/rickyltwong)

## Table of Contents

- [Description](#description)
- [Project Structure](#project-structure)
- [Technologies](#technologies)
- [Database Schema](#database-schema)
- [Features](#features)
- [How to Run](#how-to-run)

## Description

This project is a full stack e-commerce application built with React, Spring Boot, Apache Kafka, and Docker. 
The application is divided into multiple microservices, each responsible for a specific part of the application. 
The microservices communicate with each other using Open Feign client and Kafka. 
The application allows users to browse products, add products to the cart, and place orders.
The application uses Spring Cloud Stream to handle messaging with Kafka. 
It leverages StreamBridge to send messages to Kafka topics and listens for messages on Kafka topics using Spring Cloud Stream's messaging model.

## Project Structure
```
e-commerce-microservices-with-react-spring-kafka-and-docker
├── e-commerce-api
│   ├── order-service
│   ├── product-service
│   ├── admin-service
│   ├── image-service
│   ├── gateway-service
│   ├── eureka-service
├── frontend
│   ├── Dockerfile
│   ├── package.json
│   ├── src
│   └── public
├── init.sh
├── seed.sql
├── docker-compose.yml
└── README.md

 ```

## Technologies

- React
- Spring Boot
- Apache Kafka
- Docker
- PostgreSQL

## Database Schema

### Products

| Column Name  | Data Type | Constraints                         |
| ------------ | --------- | ----------------------------------- |
| product_id   | UUID      | PRIMARY KEY                         |
| name         | TEXT      | NOT NULL                            |
| description  | TEXT      |                                     |
| price        | DECIMAL   | NOT NULL                            |
| category     | TEXT      |                                     |
| created_at   | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP           |
| updated_at   | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP           |
| image_path   | TEXT      |                                     |

### Inventory

| Column Name  | Data Type | Constraints                         |
| ------------ | --------- | ----------------------------------- |
| inventory_id | UUID      | PRIMARY KEY                         |
| product_id   | UUID      | FOREIGN KEY REFERENCES Products     |
| quantity     | INTEGER   | NOT NULL                            |
| last_updated | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP           |

### Orders

| Column Name  | Data Type | Constraints                         |
| ------------ | --------- | ----------------------------------- |
| order_id     | UUID      | PRIMARY KEY                         |
| order_date   | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP           |
| total_amount | DECIMAL   | NOT NULL                            |

### Order_Items

| Column Name  | Data Type | Constraints                         |
| ------------ | --------- | ----------------------------------- |
| order_item_id| UUID      | PRIMARY KEY                         |
| order_id     | UUID      | FOREIGN KEY REFERENCES Orders       |
| product_id   | UUID      | FOREIGN KEY REFERENCES Products     |
| quantity     | INTEGER   | NOT NULL                            |



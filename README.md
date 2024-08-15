# e-commerce-microservices-with-react-spring-kafka-and-docker

By [Kajanan Sivarajah](https://github.com/kajanan16), [Ricky Wong](https://github.com/rickyltwong)

## Table of Contents

- [Description](#description)
- [Technologies Stack](#technologies-stack)
- [Project Structure](#project-structure)
- [Backend Features](#backend-features)
- [Frontend](#frontend)
- [Database Schema](#database-schema)
- [Setup](#setup)

## Description

This project is a full stack e-commerce application built with React, Spring, Apache Kafka, and Docker. 
The application is divided into multiple microservices, each responsible for a specific part of the application. 
The microservices communicate with each other using Open Feign client and Kafka. 
The application allows users to browse products, add products to the cart, and place orders.
The application uses Spring Cloud Stream to handle messaging with Kafka. 
It leverages StreamBridge to send messages to Kafka topics and listens for messages on Kafka topics using Spring Cloud Stream's messaging model.


## Technologies Stack

- **Backend:** Spring Framework
- **Deployment:** Docker
- **Frontend:** React
- **Database:** PostgreSQL
- **Message broker:** Kafka
- **Object Storage:** MinIO

## Project Structure

### Architecture

![architecture-diagram.png](demo/architecture-diagram.png)

### Directory structure

The project consists of the following components:
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

The application is containerized using Docker and deployed with Docker Compose, with each service built and dockerized separately using the Jib library. 
The project includes the following services:

### Business-related Services:
- **Product Service:** Manages product details.
- **Admin Service:** Manages admin operations.
- **Order Service:** Processes customer orders.
- **Image Service:** Serves image files. 

All resources are exposed as RESTful APIs.

### Infrastructure Services:
- **Gateway Service:** The only service exposed to the public, managing external requests and routing them to the appropriate microservices. All business-related services are internal, with only the gateway service exposed to the public. It handles routing and request management.
- **Eureka Service:** A service registry where all other services register and discover each other.

### Standalone Components:
- **Kafka:** For asynchronous communication.
- **Kafka UI:** For managing Kafka topics and events.
- **PostgreSQL:** The primary database (with schemas for products, orders, etc.).
- **PgAdmin:** A web-based database management tool for PostgreSQL.
- **MinIO:** An S3-compatible object storage server used to store and retrieve image files.

## Backend Features

### Product Service
- Manages both product data and inventory records within the e-commerce platform.
- Provides endpoints for:
    - **Fetching Products:** Allows retrieving all products or a specific product by ID.
    - **Managing Products in Admin Service:** Supports creating, updating, and deleting products through the admin service.
    - **Managing Inventory in Admin Service:** Enables fetching, creating, updating, and deleting inventory records through the admin service.
- Facilitates smooth management and inter-service communication in the admin service.
- Receives messages from the order service regarding order requests to manage stock levels.
- Receives messages from the admin service to handle image deletion requests.


#### Endpoints

- **Fetch Products:**  `GET /api/products`
- **Get Product:**    `GET /api/products/{id}`

#### Product Management in Admin Service
- **Create Product:** `POST /api/products`
- **Update Product:** `PUT /api/products/{id}`
- **Delete Product:** `DELETE /api/products/{id}`
- **Upload Product Image:** `POST /api/products/{id}/image`

#### Inventory Management in Admin Service
- **Fetch All Inventories:** `GET /api/inventory`
- **Get Inventory:** `GET /api/inventory/{id}`
- **Create Inventory:** `POST /api/inventory`
- **Update Inventory:** `PUT /api/inventory/{id}`
- **Delete Inventory:** `DELETE /api/inventory/{id}`


### Image Service

- Uploads images from the filesystem to a MinIO bucket.
- Serves image data required during product fetch operations.

#### Endpoints
- **Upload Image:** `POST /api/images`
- **Get Image:** `GET /api/images/{imgName}`
- **Delete Image:** `DELETE /api/images/{imgName}`


### Admin Service

The Admin Service is responsible for managing both product and inventory data within the e-commerce platform. It allows administrators to perform a variety of tasks, including:

#### Endpoints 

#### Product Management:
- **Create Product:** `POST /api/admin/products/{id}`
- **Upload Product Image:** `POST /api/admin/products/{id}/image`
- **Delete Product:** `DELETE /api/admin/products/{id}`
- **Fetch Products:** `GET /api/admin/products`
- **Update Product:** `PUT /api/admin/products/{id}`

#### Inventory Management:
- **Create Inventory:** `POST /api/admin/inventory`
- **Fetch Inventory:** `GET /api/admin/inventory`

#### Additional Operations:
- **Fetch Orders:** `GET /api/admin/orders`

This service ensures seamless management of products, inventory, and orders, providing administrators with the necessary tools to oversee the e-commerce platform efficiently.

### Order Service
The Order Service is an integral part of the e-commerce platform, responsible for handling all order-related operations. It allows for the management of customer orders through the following key functionalities:

#### Endpoints
- **Fetch Orders:**  `GET /api/orders`
- **Get Order:**     `GET /api/orders/{id}`
- **Create Order:**  `POST /api/orders`


### Gateway service

- **Entry Point:** Serves as the main entry point for all microservices.
- **Load Balancing:** Distributes traffic across service instances to optimize performance.
- **Request Logging:** Logs incoming requests for monitoring and debugging.
- **Path Rewriting:** Modifies request paths before routing them to the appropriate microservices.


### Eureka Service

- **Service Registration:** Microservices register their location and status.
- **Service Discovery:** Enables dynamic discovery of registered services.


### Service Interaction
- **Synchronous Communication:** Implemented using OpenFeign.
- **Asynchronous Communication:** Handled via Kafka.

#### Open Feign

In this project, OpenFeign has been used for synchronous inter-service communication. It facilitates seamless interaction between different services.

In the admin service, OpenFeign is utilized to perform the following operations:

- **Product Management:** OpenFeign calls the product service endpoints to manage product-related data, enabling operations such as:
    - `POST /api/products` - To create a new product.
    - `PUT /api/products/{id}` - To update an existing product by its ID.
    - `DELETE /api/products/{id}` - To delete a product by its ID.
    - `POST /api/products/{id}/image` - To upload the product image.

- **Inventory Management:** OpenFeign is also used to manage product inventories by interacting with the inventory service endpoints, supporting operations such as:
    - `GET /api/inventory` - To retrieve a list of all inventory records.
    - `GET /api/inventory/{id}` - To fetch details of a specific inventory by its ID.
    - `POST /api/inventory` - To create a new inventory record.
    - `PUT /api/inventory/{id}` - To update an existing inventory by its ID.
    - `DELETE /api/inventory/{id}` - To delete an inventory record by its ID.

- **Order Management:** OpenFeign is used in the admin service to manage orders:
    - `GET /api/orders` - To retrieve a list of all order records.

In the product service, OpenFeign is utilized to perform the following operations:
- `POST /api/images` - To upload a product image.
- `DELETE /api/images/{fileName}` - To delete an image.




OpenFeign simplifies the process of making HTTP requests to other services within the system, ensuring reliable and efficient inter service communication.
 
#### Kafka

Kafka is used for asynchronous communication between services. One of the Kafka flows implemented is the **OrderPlacedEvent** topic.
- When an order is placed, the **OrderService** sends a message to the **order-placed-topic** topic.
- **ProductService** subscribes to this topic, receives the message, and updates the stock level of the product accordingly.

Other kafka flow would be deleteImageEvent:
- when a admin delete the product, the admin service publish the message to **delete-image-topic** topic.
- **ProductService** subscribes to the topic, and receives the message then process it and send it call the image service to perform the image deletion operation.

### Logging
- Custom filters log the original request, rewritten request, and response.

## Frontend

The frontend is built with React in TypeScript and interacts with the backend through the following endpoints:

- **Fetch Products:** `GET /api/products`
- **Fetch Product Images:** `GET /api/images/{productname}`
- **Create Order:** `POST /api/orders`

### Pages:
1. **Products Page:** Displays the available products.
2. **Cart:** Manages items added to the cart, stored in `localStorage`.
3. **Success:** Displays transaction details after a successful order.

Other libraries used in frontend includes Mantine and Tanstack Query.

## Database Schema

### Products

| Column Name  | Data Type | Constraints                         |
| ------------ | --------- | ----------------------------------- |
| product_id   | UUID      | PRIMARY KEY                         |
| name         | TEXT      | NOT NULL                            |
| description  | TEXT      |                                     |
| price        | DECIMAL   | NOT NULL                            |
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

## Setup

1. Clone the Repository:
```
git clone https://github.com/yourusername/e-commerce-microservices-with-react-spring-kafka-and-docker.git
cd e-commerce-microservices-with-react-spring-kafka-and-docker
```

2. Run Initial Setup:
```
./init.sh
```
This script will build all services with jib library and run the docker compose.
The docker will run the `seed.sql` script to seed the product data into the application and the `image-service` will upload the product image files to the `minIO` bucket.

3. Run the frontend:
```
cd frontend
npm run dev
```

4. Access the application through: http://localhost:5173
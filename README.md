# e-commerce-microservices-with-react-spring-kafka-and-docker

By [Kajanan Sivarajah](https://github.com/kajanan16), [Ricky Wong](https://github.com/rickyltwong)

## Description

This project is a full stack e-commerce application built with React, Spring Boot, Apache Kafka, and Docker. The application is divided into multiple microservices, each responsible for a specific part of the application. The microservices communicate with each other using Kafka. The application allows users to browse products, add products to the cart, and place orders. The application also includes a Kafka producer that sends messages to Kafka topics, and a Kafka consumer that listens for messages on Kafka topics.

## Project Structure

    project dir
    ├── /backend
    │   ├── /OrderService (sub-module)
    │   ├── /ProductService (sub-module)
    │   ├── /AdminService (sub-module)
    │   ├── /ImageService (sub-module)
    │   ├── /GatewayService (sub-module)
    │   ├── /EurekaService (sub-module)
    │   ├── /KafkaConfig (sub-module)
    │   └── pom.xml
    ├── /frontend
    │   ├── Dockerfile
    │   ├── package.json
    │   ├── src
    │   └── public
    ├── init.sh
    ├── seed.sql
    ├── docker-compose.yml
    ├── README.md
    └── Final_Documentation.docx

## Technologies

- React
- Spring Boot
- Apache Kafka
- Docker
- PostgreSQL

## Features

- Browse products
- Add products to the cart
- Place orders
- Kafka producer
- Kafka consumer
- Microservices architecture
- Docker containers
- RESTful APIs

## How to Run

1. Clone the repository:

   ```bash
   git clone
   ```

2. Navigate to the project directory:

   ```bash
   cd e-commerce-microservices-with-react-spring-kafka-and-docker
   ```

3. Run the following command to start the application:

   ```bash
   chmod +x init.sh
   ./init.sh
   ```

4. Access the application at [http://localhost:3000](http://localhost:3000)

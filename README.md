
# eWallet Application

## Overview

The eWallet application is a microservices-based system built using Java Spring Boot, MySQL, Spring Data JPA, Hibernate, and Kafka. It consists of the following services:

- User Service
- Wallet Service
- Transaction Service
- Notification Service

All services are registered under a Eureka server for service discovery.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Services](#services)
  - [User Service](#user-service)
  - [Wallet Service](#wallet-service)
  - [Transaction Service](#transaction-service)
  - [Notification Service](#notification-service)
- [Kafka Integration](#kafka-integration)
- [Exception Handling](#exception-handling)
- [Contributing](#contributing)
- [License](#license)

## Architecture

The eWallet application follows a microservices architecture. The interaction flow is as follows:

1. **User Registration**: When a user registers, a `user created` event is pushed to the Kafka queue. The Wallet Service reads this event and creates a wallet for the user.
2. **User Deletion**: When a user is deleted, a `user deleted` event is pushed to the Kafka queue. The Wallet Service reads this event and deletes the user’s wallet.
3. **Transactions**: Users can perform three types of transactions: `TRANSFER`, `WITHDRAW`, and `DEPOSIT`.
    - A transfer request is initiated in the User Service and sent to the Transaction Service.
    - The Transaction Service processes the request and interacts with the Wallet Service to perform the transaction.
    - The Wallet Service returns a success or failure response.
    - Based on the response, the Transaction Service pushes a success or failure event to the Kafka queue.
    - The Notification Service reads these events and sends email notifications to the sender and receiver for successful transactions and to the sender for failed transactions.

## Services

### User Service
Manages user-related operations such as registration and deletion.

### Wallet Service
Handles wallet creation and deletion, and processes transactions.

### Transaction Service
Coordinates transaction operations and communicates with the Wallet Service.

### Notification Service
Listens to transaction events and sends email notifications using Spring's Simple Mail Service.

## Kafka Integration

Kafka is used for asynchronous communication between services. Events such as user creation, user deletion, and transaction status updates are communicated via Kafka queues.

## Exception Handling

Custom exceptions are used throughout the application to handle errors. These exceptions are propagated to the user with meaningful messages.


### Prerequisites
- Java 11 or higher
- Spring boot
- Hibernate
- Maven
- MySQL
- Kafka
- Zookeeper


## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License.

Feel free to customize this template further to suit your project's needs.

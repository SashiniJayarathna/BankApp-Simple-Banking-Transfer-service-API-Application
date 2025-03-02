BankApp - A Simple Banking Transfer service API Application

Overview

BankApp is a RESTful API built using Spring Boot for managing bank accounts. It allows users to create accounts, deposit, withdraw, transfer money, and retrieve account details. The application uses MySQL as the database and follows a clean architecture with DTOs, services, repositories, and controllers.

Features

1.	Create a bank account
2.	Deposit money
3.	Withdraw money
4.	Transfer money between accounts
5.	Fetch account details by ID or account number
6.	Handle exceptions with proper error responses
7.	Delete a bank account
   
Technologies Used

1.	Java 17
2.	Spring Boot 3
3.	Spring Data JPA
4.	Hibernate
5.	MySQL
6.	Lombok
7.	REST API
8.	Postman (for testing)

Prerequisites

Before running this application, ensure you have the following installed:

1.	Java 17 or later
2.	MySQL Database
3.	Maven
4.	Git

Installation and Setup

1.	Clone the repository:
2.	git clone [https://github.com/SashiniJayarathna/bankApp.git]
4.	Configure the database: Update src/main/resources/application.properties with your MySQL credentials:
5.	spring.datasource.url=jdbc:mysql://localhost:3306/bankdb
6.	spring.datasource.username=root
7.	spring.datasource.password={yourDBpassword}
8.	spring.jpa.hibernate.ddl-auto=update
9.	Build and run the application:
10.	mvn clean install
11.	mvn spring-boot:run

API Endpoints

Account Management

1.	Create Account: POST /api/accounts
2.	Get Account by ID: GET /api/accounts/{id}
3.	Get Account by Account Number: GET /api/accounts /{id }
4.	Get All Accounts: GET /api/accounts
5.	Delete Account: DELETE /api/accounts/{id}

Transactions

1.	Deposit Money: POST /api/accounts/{id}/deposit
2.	Withdraw Money: POST /api/accounts/{id}/withdraw
3.	Transfer Money: POST /api/accounts/transfer

Exception Handling

The application provides structured error responses for scenarios like:

1.	Account not found
2.	Invalid transaction amount
3.	Insufficient balance

Testing the API

Use Postman or any REST client to test the API. 

Contact

For any questions, feel free to reach out:

Email: sashini200042@gmail.com 


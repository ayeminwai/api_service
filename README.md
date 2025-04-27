# Java SpringBoot Application - User, Account, and Transaction Management
This repository contains a Java SpringBoot application designed to manage users, accounts, and transactions. The application includes features for creating and updating users, creating accounts, performing transactions, and integrating with a third-party API for KYC (Know Your Customer) verification.
## Features
### 1. User Management:
    - Create a new user and retrieve the userId of the newly created user.
    - Update existing user details.
### 2. Account Management:
    - Create a new account for a user and retrieve the accountId of the newly created account.
    - Each account starts with a balance of 0.
### 3. Transaction Management:
    - Make a transaction (deposit, withdrawal).
    - Transactions are linked to accounts.
### 4. Pagination for Transactions:
    - View transactions with pagination support (10 records per page).
### 5. Third-Party Integration:
    - Perform KYC verification using a third-party API.

## Project Setup
### Prerequisites
    1. Java 17 and higher
    2. Maven for building the project.
    3. MSSQL Database: Set up an MSSQL database with the name TESTDB on your local machine.
    4. Postman for testing the APIs.

## API Endpoints
### User Controller
#### 1. Create User
#### 2. Update User
#### 2. Get User

### Account Controller
#### 1. Create Account
#### 2. Get Account

### Transaction Controller
#### 1. Create Transaction
#### 2. Get Transactions (with Pagination)


## Postman Collection
A Postman collection is provided for easy testing of the APIs. You can import the collection to Postman by following these steps:
1. Download from Project
2. Open Postman and click Import in the top left corner.
3. Select the downloaded file and click Import.

The collection contains all the required API calls for testing the functionality of the application, including:
1. Create User
2. Update User
3. Get User
4. KYC Verification
5. Create Account
6. Get Account
7. Make Transactions
8. Pagination for Transactions


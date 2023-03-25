# Banking_Vikram

## API's:

### 1. POST (Creates a new account): 
>localhost:8080/banking/account
####  Payload:
  {
    "custName":"Praveen",
    "dob":"1990-09-19",
    "email":"praveen0821@gmail.com",
    "accounts":[
        {
            "balanceAmt":"1000.00"
        }
    ]
}

### 2. PUT (Updates an existing account): 
>localhost:8080/banking/account
#### Payload:
  {
    "custName":"Praveen",
    "dob":"1990-09-19",
    "email":"praveen0821@gmail.com",
    "accounts":[
        {
            "balanceAmt":"1000.00"
        }
    ]
}

### 3. DELETE (Deletes an account): 
>localhost:8080/banking/account?accountNum={27119728627}

### 4. GET (Get all customers account): 
>localhost:8080/banking/all-cust-accts

### 5. GET (Get all accounts of one particular customer): 
>localhost:8080/banking/cust-accounts?customerName={Praveen Sowmya}

### 6. GET (Get account of a particular account): 
>localhost:8080/banking/account?accountNum={27119728627}

### 7. PUT (Withdraw amount from the account): 
>localhost:8080/banking/withdraw?withdrawAmount=10&accountNum=91603512227

### 8. PUT (Deposit amount to the account): 
>localhost:8080/banking/deposit?depositAmount=100&accountNum=91603512227

## Swagger Ui url:
>http://localhost:8080/swagger-ui/index.html

## DataBase login details:
#### Url: http://localhost:8080/h2-console
#### Jdbc url: jdbc:h2:mem:testdb
#### userName: user
#### Password: pwd
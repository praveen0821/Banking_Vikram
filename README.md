# Banking_Vikram

#API

#1. POST (Creates a new account): localhost:8080/get/account
  Payload:
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

#2. PUT (Updates an existing account): localhost:8080/get/account
  Payload:
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

#3. DELETE (Deletes an account): localhost:8080/get/account?accountNum={27119728627}

#4. GET (Get all customers account): localhost:8080/get/all-cust-accts

#5. GET (Get all accounts of one particular customer): localhost:8080/get/cust-accounts?customerName={Praveen Sowmya}

#6. GET (Get account of a particular account): localhost:8080/get/account?accountNum={27119728627}


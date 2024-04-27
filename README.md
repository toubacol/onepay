# OnePay Project
OnePay is a Spring Boot application that provides a RESTful API to perform payments.

## Prerequisites
In order to build and run this project, you will need:
* Java 17 +
* Maven 3.x.x

## Database
The OnePay application is using the h2 memory database.
So you will need to create a `onepaytestdb.mv` file in your home directory.

## Installation
* Clone this git repository
* From a command line console, change directory (`cd`) to the OnePay home directory
* Run following maven command to build the project: ```mvn clean install```
* Change directory (`cd`) to the generated `target` directory
* Run the following to start the application: ```java -jar onepay-0.0.1-SNAPSHOT.jar```

## Testing
The application can be tested by accessing the swagger-ui page at: http://localhost:8080/swagger-ui/index.html#
where you will find all API endpoints.

## Screenshot
[Screenshot of the generated swagger-ui page.](./src/main/resources/static/swagger-ui-screenshot.png)

## Test data

### Creation of the first transaction

```
curl -X 'POST' \
'http://localhost:8080/api/transactions' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"type": "CREDIT_CARD",
"orders": [
{
"productName": "Ski gloves",
"quantity": 4,
"price": 10
},
{
"productName": "Cap",
"quantity": 1,
"price": 14.80
}
]
}'
```

### Update transaction status to  AUTHORIZED
```
curl -X 'PATCH' \
'http://localhost:8080/api/transactions/1' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"status": "AUTHORIZED"
}'
```

### Update transaction status to  CAPTURED
```
curl -X 'PATCH' \
'http://localhost:8080/api/transactions/1' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"status": "CAPTURED"
}'
```

### Creation of th second transaction
```
curl -X 'POST' \
'http://localhost:8080/api/transactions' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"type": "PAYPAL",
"orders": [
{
"productName": "Velo",
"quantity": 1,
"price": 208
}
]
}'
```

### Get all transactions
```
curl -X 'GET' \
'http://localhost:8080/api/transactions' \
-H 'accept: */*'
```



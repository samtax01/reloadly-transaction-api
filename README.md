# Reloadly Transaction API

This API is responsible for Storing Customer's Transaction (Requires Authorization token from Customer Account API).

Customer would be able to
- Create Transaction (Authorization Token is required with a compatible email)
- View Transactions
- Get an email when transaction is created (Using Notification API).

### This project is created using
- Java (Spring Boot)
- Accessing Data with JPA
- JUnit

### Running the application locally
- mvn clean package (To reinstall plugins)
- mvn spring-boot:run (To start the project)

### Transaction Request:
```json
{
  "orderId": "string",
  "status": "COMPLETED",
  "customerId": "string",
  "customerEmail": "string",
  "amount": 0,
  "productName": "string",
  "productDescription": "string",
  "metaData": "string"
}
```

Transaction response
```json
{
  "status": true,
  "message": "Success",
  "data": {
    "id": 15,
    "orderId": "111",
    "status": "COMPLETED",
    "customerId": "1202",
    "customerEmail": "hello@samsonoyetola.com",
    "amount": 2000,
    "productName": "Vodafone Airtime",
    "productDescription": "Airtime and bonuses",
    "metaData": null,
    "createdAt": "2021-09-07T13:37:17.064573",
    "updatedAt": "2021-09-07T13:37:17.064779"
  }
}
```

## Other Links:

### Customer Account API
> **Github**: https://github.com/samtax01/reloadly-customer-account

> **Heroku**: https://reloadly-quiz-customer-api.herokuapp.com/swagger-ui.html


#### Transaction API
> **Github**: https://github.com/samtax01/reloadly-transaction-api

> **Heroku**: https://reloadly-quiz-transaction-api.herokuapp.com/swagger-ui.html

#### Notification API
> **Github**: https://github.com/samtax01/reloadly-notification-api

> **Heroku**: https://reloadly-quiz-notification-api.herokuapp.com/swagger-ui.html

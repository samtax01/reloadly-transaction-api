# Reloadly Transaction API

This API is responsible for Customer Transaction (Requires Authorization token from Customer Account API).

Customer would be able to
- Create Transaction (Authorization Token required)
- View Transactions
- Get an email when transaction is created (Using Notification API).

### This project is created using
- Java (Spring Boot)
- Accessing Data with JPA
- JUnit


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

### Other Links:

#### Customer Account API
> **Github**: https://github.com/samtax01/reloadly-customer-account


#### Transaction API
> **Github**: https://github.com/samtax01/reloadly-transaction-api


#### Notification API
> **Github**: https://github.com/samtax01/reloadly-notification-api



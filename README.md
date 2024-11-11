# Final Prices API

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
  - [Build the Project](#build-the-project)
  - [Run the Application](#run-the-application)
- [Prerequisites](#prerequisites) 
-  [Endpoints](#endpoints)
   -  [Create or Update Price](#1-create-or-update-price) 
   -  [Get Final Price](#2-get-final-price) 
   -  [List Prices for a Product](#3-list-prices-for-a-product)
   -  [Delete a Price](#4-delete-a-price)
-  [Error Handling](#error-handling) 
-  [Logging](#logging) 
- [Running Tests](#running-tests)
- [Acknowledgments](#acknowledgments)
- [Contact](#contact)

## Overview
the purpose of designed this API is to retrieves the final price(pvp) and rate applied for a specific product and brand based on the provide application date to determine  the prices between  stardate and endDate.
for the case that multiple available prices exist for the same product, brand and date range the service may consider the priority field,for such scenario the criteria will be Prices with higher priority values are
choose over those with lower priority values.

In addition, his main purpose, this Api provide other end-point such as create, delete existing price and fetching listing available prices for a specific product and brand in order to provide much better usability of this api

## Features
- Creates a new price record or updates an existing one.
- Retrieves the final price for a product given an application date.
- Lists all available prices for a specific product and brand.
- Deletes a price record by ID.

## Technologies Used
- Spring Boot
- Maven
- Java
- SLF4J (for logging)

## Prerequisites
  - Java 17 or higher
  - Maven 3.8.4 or higher

## Getting Started

### Clone the repository:

```bash 
   git clone https://github.com/yourusername/prices-api.git cd prices-api
```
### Build the Project

```bash
mvn spring-boot:run
```

### Running the Service

```bash
mvn spring-boot:run
```

## Endpoints

### 1. Create or Update Price
**Endpoint**: `/api/v1/prices`
**Method**: `POST`
**Description**: Creates a new price record or updates an existing one.

#### Request
```json
{
  "productId": 12345,
  "brandId": 1,
  "price": 100.0,
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-12-31T23:59:59",
  "priority": 1,
  "priceList": 1,
  "curr": "USD"
}
```
#### Response
```json
{
 "metadata": {
  "specVersion": "1.0",
  "operation": "createOrUpdatePrice",
  "id": "6f9b92a4-75e8-461f-a38c-8e12db86c9ef",
  "time": "2024-11-08T20:56:57",
  "dataContentType": "application/json"
 },
 "status": "SUCCESS",
  "data": {
    "id": 25,
    "productId": 12345,
    "brandId": 1,
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-12-31T23:59:59",
    "priceList": 1,
    "priority": 1,
    "price": 100.0,
    "curr": "USD"
  }
}
```

### 2. Get Final Price
**Endpoint**: `/api/v1/prices/final`
**Method**: `POST`
**Description**: Retrieves the final price for a product given an application date.
#### Request
```json
{
 "applicationDate": "2024-01-15T10:00:00",
 "productId": 12345,
 "brandId": 1  
}
```

#### Success Response
```json
{
 "metadata" :{
  "specVersion":"1.0",
  "operation":"findFinalPrice",
  "id":"e99103a3-b820-4de3-a034-934f593e963a",
  "time":"2024-10-09T16:31:36",
  "dataContentType":"application"},
 "data": {
  "productId":35455,
  "supplyChainId":1,
  "rateToApply":4,
  "applicationDateStart":"2020-06-15T16:00:00",
  "applicationDateEnd":"2020-12-31T23:59:59",
  "finalPrice":38.95,
  "cur":"EUR"
 },
  "error":null,
  "success":true
}
```
#### Failure Response
```json
{
 "metadata" :{
  "specVersion":"1.0",
   "operation":"findFinalPrice",
  "id":"e99103a3-b820-4de3-a034-934f593e963a",
  "time":"2024-10-09T16:31:36",
  "dataContentType":"application"
 },
 "data": null,
  "error":{
   "code":"BUSINESS ERROR",
   "message":"Product ID not found"
   },
   "success":false
}
```

### 3. list Prices for a Product
**Endpoint**: `/api/v1/prices/brand/{brandId}/product/{productId}`
**Method**: `GET`
**Description**: Lists all available prices for a specific product and brand.

#### Success Response
```json
{
"metadata":{
 "specVersion":"1.0",
 "operation":"findListPriceAvailableOfProduct",
 "id":"13c7f5c3-d0c6-400b-8894-240d807b0b8c",
 "time":"2024-10-09T16:40:32",
 "dataContentType":"application"
},
 "data":[
    {
     "id":1,
     "brand":1,
     "startDate":"2020-06-14T00:00:00",
     "endDate":"2020-12-31T23:59:59",
     "priceList":1,
     "productId":35455,
     "priority":0,
     "price":35.5,
     "curr":"EUR"
    }
   ],
   "error":null,
   "success":true
}
```
#### 4. Delete a Price
**Endpoint**: `/api/v1/prices/{id} `
**Method**: `DELETE`
**Description**: Deletes a price record by ID

#### Success Response
```json
{
"metadata":{
 "specVersion":"1.0",
 "operation":"deletePrice",
 "id":"13c7f5c3-d0c6-400b-8894-240d807b0b8c",
 "time":"2024-10-09T16:40:32",
 "dataContentType":"application"
},
 "data":[
    {
     "id":1,
     "brand":1,
     "startDate":"2020-06-14T00:00:00",
     "endDate":"2020-12-31T23:59:59",
     "priceList":1,
     "productId":35455,
     "priority":0,
     "price":35.5,
     "curr":"EUR"
    }
   ],
   "error":null,
   "success":true
}
```

## Error Handling
All error responses will follow a standardized structure:

```json
{
 "metadata" :{
  "specVersion":"1.0",
  "operation":"operationName",
  "id":"e99103a3-b820-4de3-a034-934f593e963a",
  "time":"2024-10-09T16:31:36",
  "dataContentType":"application/json"},
 "data": null,
  "error":{
   "code":"ERROR_CODE",
   "message":"Error message description"
   },
   "success":false
}
```

## Logging
The API uses SLF4J for logging. Each endpoint logs its invocation and other significant events.

## Running Tests
To run the tests for this application, use the following command:

```bash
mvn test
```

## Acknowledgments
Special thanks to Jeitson Cabrera Vivas for giving me the opportunity to present this project and learn from it.

## Contact
If you have any questions or need further information, please contact us at sandreajesus05@gmail.com.
# forex-coding-task
Forex transactions validation.

## Main activity part
The repository contains 2 projects: `validations` and `client`.  
- `validations` - webservice that exposes REST API for transactions validation  
- `client` - simple client that sends example json data with transactions to validate and logs the output to the console.

### Validation rules
For validation I decided to have `Validator` interface with two methods. One is a validation method for a transaction object and the second one is a "validation rule" getter.  
A "validation rule" is an enum that contains all types of validation errors.  
Each validation (like value date or counterparty validation) is a seperate class which is annotated with @Component. This gives me a possibility to inject dependencies needed to validate a transaction.  
Thanks to this structure I was able to create a factory class `TransactionValidatorFactory` that returns the correct validator class for a given transaction type.  
Additionally there is another class `TransactionRulesFactory` that lists a validation rules for each transaction type. I wanted to make it readable and clear and this is why it's in the seperate class.  
Now, having a list of validators of a given transaction type I use `CompositeValidator` which is a composite for validators that exposes a single `validate(transaction)` method and composes a list of results.  
The list of results is filtered from successful results and the response it created.
Example result:
```
[
    {
        "message": "Value date must be after greater than 2 days from now. Current date: 2020-10-10",
        "transactionIndex": 2
    },
    {
        "message": "Value date cannot be before trade date.",
        "transactionIndex": 5
    },
    {
        "message": "Customer must be one of the supported counterparties: [YODA2, YODA1]",
        "transactionIndex": 6
    }
]
```
- I decided to make some DTO fields as a String instead of an enums to be able to verify the value manually and to add a validation error message to other messages. With enums we can't simply deserialize an invalid enum value, it will throw an exception.
- For the value dates validation I validate if the Spot value date is before two days from now and Forward value date is greater than 2 days from now. I found an info about this here: https://www.investopedia.com/terms/v/valuedate.asp "Due to differences in time zones and bank processing delays, the value date for spot trades in foreign currencies is usually set two days after a transaction is agreed on".
- validation endpoint should always return 200. This is because if we return 400 this means there is something wrong with our request body and not really the "real" validation of the trades. When the list is empty we know the transactions are valid (this behavior is also described in the swagger documentation).


### Requirements
- Project `validations` contains a rest endpoint to `/transactions/validate` that consumes a json request body with list of transactions.  
Each transaction is validated based on the specific type validator and the common validators.  
Validation response contains a list of all validation errors on all transactions (and not only the first one). 
Validation response item contains a message and the transaction index - which indicates the position of the transaction in the request body. This works as a link so the client knows which transactions are invalid.  
- The service is flexible to extend the validation logic. To add a validation rule we need to extend the `ValidationRule` 
with the new rule, create a new Validator class that implements the Validator and assign the new rule to the transaction type (or add it for all types) in `TransactionRulesFactory` class. And that's all.  
To add a new transaction type, we only need to extend the `TransactionType` enum. We can also extend `TransactionRulesFactory` with the new transaction type when we have validation rules for that new type. 
- graceful shutdown - to enable graceful shutdown I used built-in application property `server.shutdown=graceful`. Now, the server will wait for active requests to finish their work. I also configured the grace period using the `spring.lifecycle.timeout-per-shutdown-phase=1m` to 1 minute. To see the shutdown in action in Intellij IDE, we need to stop the application with "Exit" button which sends the SIGTERM signal.
- To setup the current date to `09.10.2020` I created a `DateTimeService` component with getCurrentDate() method and I used that dependency instead of very common `LocalDate.now()`.
- To validate supported counterparties and legal entities I decided to create a separate validator rules.


### Simple client
Project `client` is a simple application that sends json request data (stored in resource folder file) and prints the output to the console. After the request is done the application shuts down.  
I decided to use Spring Boot to have a familiar to me RestTemplate. To have a rest template I need spring-boot-starter-web dependency which automatically starts tomcat server, so I disabled it with an application property: `spring.main.web-application-type=none`.

## Extra activity part

### Metrics
To expose metrics I used `micrometer-registry-prometheus` dependency along with @Timed annotation from the micrometer library on the request method. 
I also selected which metrics I want to expose with the application property: `management.endpoints.web.exposure.include=prometheus,health,info,metric`.
Metrics from prometheus are available under the url: http://localhost:8080/actuator/prometheus  
Sample response (interesting part):
```
 ...
 validate_trades_seconds{exception="None",method="POST",outcome="SUCCESS",status="200",uri="/transactions/validate",quantile="0.95",} 0.039714816
 validate_trades_seconds_count{exception="None",method="POST",outcome="SUCCESS",status="200",uri="/transactions/validate",} 5.0
 validate_trades_seconds_sum{exception="None",method="POST",outcome="SUCCESS",status="200",uri="/transactions/validate",} 0.0523998
 ...
 validate_trades_seconds_max{exception="None",method="POST",outcome="SUCCESS",status="200",uri="/transactions/validate",} 0.0395092
 ...
```

### High availability and scalability
The service is very scalable in two ways. First, it's easy to extend the validation logic. Second, it can also be part of a microservice architecture. There is no @Schedule or other mechanisms that can cause issues. We can simply hide it behind a loadbalancer and run multiple instances in the same time. I also added metrics with an actuator helthcheck which can be very helpful to monitor the state of the service. For Spring Cloud environments it would be very easy to make this service accessible through the Discovery, just by changing the parent in pom.xml, enabling service discovery with annotation and by providing some additional application properties. I also implemented graceful shutdown which gives us the ability to stop the application at any time.

### REST API documentation exposed by the service
To create an API documentation I used `springdoc-openapi-ui` dependency. I also configured the html and json enpoint urls in the application properties.  
When the service is running, the documentation is available here: http://localhost:8080/swagger-ui/index.html  
and the json api-docs is here: http://localhost:8080/api-docs/

To make the documentation better:
- I added `OpenApiConfig` with the app name and the description
- I added @Operation and @ApiResponse on a controller method to write down more details about the endpoint
- I extended the models with @Schema annotation where I can add property definition (for example: description, allowable values, example values)
  
Examples from the documentation:
![image](https://user-images.githubusercontent.com/15677359/149514454-b22ef3c9-5438-444d-aa07-f6979df7b0d4.png)
![image](https://user-images.githubusercontent.com/15677359/149514549-15991820-1442-4e56-83e8-b4814e960196.png)
![image](https://user-images.githubusercontent.com/15677359/149514604-c66b7d81-ebde-4413-9f28-f1f05f07da39.png)

  

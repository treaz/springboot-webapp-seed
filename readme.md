# Spring boot webapp seed project

This project can be used as a starting point for a new Spring Boot webapp project.

## Features

* persistence with Spring Data JPA and postgresql
* authentication with JWT
* test strategy for unit and end-to-end tests

### Existing endpoints

I've attached a [Postman](https://www.postman.com/) collection in the [root](postman_requests.postman_collection.json)
of the project.

This collection can be imported into Postman to see all the requests and their payloads.

* POST /register - (public) allows userProfile registration (without email verification).
* POST /authenticate - (public) authentication (using JWT), returns the auth token string
* GET /account - allows userProfile to see account balance and current transactions
* POST /topup - allows userProfile to transfer money to their own account
* POST /transfer - allow userProfile to transfer money to another existing userProfile
  * transferring money to self is not allowed,
  * transfer of sum bigger that current account balance is not allowed
  * transferring money to non-existing account is not allowed

### Authentication

The application uses [JWT](https://jwt.io/) for authentication. All endpoints that are not marked as public are
protected.

In order to access the protected endpoints:

* call /authenticate to obtain the JWT
* Pass the JWT in the Authorization header for all protected requests.

### For the future

* support for amounts with decimals
* expose JWT timeouts via configuration
* topup transactions currently appear as negative
* transaction date is of type Date, which will be super painful later, when timezone and other databases will be
  introduced.
* currently, the DB schema is created/updated on app init. This is not prod ready. Migrations are the way to go.
* the transfer operation is not atomic, if something happens during the operation, some people will be very unhappy...
* currently, transactions don't show where the money is coming from. Therefore, transfers are nameless.

### Test coverage

My goal was to define a test strategy, more than to cover all the code with tests. The existing tests can be seen as
guidelines for new tests.

* fully covered with unit tests: UserProfileRepository and UserService.
* partially covered with end-to-end test: all endpoints are covered at least on the "happy" path with e-2-e test.

## Development

Prerequisites:
* java SDK 11
* maven 3+
* postgresql 14 (configuration of host, port and credentials is possible in application.properties). For
  example: ``docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=test123 postgres:14``

### Debugging

To debug, open SpringBootJwtApplication.java in Intellij IDEA CE, right click and debug.

### Running the app locally

To run the app locally, in the project directory root execute: ``mvn spring-boot:run``

### Running the tests locally

* unit tests: ``mvn clean test``
* unit + end-to-end tests: ``mvn clean verify``

## Deployment

This app can be deployed to heroku easily. All you have to do is to
follow [these instructions](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku#preparing-a-spring-boot-app-for-heroku)
Note: the postgresql connection details are overridden automatically by heroku, so the app will run immediately.

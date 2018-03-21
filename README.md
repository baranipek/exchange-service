## User Story
  Develop a service, that constantly checks the currency exchange rate for instance (1 EUR = x USD).
  The check period is configurable
  The results are stored in a database.
  The service has an HTTP-Resource with the following endpoints can be followed http://localhost:8080/swagger-ui.html:
     o ​Get latest rate http://localhost:8080/api/v1/exchange/EUR/USD
     o ​Get historical rates from startDate to endDate (http://localhost:8080/api/v1/exchange/EUR/USD?startDate=19-03-2018&endDate=21-03-2018)

 ## How to test and run
   This is a simple spring boot project you can run test with the command mvn  clean test and run spring-boot:run





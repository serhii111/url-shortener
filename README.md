# Url Shortener Application
Provides REST API for creating a short version of given URL and getting the full URL out of the shortened version back.

## How to run the application:
### Pre-requisites
1. Java 21 is installed
2. Gradle 8.11 is installed
3. Docker is installed

### Build the application
Application could be built from terminal using maven command:
```bash
  ./gradlew clean build
```  
### Run the application
1. Start the container with PostgreSQL database:
```bash
   docker-compose up
```  
2. Run the application
- (Option 1) from IntelliJ IDEA by clicking on the 'Run' button in the UrlShortenerApplication class.
- (Option 2) from terminal:
```bash
  ./gradlew bootRun
```

### Check application's health
Application's health can be checked using the following endpoint:
http://localhost:8080/actuator/health

### Run tests
Tests can be run with the following maven command from the project's root:
  ```bash
  ./gradlew test
  ```   
### Sample requests:
#### Create a shortened url:
POST http://localhost:8080/v1/url_shortener
<br/>
body text:
https://www.youtube.com/watch?v=bE6w6zYYQYU&ab_channel=EasyGerman

#### Redirect to long url using short url id  
GET http://localhost:8080/v1/url_shortener/aHR0cHM6
where aHR0cHM6 id from POST request

## Some TODOs for production-ready version:
1. Create a production database resource and keep the dockerized version for local development.
2. Store username, password and hosts in Cloud like AWS Secrets Manager or Hashicorp Vault.
3. Add more tests for testing expired links, add tests for controller. 
4. Secure endpoints with Spring Security. 
5. Create AWS API Gateway (or analogue) resource and configure logging of request and response. Remove 'org.springframework.web.server.adapter.HttpWebHandlerAdapter' property.
6. Add OpenAPI for endpoint documentation.
7. Add code coverage tooling.
8. Add cash for get requests.
9. Provide analytics on link usage.
10. Add user registration functionality.
11. Add logging and monitoring.
12. Add rate limiting to prevent abuse.
13. Add validation (Ensure that the URLs being shortened do not contain malicious content).
14. All communication between clients and the service should be encrypted using HTTPS to prevent eavesdropping and man-in-the-middle attacks.
# NewsLetter-Subscription
Created springboot application for news letter subscription.

## Technologies used:

I have developed the application in Springboot with JPA.
Project: Maven Project
SpringBoot Version:3.0.6
Java version:11

### Dependencies added:

1.spring-boot-starter-data-jpa
2.spring-boot-starter-web
3.spring-boot-starter-test
4.Junit test
5.H2

#### DataBase Used:

in memory database H2.

##### plugins used

spring-boot-maven-plugin

###### Business logic

SubscriptionController---> In this class i used Rest end points like @GetMapping,@PostMapping,@DeleteMapping for retraving,creating,deleting for the subscribers

SubscriptionServiceImpl ----> In this class i written main logic by invoking JPA Repository methods from Service interface methods to support controller class methods.

SubscriptionService ----> In this i defined methods to be implemented in ServiceImpl class.

###### Properties

application.properties -----> I have given basic properties like database connection.

###### Dockerizing the application:
Dockerfile---->given commands and instructions like FROM,EXPOSE,ADD,CMD to upload the application jar in container for creation of the image.


###### Test Cases Created:
SubscriptionControllerTest.class ---> created Junit tests for the controller class methods



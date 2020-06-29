# OptusAssessment
Project developed for Optus Assessment 

##install this project using 
./mnvw clean install

##Run this project with 

./mvnw spring-boot:run

##To Run All tests please use 

./mvnw test -Dtest=Test*

##For Only Integration testing please use

./mvnw test -Dtest=TestInt*

##Login using  
username: optus
password: candidates

## Use the following urls for testing after running application 

#task 1 : test using different inputs in body of post requests

http://localhost:8080/counter-api/search

#task 2 : test with different values 

http://localhost:8080/counter-api/top/5

 
##By Default fileTest.txt is being used inside of resource directory, you can provide path to your file via file.path property inside application.property if its empty then default text which was provided with task will be used. 




# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* This sample project contains example for saving user data based on roles.
* Version 1.0

## How do I get set up? ##

* download or clone the project 
  open/import the project as existing mvn project in IDE
  
Configurations
---------------
### Database configuration ###
>go to application.properties and do the following:
  spring.datasource.url=jdbc:mysql://localhost:3306/sampledatabaseschema?
  useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
  
(I had taken my sql to create the schema. Change the port and schema name from 'sampledatabaseschema' to  your specific one and create the schema in database before running the app)
    
  >spring.datasource.username = "your database username"
  >spring.datasource.password = "your database password"
  
 ### Mail configuration ###
  > open mail.properties and change the below:
    >spring.mail.default-encoding=UTF-8
    >spring.mail.host=smtp.gmail.com
    >spring.mail.username= "your mail id"
    >spring.mail.password= "your mail password"
    >spring.mail.port  = 587
    >spring.mail.protocol=smtp

  

Deployment instructions
-----------------------
 
 ### customizing HTML Templates and FTL templates###
 
 You can customize the html templates that were used to send the mails present in src/main/resources folder

  >mails will be sent when:
    * a user creates the account
    * forgets the password
    
  >go to sampleapp.java and run as java application
  
  >after running the sampleapp.java, database tables will be created. go to the schema, find the table with name "Role" and insert the 
   role data as follows: you can add as many roles required.
   INSERT INTO `sampledatabaseschema`.`role` (`role_id`, `role_name`) VALUES ('1', 'ADMIN');
   INSERT INTO `sampledatabaseschema`.`role` (`role_id`, `role_name`) VALUES ('2', 'MERCHANT');

  
  you can change the port number of the server in application.properties. I had specified the port number as 9093.
  server.port=9093
  
Testing
-------
you can test the API's using any tools like postman by passing the JSON data

![signin](https://user-images.githubusercontent.com/37467247/84102928-4ec5eb80-aa2f-11ea-9a14-281b7c0add23.PNG)
![signup](https://user-images.githubusercontent.com/37467247/84102932-4ff71880-aa2f-11ea-9182-238715864923.PNG)

### Who do I talk to? ###

*srihari
*email: vellaturi.srihari92@gmail.com

# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* This sample project contains example for saving user data based on roles.
* Version 1.0

## How do I get set up? ##

* download or clone the project 
  open/import the project as existing mvn project in IDE
  
  
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

  

## Deployment instructions ##
 
 ### customizing HTML Templates and FTL templates###
 
 You can customize the html templates that were used to send the mails present in src/main/resources folder
 ----------------------------------------------------------------------------------------------------------
  >mails will be sent when:
    * a user creates the account
    * forgets the password
  
  >go to sampleapp.java and run as java application
  
  you can change the port number of the server in application.properties. I had specified the port number as 9093.
  server.port=9093

### Who do I talk to? ###

srihari
email: vellaturi.srihari92@gmail.com

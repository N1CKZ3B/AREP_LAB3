# Application Server Architectures, Object Meta Protocols, IoC Pattern, Reflection

## Description
This project is a Java application that implements a simple HTTP server. The server can handle dynamic requests and serve static files from a resource directory.

## Requirements

* Java 8 or higher
* Maven

## Project Structure

* src/main/java/com/mycompany/reflexion: Everything on the reflection topic and a dependency on creating a JUnit class.
* src/main/java/com/mycompany/springeci/HttpServer.java: HTTP server implementation.
* src/main/java/com/mycompany/springeci/HelloService.java: REST controller with various endpoints.
* src/main/resources: Directory for static files.

## Installation

1. Clone the repository and navigate through the folder.
```
   git clone https://github.com/N1CKZ3B/AREP_LAB3
   cd AREP_LAB3
 ```

2. Compile using maven

```
  mvn clean install
```

3. Run the project
   * For the reflection topic worked on class
      ```
        java -cp target/springeci-1.0-SNAPSHOT.jar com.mycompany.reflexion.JUnitECI com.mycompany.reflexion.ClassToBeTested
      ```
   * For the http server
       ```
            java -cp target/classes com.mycompany.springeci.HttpServer
       ```
     * Endpoints
          * GET /hello: Returns a greeting message.
          * GET /tomorrow: Returns the day of the week for tomorrow.
          * GET /euler: Returns the value of Euler's number.
          * GET /editor: Returns the editor's name.
          * GET /greeting?name=<name>: Returns a personalized greeting.
          * GET /: Serves the index.html file.
          * GET /staticfile?file=<file_name>: Serves a specified static file.
  * Running default service
    ```
     java -cp target/springeci-1.0-SNAPSHOT.jar com.mycompany.springeci.Springeci com.mycompany.springeci.HelloService
    ```

-----------------------------------

# REVIEW

 1. When executing the http server command
  ![image](https://github.com/user-attachments/assets/3c6a18a3-a376-4c76-8296-d5f4ecd2cf59)

 2. We are able to see the http server in which we are also able to run the multiple services and the ability to recognize static files.
  
    ![image](https://github.com/user-attachments/assets/1726c125-0bb6-4a1e-aa98-67c307e37f48)

    
    ![image](https://github.com/user-attachments/assets/da5f7cd3-5159-4fa5-ad7b-8b5cefbe9cd2)




     

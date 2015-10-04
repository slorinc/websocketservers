# Server B

This component hosts the WebSocket endpoints to refresh the data and to
push it to subscribers.

The frontend contains a SockJs client to connect to the WebSocket endpoint
of the backend and to subscribe to a channel and display the data periodically
based on the refresh timer received from Server A.

If Server A is not running only default data will be displayed by the
frontend.

Build:
--

Spring Boot application:
--

By commenting out the scope field in the following dependency you can run
the project with spring-boot:run maven goal. 

    <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-tomcat</artifactId>
       <!--scope needs to commented out to run with spring-boot:run goal-->
       <!--<scope>provided</scope>-->
    </dependency>

The following URL can be used:

Access the frontend:

    http://localhost:8080/serverb-1.0.0-SNAPSHOT

WAR packaging:
--

Verify the pom.xml has the following dependency with scope set to provided!

    <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-tomcat</artifactId>
       <!--scope needs to commented out to run with spring-boot:run goal-->
       <scope>provided</scope>
    </dependency>

Then build the WAR archive with:

    mvn clean install
    
The packaged WAR file will be in the /target folder. Deploy and run.

The following URL can be used:

Access the frontend of Server B (Server B must be running!)

    http://localhost:<TOMCAT PORT>/serverb-1.0.0-SNAPSHOT


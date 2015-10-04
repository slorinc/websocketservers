# Server A

This component generates the random data periodically to be pushed to
server A via WebSocket connection. The refresh frequency can be set on
the admin page.

Also hosts a redirect point to the frontend of Server B and an
administration page to change refresh times.

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

The following URLs can be used:

Access the frontend of Server B (Server B must be running!)

    http://localhost:9090/servera-1.0.0-SNAPSHOT

Access the Admin page:

    http://localhost:9090/servera-1.0.0-SNAPSHOT/internal/admin

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

The following URLs can be used:

Access the frontend of Server B (Server B must be running!)

    http://localhost:<TOMCAT PORT>/servera-1.0.0-SNAPSHOT

Access the Admin page:

    http://localhost:<TOMCAT PORT>/servera-1.0.0-SNAPSHOT/internal/admin

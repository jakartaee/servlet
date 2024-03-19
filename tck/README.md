# Jakarta Servlet

This repository contains the code for Jakarta Servle TCK

About Jakarta Servlet TCK
-------------------------
Jakarta Servlet TCK defines a server-side API for handling HTTP requests and is based on Junit5 and Arquillian.

Building
--------
Prerequisites:

* JDK 11+
* Maven 3.9.0+

Run the build: 

`mvn install`

Running TCK 
------------
You need to configure your Apache Maven build to run tests from the tck artifacts.
This will be achieved by adding a dependency within your surefire configuration
```xml
    <dependencies>
      <dependency>
        <groupId>jakarta.servlet</groupId>
        <artifactId>tck-runtime</artifactId>
      </dependency>
    </dependencies>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>${surefire.version}</version>
      <configuration>
        <dependenciesToScan>
          <dependenciesToScan>jakarta.servlet:tck-runtime</dependenciesToScan>
        </dependenciesToScan>
        <systemProperties>
          <!-- if the servlet container doesn't support optional cross context -->  
          <servlet.tck.support.crossContext>false</servlet.tck.support.crossContext>
          <!-- if the servlet container doesn't support optional http2 push -->  
          <servlet.tck.support.http2Push>false</servlet.tck.support.http2Push>
        </systemProperties>          
      </configuration>
    </plugin>
```

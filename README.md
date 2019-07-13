# Jakarta Servlet

This repository contains the code for Jakarta Servlet.

[Online JavaDoc](https://javadoc.io/doc/jakarta.servlet/jakarta.servlet-api/)

Building
--------

Prerequisites:

* JDK8+
* Maven 3.0.3+

Run the build: 

`mvn install`

The build runs a copyright check and generates the jar, sources-jar and javadoc-jar by default.

The API jar can be found in /api/target.

Checking findbugs
-----------------

`mvn -DskipTests -Dfindbugs.threshold=Low findbugs:findbugs`

About Jakarta Servlet
-------------

Jakarta Servlet defines a server-side API for handling HTTP requests and responses,
and a packaging model for such server-side applications.


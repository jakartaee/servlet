Java Servlet API
================

Building
--------

Prerequisites:

* JDK8+
* Maven 3.0.3+

Run the build: 

`mvn install`

The build runs copyright check and generates the jar, sources-jar and javadoc-jar by default.

Checking findbugs
-----------------

`mvn -DskipTests -Dfindbugs.threshold=Low findbugs:findbugs`


# Spark example webapp

This is an example application that uses the [Spark framework](http://sparkjava.com/) and [Kotlin](http://kotlinlang.org/)
to write a small, concise and fast web application.

# How to start

You can start the web application from your IDE by running the `main` method inside `SparkExample.kt`.

You can also build the JAR file and start it.

    gradle build
    OR if you don't have gradle installed
    ./gradlew build
    THEN
    java -jar build/libs/spark-webapp-fat-1.0.jar

Interact with the application by

* Going to [http://localhost:4567](http://localhost:4567) in your browser
* Using curl:

      curl http://localhost:4567/person
      curl http://localhost:4567/person/1
      curl http://localhost:4567/person?last_name=Schulze

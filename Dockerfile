FROM openjdk:17
COPY target/shopeymart-0.0.1-SNAPSHOT.jar /shopeymart.jar

ENTRYPOINT ["java", "-jar", "/shopeymart.jar"]
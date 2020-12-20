FROM openjdk:15-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
ADD target/pagacoin-1.0.jar app.jar
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://api-database/pagantis_db", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
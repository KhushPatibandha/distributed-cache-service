FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/cache.client-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","/app.jar"]
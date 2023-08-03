FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/ogym-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 14641
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]
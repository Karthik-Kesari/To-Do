# todo/Dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY todo/target/todo-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
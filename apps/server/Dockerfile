FROM openjdk:17

WORKDIR /app
COPY . /app

RUN ./mvnw  -B -DskipTests clean package

EXPOSE 8080

CMD ["java", "-jar", "target/server-0.0.1-SNAPSHOT.jar"]

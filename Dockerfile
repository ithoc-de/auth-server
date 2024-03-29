FROM maven:3.9.0-eclipse-temurin-17 as build
WORKDIR /usr/app
ADD . /usr/app
RUN --mount=type=cache,target=/root/.m2 mvn -f /usr/app/pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /usr/app/target/auth-server.jar /app/auth-server.jar
ENTRYPOINT java -jar /app/auth-server.jar

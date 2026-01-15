FROM eclipse-temurin:25 AS build
WORKDIR /workspace
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package

FROM eclipse-temurin:25
VOLUME /tmp
COPY --from=build /workspace/target/*.jar app.jar
RUN apt-get update && apt-get install -y curl
RUN curl -L -o opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar
ENTRYPOINT exec java -javaagent:opentelemetry-javaagent.jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar

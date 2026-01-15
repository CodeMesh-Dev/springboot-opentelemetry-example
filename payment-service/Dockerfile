FROM maven:3.9.5-amazoncorretto-17 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package
RUN ls -lrt /usr/src/app/target/

FROM amazoncorretto:17-al2-jdk

VOLUME /tmp
COPY --from=build /usr/src/app/target/payment-service-0.0.1-SNAPSHOT.jar app.jar

RUN curl -L -o opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar

ENTRYPOINT exec java -javaagent:opentelemetry-javaagent.jar $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar

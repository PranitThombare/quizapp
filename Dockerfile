# Stage 1 — build with Maven
ARG MAVEN_IMAGE=maven:3.9.6-eclipse-temurin-17
FROM ${MAVEN_IMAGE} AS builder
WORKDIR /workspace
COPY pom.xml .
# copy mvn settings if needed
COPY .mvn .mvn
COPY mvnw mvnw
# copy source
COPY src src
RUN mvn -B -DskipTests package

# Stage 2 — runtime
# Use a JRE matching your Java target (update if you target Java 24)
FROM eclipse-temurin:17-jre
WORKDIR /app
# copy jar (adjust artifactId/version if needed)
COPY --from=builder /workspace/target/*.jar app.jar

# Expose port used by Spring Boot (default 8080)
EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]

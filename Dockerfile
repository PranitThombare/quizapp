# Stage 1: build with Maven (adjust image if you need newer Java)
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /workspace

# copy only maven descriptor first for caching
COPY pom.xml ./
## if you have .mvn and mvnw, copy them too (optional)
#COPY .mvn .mvn
#COPY mvnw mvnw

# download dependencies
RUN mvn -B -DskipTests dependency:go-offline

# copy source and build
COPY src src
RUN mvn -B -DskipTests package

# Stage 2: runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app

# copy jar from builder (change wildcard if multiple jars)
COPY --from=builder /workspace/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]

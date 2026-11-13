FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /workspace

# Copy parent and child POM files
COPY pom.xml .
COPY microservice-a/pom.xml microservice-a/pom.xml
COPY microservice-b/pom.xml microservice-b/pom.xml

# Install the parent POM
RUN mvn -q -N install

# Copy only Microservice B source code
COPY microservice-b/src microservice-b/src

# Build Microservice B
RUN mvn -q -f /workspace/pom.xml -pl microservice-b package -DskipTests


# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /workspace/microservice-b/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
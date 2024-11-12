FROM maven:3.8.7-eclipse-temurin-19-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the source code to the container
COPY . .

# Build the application with Maven
#RUN mvn package
RUN mvn clean package -DskipTests

EXPOSE 8090




CMD ["java", "-jar", "/app/sognisport-challenge-api.jar", "-Dquarkus.http.port=8090"]
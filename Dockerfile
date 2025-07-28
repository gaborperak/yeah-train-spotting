# Use a base image with JDK 21
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/train-scheduler-*.jar app.jar

# Create a directory for the SQLite database
RUN mkdir -p /app/data

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
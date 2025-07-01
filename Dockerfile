# Use a lightweight OpenJDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the built jar from Gradle's build/libs
COPY build/libs/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Load env variables from file if needed (optional for debugging)
# ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

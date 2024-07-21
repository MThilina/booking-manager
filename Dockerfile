# Use a base image with Java 17
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host machine to the container
COPY target/booking-management.jar booking-management.jar

# Expose the port that the application will run on
EXPOSE 8080
EXPOSE 1500
EXPOSE 3306

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "booking-management.jar"]
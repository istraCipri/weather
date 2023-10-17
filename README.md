# Weather App

The Weather app is a Spring Boot application that provides weather information for various cities. This README will guide you through the installation process, cleaning, and running the app using Maven.

## Prerequisites

Before you begin, ensure that you have the following prerequisites installed on your system:

- [Java Development Kit (JDK)](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html) 17 or higher.
- [Maven](https://maven.apache.org/download.cgi) for building and managing dependencies.

## Installation

Follow these steps to get the Weather app up and running on your system:

**Clone the Repository**:
   ```
   git clone https://github.com/istraCipri/weather.git
   cd weather
   ```
## Build, run and clean the Application
Use Maven to build, run and clean the application and download its dependencies:

**1.Build the Application**:
   ```
  mvn clean install
   ```
**2.Run the Application**:
   ```
  mvn spring-boot:run
   ```
**3.Clean the Application**:
   ```
  mvn clean
   ```

##Accessing the App

Open your web browser and navigate to http://localhost:8080 to access the application.

Swagger: http://localhost:8080/swagger-ui/index.html#/weather-controller/getWeatherAverages
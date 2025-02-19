# Stock Tracker Setup Guide

## System Requirements

### 1. Java Development Kit (JDK)
- Required: JDK 23 or later
- To check your Java version:
  ```bash
  java -version
  ```
- If not installed or outdated, download from: https://adoptium.net/

### 2. Maven
- Required: Maven 3.8.0 or later
- To check your Maven version:
  ```bash
  mvn -version
  ```
- If not installed, download from: https://maven.apache.org/download.cgi

### 3. Alpha Vantage API Key
- Required: Free API key from Alpha Vantage
- Get it from: https://www.alphavantage.co/support/#api-key

## Setup Steps

1. **Clone/Download the Project**
   ```bash
   git clone [your-repository-url]
   cd stock-price-tracker
   ```

2. **Configure API Key**
   - Navigate to `src/main/resources/`
   - Open `config.properties`
   - Replace `YOUR_API_KEY_HERE` with your Alpha Vantage API key
   ```properties
   alpha.vantage.api.key=YOUR_ACTUAL_API_KEY
   ```

3. **Install Dependencies**
   ```bash
   mvn clean install
   ```

## Running the Application

### Method 1: Using Maven
```bash
mvn javafx:run
```

### Method 2: Using JAR file
1. Build the JAR:
   ```bash
   mvn clean package
   ```
2. Run the JAR:
   ```bash
   java -jar target/stock-price-tracker-1.0-SNAPSHOT.jar
   ```

## Troubleshooting

### Common Issues:

1. **Java Version Error**
   - Error: "Java version not supported"
   - Solution: Install JDK 23 and ensure JAVA_HOME is set correctly
   ```bash
   echo %JAVA_HOME%  # Windows
   echo $JAVA_HOME   # Linux/Mac
   ```

2. **Maven Build Failure**
   - Error: "Could not resolve dependencies"
   - Solution: Check internet connection and run:
   ```bash
   mvn clean install -U
   ```

3. **JavaFX Not Found**
   - Error: "Error: JavaFX runtime components are missing"
   - Solution: Ensure you're using the Maven command to run the application

4. **API Key Error**
   - Error: "Failed to load API key" or "Invalid API key"
   - Solution: Double-check your API key in config.properties

## System-Specific Notes

### Windows
- Ensure PATH environment variable includes Java and Maven
- Use Windows PowerShell or Command Prompt to run commands

### macOS/Linux
- You might need to set execute permissions:
  ```bash
  chmod +x mvnw
  ```
- Use Terminal to run commands

## Performance Requirements

- Minimum RAM: 2GB
- Disk Space: 500MB free space
- Internet Connection: Required for real-time stock updates

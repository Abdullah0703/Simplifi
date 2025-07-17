# Simplifi Project

This is a Java Spring Boot application designed to connect with a MySQL database.

## ✅ Prerequisites

- Java **17** or higher  
- Maven **3.6** or higher  
- **Docker** (optional for containerization)  
- **MySQL** (local or remote instance)

---

## 🛠️ Building the Project

Use Maven wrapper to build the application:

```bash
./mvnw clean package
```

## 🚀 Running the Application
🖥️ Locally
Make sure your MySQL server is up and running.

Update application.properties:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/simplifi
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=create
```

## Run the application:

```bash
java -jar target/simplifi-0.0.1-SNAPSHOT.jar
```

## 🐳 Using Docker
🔨 Build the Docker image:

```bash
docker build -t simplifi .
```

## ▶️ Run the container:

```bash
docker run -p 8080:8080 simplifi
```

If using a local MySQL DB, update your application.properties:

```bash
spring.datasource.url=jdbc:mysql://host.docker.internal:3306/simplifi
```

## ⚙️ Configuration
Edit the src/main/resources/application.properties file:

```bash
spring.datasource.url=jdbc:mysql://host.docker.internal:3306/simplifi
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=create
```



















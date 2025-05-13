# Usa una imagen base de OpenJDK
FROM openjdk:21-jdk-slim

# Copia el archivo JAR de tu aplicación al contenedor
COPY target/login-api-0.0.1-SNAPSHOT.jar /app/mi-api-login.jar

# Expone el puerto 8080 para que sea accesible desde fuera del contenedor
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "/app/mi-api-login.jar"]

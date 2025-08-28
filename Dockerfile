#OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

# Directorio de trabajo en el contenedor
WORKDIR /app

# JAR generado
COPY target/farmatodo-0.0.1-SNAPSHOT.jar app.jar

# Puerto
EXPOSE 8080

# Comandos
ENTRYPOINT ["java","-jar","app.jar"]

# Imagen base de Java 22
FROM eclipse-temurin:22-jdk-alpine

# Configura el directorio de trabajo
WORKDIR /app

# Copia solo los archivos necesarios para resolver dependencias
COPY ./pom.xml /app
COPY ./.mvn /app/.mvn
COPY ./mvnw /app

# Asignar permisos de ejecución al archivo mvnw
RUN chmod +x /app/mvnw

# Convertir finales de línea CRLF a LF (opcional si desarrollas en Windows)
RUN sed -i 's/\r$//' /app/mvnw

# Descargar dependencias offline
RUN ./mvnw dependency:go-offline

# Copia el código fuente de la aplicación
COPY ./src /app/src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests -Pprod

# Exponer el puerto
EXPOSE 8084

# Entrypoint para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/target/unmsm-0.0.1-SNAPSHOT.jar"]

# Configurar variables de entorno
ENV AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
ENV AWS_S3_BUCKET_NAME=${AWS_S3_BUCKET_NAME}
ENV AWS_S3_REGION=${AWS_S3_REGION}
ENV SERVER_PORT=${SERVER_PORT:-8084}

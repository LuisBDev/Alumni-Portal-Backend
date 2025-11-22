# Etapa 1: Build
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY . .
# Omitimos tests aquí para agilizar el build de la imagen (los tests corren en GitHub Actions antes)
RUN ./mvnw clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# SEGURIDAD: Creamos un usuario sin privilegios
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiamos el JAR del builder
COPY --from=builder /app/target/*.jar app.jar

# Variables por defecto (se sobrescriben en docker-compose)
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
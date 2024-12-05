# Alumni Portal

![Java](https://img.shields.io/badge/Java-22-blue)
![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot-brightgreen)
![MySQL](https://img.shields.io/badge/Database-MySQL-orange)
![Hibernate](https://img.shields.io/badge/ORM-Hibernate-orange)
![REST API](https://img.shields.io/badge/API-REST-green)
![API Documentation](https://img.shields.io/badge/API%20Docs-Swagger-green)
![JWT](https://img.shields.io/badge/Authentication-JWT-blue)
![Version](https://img.shields.io/badge/Version-1.0.0-brightgreen)
![Testing](https://img.shields.io/badge/Testing-JUnit-orange)

# Índice

- [📖 Descripción del Proyecto](#-descripción-del-proyecto)
- [🎯 Propósito del Proyecto](#-propósito-del-proyecto)
- [✨ Características Principales](#-características-principales)
- [🛠 Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [📋 Requisitos del Sistema](#-requisitos-del-sistema)
- [🚀 Cómo Ejecutar el Proyecto](#-cómo-ejecutar-el-proyecto)
- [🗂 Estructura del Proyecto](#-estructura-del-proyecto)
- [📦 Descripción de los Componentes](#-descripción-de-los-componentes)
- [📜 Jenkins Pipeline](#-jenkins-pipeline)
- [🔧 Configuración de Plugins](#-configuración-de-plugins)
- [🌍 Variables de Entorno](#-variables-de-entorno)
- [🛠️ Stages del Pipeline](#-stages-del-pipeline)
- [📊 Resultados del Pipeline](#-resultados-del-pipeline)

## 📖 Descripción del Proyecto

El **Portal Alumni UNMSM** es una aplicación web que actúa como puente entre la comunidad universitaria de la
Universidad
Nacional Mayor de San Marcos y el mundo profesional. Diseñada para potenciar el networking, esta plataforma facilita la
gestión de perfiles, la participación en actividades académicas y la búsqueda de oportunidades laborales en un entorno
intuitivo y moderno.

---

## 🎯 Propósito del Proyecto

El Portal Alumni es una aplicación diseñada para potenciar la comunidad
estudiantil de la Universidad Nacional Mayor de San Marcos (UNMSM). Este portal no solo facilita la creación y
personalización de perfiles profesionales, sino también la búsqueda de actividades académicas, eventos y oportunidades
laborales, fomentando la interacción directa entre estudiantes y empresas en un entorno moderno y accesible.

---

## ✨ Características Principales

### Gestión de Acceso

* **Integración con el Sistema SUM:** Registro de estudiantes utilizando credenciales institucionales.
* **Registro de Empresas:** Empresas pueden crear cuentas proporcionando información de contacto relevante.
* **Gestión de Contraseñas:** Opciones para cambiar la contraseña actual tanto para estudiantes como para empresas.
* **Eliminación de Cuentas:** Posibilidad de desactivar cuentas de manera autónoma.

### Gestión de Perfiles

* **Personalización de Perfiles:** Estudiantes pueden actualizar sus perfiles profesionales, incluyendo datos
  académicos.
* **Registro Detallado:** Opciones para agregar experiencia laboral, educación, certificaciones, proyectos y habilidades
  clave.
* **Generación de CV:** Creación automática de un currículum en formato ATS a partir de los datos del perfil del
  estudiante.
* **Gestión de Información Empresarial:** Empresas pueden editar y personalizar su información de contacto.

### Gestión de Actividades

* **Publicación de Actividades:** Estudiantes y empresas pueden compartir actividades académicas, eventos y
  conferencias.
* **Búsqueda de Actividades:** Estudiantes pueden explorar actividades según sus preferencias.
* **Inscripción y Cancelación de Actividades:** Los estudiantes pueden registrarse o cancelar su inscripción en
  actividades de interés.
* **Gestión de Participantes:** Visualización de la lista de participantes registrados en cada actividad.

### Gestión de Empleabilidad

* **Publicación de Ofertas Laborales:** Empresas pueden publicar vacantes con detalles específicos.
* **Búsqueda de Oportunidades:** Estudiantes pueden explorar ofertas laborales según sus preferencias y habilidades.
* **Aplicación y Cancelación de Aplicaciones:** Gestión de postulaciones por parte de los estudiantes.
* **Seguimiento de Aplicantes:** Empresas pueden visualizar a los postulantes interesados en sus ofertas laborales.

---

## 🛠 Tecnologías Utilizadas

### Backend

- **Java 22**: Lenguaje de programación.
- **Spring Boot 3**: Framework principal.
- **Spring Security (JWT)**: Autenticación y autorización.
- **Maven**: Gestión de dependencias.
- **ModelMapper**: Mapeo de DTOs a entidades.
- **Lombok**: Reducción de código repetitivo.

### API REST

- **API REST**: Protocolo de comunicación que permite realizar operaciones de consulta sobre los datos almacenados.

### Base de Datos

- **MySQL**: Gestión de datos persistentes.

### Servicios Cloud

- **AWS Lambda**: Procesamiento asíncrono de correos.
- **AWS S3**: Almacenamiento de imágenes.

### Seguridad

- **JWT**: Biblioteca que permite trabajar con tokens JWT (JSON Web Tokens) para la autenticación y
  autorización de usuarios.

### Pruebas y Documentación

- **Swagger**: Generación de documentación de API.
- **JUnit**: Framework para pruebas unitarias.
- **Mockito**: Simulación de comportamientos en pruebas unitarias.
- **JaCoCo**: Cobertura de pruebas.

## 📋 Requisitos del Sistema

- **Java 22** instalado.
- **Maven 3.8+** configurado.
- **MySQL 8.0+** con una base de datos configurada.
- **IntelliJ IDEA** (o cualquier otro IDE compatible con Java)

- Configuración de credenciales para AWS S3 y Lambda.

## 🚀 Cómo Ejecutar el Proyecto

1. **Clonar el Repositorio**
   ```bash
   git clone https://github.com/LuisBDev/Alumni-Portal-Backend.git
   cd Alumni-Portal-Backend

2. **Configura el archivo application.properties según tu ambiente**

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/alumniportal
    spring.datasource.username=youruser
    spring.datasource.password=yourpassword
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    spring.jpa.hibernate.ddl-auto=update

3. **Agrega las variables de entorno**:  
   A continuación, agrega las siguientes variables de entorno directamente en la configuración de tu sistema operativo o
   IDE.

   ```dotenv
   AWS_ACCESS_KEY_ID=your_access_key
   AWS_SECRET_ACCESS_KEY=your_secret_key
   AWS_S3_REGION=your_region
   ```
   Reemplaza los valores de ejemplo con los detalles de tu configuración real.

4. **Construir y Ejecutar el Proyecto**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Acceder a la Aplicación**
    - La aplicación estará disponible en `http://localhost:8080`.

6. **Documentación de la API**
    - La documentación de la API estará disponible en `http://localhost:8080/swagger-ui.html`.

## 🗂 Estructura del Proyecto

```
Alumni-Portal-Backend/
├───.mvn
│   └───wrapper
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───alumniportal
│   │   │           └───unmsm
│   │   │               ├───config
│   │   │               │   ├───awsConfig
│   │   │               │   └───SpringSecurity
│   │   │               ├───controller
│   │   │               ├───dto
│   │   │               │   ├───RequestDTO
│   │   │               │   └───ResponseDTO
│   │   │               ├───exception
│   │   │               ├───mapper
│   │   │               ├───model
│   │   │               ├───persistence
│   │   │               │   ├───impl
│   │   │               │   └───interfaces
│   │   │               ├───repository
│   │   │               ├───service
│   │   │               │   ├───impl
│   │   │               │   └───interfaces
│   │   │               └───util
│   │   └───resources
│           ├───application.properties
│           ├───application-dev.properties
│           ├───application-stg.properties
│           └───application-prod.properties
│   └───test
│       ├───java
│       │   └───com
│       │       └───alumniportal
│       │           └───unmsm
│       │               ├───controller
│       │               ├───Data
│       │               ├───persistence
│       │               │   └───impl
│       │               └───service
│       │                   └───impl
│       └───resources
│           ├───application.properties
│   
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## 📦 Descripción de los Componentes

### 1. **`config`**

Contiene configuraciones de la aplicación:

- **`awsConfig`**: Configuración para la interacción con servicios de AWS (S3, Lambda).
- **`SpringSecurity`**: Configuración de autenticación y autorización basada en JWT.

---

### 2. **`controller`**

Responsable de manejar las solicitudes HTTP y coordinar las respuestas utilizando los servicios.

---

### 3. **`dto`**

Define los objetos de transferencia de datos:

- **`RequestDTO`**: Estructuras para recibir datos de las solicitudes.
- **`ResponseDTO`**: Estructuras para devolver datos en las respuestas.

---

### 4. **`exception`**

Gestión centralizada de excepciones personalizadas para un manejo consistente de errores.

---

### 5. **`mapper`**

Define mapeos automáticos entre entidades y DTOs utilizando la biblioteca ModelMapper.

---

### 6. **`model`**

Contiene las clases que representan las entidades del dominio y reflejan la estructura de la base de datos.

---

### 7. **`persistence`**

Gestiona la interacción con los datos:

- **`impl`**: Implementaciones de lógica para la persistencia de datos.
- **`interfaces`**: Contratos que definen cómo interactuar con la capa de persistencia.

---

### 8. **`repository`**

Define los repositorios para interactuar con la base de datos mediante Spring Data JPA.

---

### 9. **`service`**

Gestiona la lógica de negocio:

- **`impl`**: Implementaciones concretas de los servicios de negocio.
- **`interfaces`**: Contratos para los servicios utilizados por los controladores.

---

### 10. **`util`**

Proporciona utilidades y clases auxiliares reutilizables para tareas comunes como validaciones, formatos, etc.

### 1. Arquitectura en Capas

Se separan las responsabilidades del sistema en capas distintas (como controller, service, persistence, repository,
model).

**Impacto:** Mejora la modularidad, facilita el mantenimiento y permite cambios en una capa sin afectar a las demás.

**Ejemplo:**

```java

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
public class ApplicationController {
    private final IApplicationService applicationService;

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationResponseDTO>> findAll() {
        List<ApplicationResponseDTO> applicationResponseDTOList = applicationService.findAll();
        return ResponseEntity.ok(applicationResponseDTOList);
    }
}

// ...

```

### 2. Implementación de Spring Security

Se usó Spring Security para la implementación de autenticación/autorización basada en roles.

**Impacto:** Protege el acceso a los distintos endpoints de la aplicación.

**Ejemplo:**

```java

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//    ...
    }
}
```

### 3. Implementación de Roles

Se definen roles para permitir un control de acceso granular a diferentes endpoints de la aplicación.

**Ejemplo:**

```java
public enum Role {
    USER,
    COMPANY
}
``` 

### 4. Uso de DTOs (Data Transfer Objects)

Para mover la información entre las distintas capas, en lugar de usar la entidad directamente se implementa un DTO, que
es un objeto simple que se usa netamente para transferir datos.

**Impacto:** Mejora la seguridad al no exponer cualquier información innecesaria.

**Ejemplo:**

```java

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    private Long id;
    private String email;
    private String password;
    private String paternalSurname;
//    ...
}
```

### 5. Manejo de Excepciones Centralizado

Se usa un @RestControllerAdvice , que proporciona un punto central para manejar las excepciones.

**Impacto:** Mejora la consistencia en las respuestas de error y facilita el mantenimiento.

**Ejemplo:**

```java

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
//      ...
    }
}
```

### 6. Implementación de Interfaces para Servicios

Se crean interfaces para los servicios, lo que mejora abstracción y facilita futuras pruebas unitarias.

**Impacto:** Mejora la mantenibilidad y permite cambiar implementaciones fácilmente.

**Ejemplo:**

```java
public interface IAuthService {
    AuthUserResponseDTO loginAcademic(LoginRequestDTO loginRequestDTO);
    //...
}

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    @Override
    public AuthUserResponseDTO loginAcademic(LoginRequestDTO loginRequestDTO) {
        //...
    }
}
```

### 7. Uso de Lombok

Es una libreria usada para reducir código repetitivo, propociona notaciones para getters, setters, constructores, etc.

**Impacto:** Mejora la legibilidad del código, reduce la probabilidad de errores y superficie de ataque.

**Ejemplo:**

```java

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequestDTO {
    private String name;
    private String password;
//  ...
}
```

### 8. Uso de ModelMapper

Simplifica el mapeo entre objetos de diferentes capas.

**Impacto:** Reduce el código repetitivo y mejora la mantenibilidad.

**Ejemplo:**

```java
public ActivityResponseDTO entityToDTO(Activity activity) {
    return modelMapper.map(activity, ActivityResponseDTO.class);
}
```

### 9. Uso de Swagger

Se usa swagger para documentar de manera automatizada los endpoints del proyecto.

**Impacto:** Facilita la documentación del proyecto.

**Ejemplo:**

```xml

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

### 9. Uso de Servicios AWS

Se utilizan servicios de AWS como S3 y Lambda para almacenar imágenes y procesar correos de manera asíncrona.

```java

@Bean
public LambdaClient lambdaClient() {
    AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
    return LambdaClient.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build();
}

@Bean
public S3Client s3Client() {
    AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
    return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .build();
}
```

### 10. Aplicación de Principios de Clean Code

Se implementan principios de Clean Code a lo largo del proyecto, especialmente en la capa de servicio. Esto incluye
métodos con responsabilidad única, nombres descriptivos, y una estructura clara y lógica del código.

**Impacto:** Mejora significativamente la legibilidad, mantenibilidad y escalabilidad del código.

**Ejemplo:**

```java

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements ICertificationService {

    private final ICertificationDAO certificationDAO;
    private final IUserDAO userDAO;
    private final CertificationMapper certificationMapper;

    @Override
    public List<CertificationResponseDTO> findAll() {
        List<Certification> certificationList = certificationDAO.findAll();
        if (certificationList.isEmpty()) {
            throw new AppException("No certifications found!", "NOT_FOUND");
        }
        return certificationMapper.entityListToDTOList(certificationList);
    }

    @Override
    public CertificationResponseDTO findById(Long id) {
        Certification certification = certificationDAO.findById(id);
        if (certification == null) {
            throw new AppException("Certification with id " + id + " not found!", "NOT_FOUND");
        }

        return certificationMapper.entityToDTO(certification);
    }
//    ...
}

```

# 📜 Jenkins Pipeline

Este pipeline está diseñado para automatizar el ciclo de vida de desarrollo de la aplicación **Alumni Portal**. Incluye
pasos para la construcción, pruebas, análisis de calidad, empaquetado, despliegue, pruebas de rendimiento y
notificaciones.

## 🔧 Configuración de Plugins

- **JDK**: `JDK22`
- **Maven**: Herramienta para la gestión de dependencias y la construcción.
- **SonarQube Scanner**: Análisis estático del código fuente.

## 🌍 Variables de Entorno

- **AWS_ACCESS_KEY_ID** / **AWS_SECRET_ACCESS_KEY**: Credenciales de AWS para interactuar con S3.
- **AWS_S3_BUCKET_NAME** / **AWS_S3_REGION**: Configuración del bucket S3.
- **SLACK_WEBHOOK_URL**: URL para notificaciones en Slack.
- **SLACK_CHANNEL**: Canal de Slack donde se envían notificaciones.
- **SPRING_BOOT_APP_JAR**: Ubicación del archivo JAR generado.
- **SPRING_BOOT_APP_URL**: URL de la aplicación Spring Boot para verificar su estado.

## 🛠️ Stages del Pipeline

## Ejecución de Etapas

![image](https://github.com/user-attachments/assets/573f5bd2-59bc-4488-b138-338fbeef1f85)

### 1. **Git Checkout**

Clona el repositorio desde GitHub en la rama `master`.

![image](https://github.com/user-attachments/assets/795635f1-6667-4a11-a749-77219fa422e8)


### 2. **Build with Maven**

Compila el proyecto utilizando Maven.

![image](https://github.com/user-attachments/assets/74b2d4ec-bb5f-4142-bc2b-24f2a929330b)


### 3. **Run Tests**

Ejecuta las pruebas unitarias definidas con **JUnit** y utiliza **Mockito** para los mocks.

![image](https://github.com/user-attachments/assets/25fab42d-f963-489e-b068-3de07946dc31)


### 4. **SonarQube Analysis**

Realiza un análisis estático del código fuente utilizando **SonarQube**, excluyendo las pruebas.

![image](https://github.com/user-attachments/assets/9c0dc849-1473-4989-96a9-80ab87291d48)


### 5. **Package**

Genera un archivo JAR ejecutable, omitiendo las pruebas.

![image](https://github.com/user-attachments/assets/cf4c5c59-7607-4107-8975-c386ba8a6ca5)


### 6. **Deploy (Publish)**

Despliega la aplicación Spring Boot y las variables necesarias.

![image](https://github.com/user-attachments/assets/d8a5ddd1-cd4d-4bc4-8ee9-d59b88437730)


### 7. **Health Check (Actuator)**

Verifica que la aplicación esté corriendo correctamente utilizando el endpoint `/actuator/health`.

![image](https://github.com/user-attachments/assets/9f8a2b2b-8d20-465c-b6f6-d89dcb0e0690)


### 8. **Performance Testing with JMeter**

Ejecuta pruebas de rendimiento utilizando un archivo JMX de JMeter preconfigurado.

![image](https://github.com/user-attachments/assets/2b85c978-eee7-41a1-9315-eba34925765a)


### 9. **Send Slack Notification**

Notifica en Slack el estado exitoso o fallido del pipeline con detalles del trabajo.

![image](https://github.com/user-attachments/assets/f0d94620-2b47-4887-9374-e66a6d0fd6b6)


## 📤 Comportamiento de Post-Ejecución

- **`failure`**: Si algún paso falla, envía una notificación a Slack indicando el error.
- **`always`**: Muestra un mensaje de finalización en la consola.

## 📊 Finalización del Pipeline

![image](https://github.com/user-attachments/assets/18067bb1-aedc-4e7a-b21a-85f19037fdd4)

## Timings del Pipeline

![image](https://github.com/user-attachments/assets/11a814ab-be29-4cb1-8238-ec8bc8f05371)


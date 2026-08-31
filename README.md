# DAW Framework 🚀

Este proyecto es un **framework web Java incremental y didáctico**, construido desde cero sobre **Tomcat Embebido**.

El objetivo principal es entender *"qué hay debajo del capó"* de frameworks modernos como Spring Boot, implementando paso a paso los componentes esenciales de una arquitectura web.

---

## 🛠️ Requisitos previos

* **Java JDK 25+**
* **Apache Maven 3.8+**

---

## 📋 Historial de versiones (Changelog)

<details open>
<summary><b>v0.2.2-dao-repository-entities</b> <i>(Versión actual)</i></summary>

### Añadido
* Capa de persistencia con entidades de base de datos (`UserEntity`, `RoleEntity`).
* Mappers de acceso a datos (`UserDaoMapper`, `RoleDaoMapper`) para transformar filas de `ResultSet` en entidades.
* Mappers de dominio (`UserRepositoryMapper`, `RoleRepositoryMapper`) para transformar entidades de persistencia a modelos de dominio (`User`, `Role`).
* Implementación de acceso a datos con JDBC puro (`UserDaoImpl`, `RoleDaoImpl`) con soporte para lecturas totales (`findAll`) e individuales (`findById`).
* Implementación de `UserRepositoryImpl` orquestando la carga de relaciones $N:M$ (roles por usuario) envolviendo búsquedas en `Optional`.
* Configuración explícita del contenedor IoC en `AppConfig`.

### Modificado
* `AppInitializer` delega la configuración de dependencias a `AppConfig.configure()`.

</details>

<details>
<summary><b>v0.2.1-flyway-db</b></summary>

### Añadido
* Gestión del pool de conexiones JDBC mediante HikariCP en `DataSourceManager`.
* Migraciones automáticas con Flyway en `DatabaseMigrator` con limpieza condicional controlada por `flyway.clean-disabled`.
* Script de migración inicial `V1__create_tables.sql` con tablas (`users`, `roles`, `user_roles`) y datos iniciales en MariaDB.
* Registro del bean `DataSource` dentro del contenedor IoC (`AppContext`).

### Modificado
* `AppInitializer` gestiona el ciclo de vida de la base de datos (arranque y migración en `contextInitialized`, cierre del pool en `contextDestroyed`).

</details>

<details>
<summary><b>v0.2.0-request-body-json</b></summary>

### Añadido
* Método genérico `<T> T getBodyAs(Class<T> clazz)` en la clase `Request` para deserializar el cuerpo JSON de peticiones `POST` y `PUT` utilizando Jackson.
* Soporte para respuestas HTTP `201 Created` mediante la invocación a `Response.created(...)`.
* Definición de la ruta `POST /users` en `ApiRoutes`.

> ⚠️ **Nota pedagógica sobre Jackson:**  
> Las clases de dominio o DTOs que se deseen deserializar desde un cuerpo JSON (como `User` o `Role`) **deben disponer de un constructor por defecto (sin parámetros)** y sus correspondientes métodos *setter*. Jackson utiliza este constructor para instanciar el objeto mediante reflexión antes de poblar sus atributos.

</details>

<details>
<summary><b>v0.1.9-router</b></summary>

### Añadido
* Sistema de enrutado funcional en `routing` con las clases `RouteHandler`, `RouteKey` y `Router` (sintaxis fluida estilo Laravel).
* Clase de definición de rutas de usuario `ApiRoutes` fuera del núcleo del framework.

### Modificado
* `AppInitializer` registra las rutas mediante `ApiRoutes.configure()` y guarda la instancia de `Router` en el `AppContext`.
* `FrontController` se vuelve 100% agnóstico a las rutas de la aplicación; resuelve el `RouteHandler` correspondiente desde el `Router` y ejecuta la petición.

</details>

<details>
<summary><b>v0.1.8-app-context</b></summary>

### Añadido
* Contenedor de Inyección de Dependencias `AppContext` (Singleton con `ConcurrentHashMap`) en `core.context`.
* Listener del ciclo de vida `AppInitializer` (`ServletContextListener`) para registrar los beans en el arranque del servidor.

### Modificado
* `TomcatServer` registra `AppInitializer` como listener del contexto web.
* `FrontController` obtiene `UserController` desde `AppContext` durante la fase de `init()`.

</details>

<details>
<summary><b>v0.1.7-http-wrappers</b></summary>

### Añadido
* Wrappers `Request` y `Response` en `es.cesguiro.daw.framework.http`.
* `Request` abstrae la petición extrayendo la ruta limpia (sin `contextPath`), las cabeceras y los parámetros de consulta.
* `Response` incluye métodos factoría estáticos (`Response.ok(...)`, etc.) para gestionar el código HTTP, cabeceras y cuerpo de la respuesta.

### Modificado
* `UserController` devuelve objetos `Response` e independiza sus métodos de los objetos Servlet.
* `FrontController` coordina la conversión de `HttpServletRequest` a `Request`, la ejecución de rutas y el envío de `Response` como JSON.

</details>

<details>
<summary><b>v0.1.6-exception-handler</b></summary>

### Añadido
* Excepción base `CustomException` en `core.exception` con soporte para códigos de estado HTTP (`httpStatus`).
* Excepciones de dominio especializadas como `ResourceNotFoundException`.
* Manejador global `GlobalExceptionHandler` para interceptar cualquier `Throwable` y transformarlo en una respuesta JSON estandarizada (`{"status": 404, "error": "..."}`).

### Modificado
* `FrontController` simplifica su método `service()` utilizando un bloque `try-catch` global que delega todo el tratamiento de errores al `GlobalExceptionHandler`.

</details>

<details>
<summary><b>v0.1.5-jackson-json</b></summary>

### Añadido
* Dependencia `jackson-databind` (2.17.1) en `pom.xml` para soporte de serialización JSON.
* Formateo de respuestas de error (400, 404) en formato JSON estructurado.

### Modificado
* `FrontController` utiliza `ObjectMapper` para convertir POJOs a cadenas JSON.
* Cabeceras de respuesta HTTP cambiadas de `text/plain` a `application/json;charset=UTF-8`.

</details>

<details>
<summary><b>v0.1.4-user-controller</b></summary>

### Añadido
* Entidades de dominio `User` y `Role` con relaciones y formato de salida en texto (`toString()`).
* Controlador `UserController` con datos *hardcodeados* (`findAll` y `findById`).

### Modificado
* `FrontController` extiende su lógica para enrutar las peticiones `/api/users` y `/api/users/detail` invocando a `UserController`.

</details>

<details>
<summary><b>v0.1.3-front-controller</b></summary>

### Añadido
* Servlet centralizado `FrontController` en `core.web` que captura todas las peticiones a través del método `service()`.

### Modificado
* Configuración de `TomcatServer` para mapear el `FrontController` a la ruta `/*`.
* Incorporación del método `configureClasspath(Context)` en `TomcatServer` utilizando `StandardRoot` y `DirResourceSet` para que Tomcat resuelva dinámicamente el `ClassLoader` desde `target/classes`.

</details>

<details>
<summary><b>v0.1.2-properties</b></summary>

### Añadido
* Archivo de configuración `application.properties` en `src/main/resources`.
* Clase de utilidad `PropertyUtil` en el paquete `core.util` (o `core.config`) para la lectura dinámica de propiedades mediante `Properties` / `ClassLoader`.

### Modificado
* `TomcatServer` ahora lee el puerto de escucha dinámicamente desde `application.properties` en lugar de tenerlo hardcodeado.

</details>

<details>
<summary><b>v0.1.1-logging</b></summary>

### Añadido
* Dependencias de Logging (SLF4J + implementación como Logback) en `pom.xml`.
* Archivo de configuración de logs (`logback.xml`).

### Modificado
* Sustituidos todos los `System.out.println` y `e.printStackTrace()` de `TomcatServer` y `App` por instancias de `Logger` (`logger.info()`, `logger.error()`).

</details>
<details>
<summary><b>v0.1.0-embedded-tomcat</b></summary>

### Añadido
* Estructura base del proyecto Maven con Java 25+.
* Incorporación de la dependencia `tomcat-embed-core`.
* Infraestructura en `core.server.TomcatServer` para inicializar y arrancar Tomcat Embebido en el puerto 8080.
* Punto de entrada principal en `App.java`.
</details>

## 🔀 Cómo navegar entre versiones con Git

Para explorar el código en un punto específico de la evolución, puedes moverte entre los diferentes **Tags**:

### 1. Listar todas las versiones disponibles
```bash
git tag
```

### 2. Cambiar a una versión específica
Para examinar el código de un hito en concreto (por ejemplo, la versión base):

```bash
git checkout v0.1-embedded-tomcat
```

> **Nota:** Al hacer checkout a un tag entrarás en estado *detached HEAD*. Puedes revisar y ejecutar el código libremente.

### 3. Volver a la versión más reciente (desarrollo actual)
Para volver a la rama principal y seguir trabajando:
```bash
git checkout main
```
---

## 🚀 Cómo ejecutar la aplicación

Para compilar y arrancar el servidor web desde la terminal:

```bash
mvn clean compile exec:java
```
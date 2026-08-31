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
<summary><b>v0.1.3-front-controller</b> <i>(Versión actual)</i></summary>

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
# DAW Framework 🚀

Este proyecto es un **framework web Java incremental y didáctico**, construido desde cero sobre **Tomcat Embebido**.

El objetivo principal es entender *"qué hay debajo del capó"* de frameworks modernos como Spring Boot, implementando paso a paso los componentes esenciales de una arquitectura web.

---

## 🛠️ Requisitos previos

* **Java JDK 25+**
* **Apache Maven 3.8+**

---

## 📋 Hitos y Evolución del Proyecto (Tags)

El proyecto evoluciona de forma incremental mediante etiquetas de Git (**Tags**). Cada tag representa una fase funcional completa:

| Versión                | Descripción / Componentes introducidos |
|:-----------------------| :--- |
| `v0.1-embedded-tomcat` | Servidor Tomcat embebido básico con `FrontController` y sistema de logs con Logback. |
| `v0.2-property-util`   | Añadida la clase de utilidad `PropertyUtil` para la lectura de pares clave-valor desde `application.properties`. |
| `v0.3-data-infrastructure` | Infraestructura de BD con `AppServletContextListener`, pool HikariCP, migraciones con Flyway (MariaDB) y esquemas iniciales de `users` y `roles`. |
| `v0.3.1-app-context` | Introducción del contenedor de Beans IoC (`AppContext`) y refactorización de la infraestructura. |
| `v0.4.0-http-wrappers` | Introducción de los wrappers `Request` y `Response` para desacoplar los controladores de la API de Jakarta Servlets. |
| `v0.5.0-manual-router` | Registro manual de rutas explícito con `RouteKey`, `RouteHandler`, `Router` y `AppRoutes`. |
---

### 📌 Notas de Refactorización y Correcciones (v0.5.0)
* **Fix (TomcatServer):** Se corrigió la inicialización del `FrontController` en Tomcat Embebido. En lugar de pasar una instancia manual (`new FrontController()`), ahora se pasa el nombre de la clase (`FrontController.class.getName()`). Esto garantiza que Tomcat utilice el `WebappClassLoader` correcto para instanciar el servlet, resolviendo el problema de aislamiento de memoria donde el `AppContext` no compartía los beans (como el `Router`) registrados por el `AppServletContextListener`.

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
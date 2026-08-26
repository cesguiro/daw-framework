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
---

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

El servidor se iniciará en `http://localhost:8080`.
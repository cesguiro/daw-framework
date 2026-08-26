package es.cesguiro.daw.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static final Properties properties = new Properties();

    // Bloque estático: se ejecuta una sola vez al cargar la clase en memoria
    static {
        try (InputStream input = PropertyUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                logger.error("No se encontró el archivo 'application.properties' en el classpath");
            } else {
                properties.load(input);
                logger.info("Propiedades cargadas correctamente desde 'application.properties'");
            }
        } catch (IOException ex) {
            logger.error("Error al leer 'application.properties'", ex);
        }
    }

    // Constructor privado para evitar instanciación (clase de utilidad)
    private PropertyUtil() {
        throw new AssertionError("No se puede instanciar esta clase");
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warn("Propiedad '{}' con valor '{}' no es un entero. Se usará el valor por defecto: {}", key, value, defaultValue);
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        String trimmed = value.trim();
        if (trimmed.equalsIgnoreCase("true")) {
            return true;
        }
        if (trimmed.equalsIgnoreCase("false")) {
            return false;
        }
        logger.warn("Propiedad '{}' con valor '{}' no es un booleano válido. Se usará el valor por defecto: {}", key, value, defaultValue);
        return defaultValue;
    }
}
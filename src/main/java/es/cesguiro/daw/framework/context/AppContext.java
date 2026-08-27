package es.cesguiro.daw.framework.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppContext {

    private final Map<Class<?>, Object> beans = new ConcurrentHashMap<>();
    private static final AppContext INSTANCE = new AppContext();

    private AppContext() {
    }

    public static AppContext getInstance() {
        return INSTANCE;
    }

    public <T> void register(Class<T> type, T instance) {
        if (type == null || instance == null) {
            throw new IllegalArgumentException("El tipo y la instancia no pueden ser nulos.");
        }
        beans.put(type, instance);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        T instance = (T) beans.get(type);
        if (instance == null) {
            throw new IllegalArgumentException("No se econtró ningún Bean del tipo:" + type.getName());
        }
        return instance;
    }
}

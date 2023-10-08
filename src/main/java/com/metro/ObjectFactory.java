package com.metro;

import com.metro.announcer.Announcer;
import com.metro.announcer.ConsoleAnnouncer;
import com.metro.configuration.Config;
import com.metro.configuration.JavaConfig;
import com.metro.objectconfigurator.ObjectConfigurator;
import lombok.SneakyThrows;

import java.util.*;

public class ObjectFactory {
    private static final ObjectFactory objectFactory = new ObjectFactory();
    private final Config config;
    private final List<ObjectConfigurator> configurators = new ArrayList<>();

    @SneakyThrows
    private ObjectFactory() {
        config = new JavaConfig("com.metro", Map.of(
                Announcer.class, ConsoleAnnouncer.class
        ));
        for (Class<? extends ObjectConfigurator> cls : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(cls.getDeclaredConstructor().newInstance());
        }
    }

    public static ObjectFactory getInstance() {
        return objectFactory;
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        Class<? extends T> impl = type;
        if (type.isInterface()) {
            impl = config.getImpl(type);
        }
        T obj = impl.getDeclaredConstructor().newInstance();
        configurators.forEach(configurator -> configurator.configure(obj));
        return obj;
    }
}

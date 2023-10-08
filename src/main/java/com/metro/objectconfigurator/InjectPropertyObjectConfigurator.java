package com.metro.objectconfigurator;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;

public class InjectPropertyObjectConfigurator implements ObjectConfigurator {
    Map<String, String> propertyMap;

    @SneakyThrows
    public InjectPropertyObjectConfigurator() {
        String path = ClassLoader.getSystemClassLoader().getResource("fields.properties").getPath();
        this.propertyMap = new BufferedReader(new FileReader(path)).lines()
                .map(line -> line.split("="))
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    @SneakyThrows
    @Override
    public void configure(Object obj) {
        Class<?> cls = obj.getClass();
        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectProperty.class)) {
                String annotationValue = field.getAnnotation(InjectProperty.class).value();
                String value = propertyMap.get(!annotationValue.isEmpty() ? annotationValue : field.getName());
                field.setAccessible(true);
                field.set(obj, value);
            }
        }
    }
}

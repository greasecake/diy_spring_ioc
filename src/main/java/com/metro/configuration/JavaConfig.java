package com.metro.configuration;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JavaConfig implements Config {
    @Getter
    private final Reflections scanner;
    Map<Class, Class> implMap;

    public JavaConfig(String packagesToScan, Map<Class, Class> implMap) {
        this.scanner = new Reflections(packagesToScan);
        this.implMap = new HashMap<>(implMap);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> getImpl(Class<T> type) {
        return implMap.computeIfAbsent(type, cls -> {
            Set<Class<? extends T>> impls = scanner.getSubTypesOf(type);
            if (impls.size() != 1) {
                throw new RuntimeException(String.format(
                        "For non-configured interfaces only single implementation is allowed! Type: %s, Impls: %s",
                        type.getName(),
                        impls.size() == 0 ? null: impls.stream().map(Class::getName).collect(Collectors.joining(", "))));
            }
            return impls.iterator().next();
        });
    }
}

package com.metro.configuration;

public interface Config {
    @SuppressWarnings("unchecked")
    <T> Class<? extends T> getImpl(Class<T> type);

    org.reflections.Reflections getScanner();
}

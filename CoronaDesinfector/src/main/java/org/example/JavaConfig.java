package org.example;

public class JavaConfig implements Config {
    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) {
        return null;
    }
}

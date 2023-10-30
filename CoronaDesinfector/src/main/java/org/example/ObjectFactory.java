package org.example;

public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();

    private Config config;
    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    private ObjectFactory() {
    }

    public <T> T createObject(Class<T> type){
        Class<? extends T> implClass = type;
        if (type.isInterface()){
            implClass = config.getImplClass(type);
        }
    }
}

package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ObjectFactory {
    private static ObjectFactory ourInstance = new ObjectFactory();

    private Config config;
    public static ObjectFactory getInstance() {
        return ourInstance;
    }

    private ObjectFactory() {
        config = new JavaConfig("org.example", new HashMap<>(Map.of(Policeman.class, AngryPoliceman.class)));
    }

    public <T> T createObject(Class<T> type){
        Class<? extends T> implClass = type;
        // obtinem implementarea acestei clase
        if (type.isInterface()){
            implClass = config.getImplClass(type);
        }
        // cream o instanta a clasei dorite
        T t;
        try {
            t = implClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        for (Field declaredField : implClass.getDeclaredFields()) {
            InjectProperty annotation = declaredField.getAnnotation(InjectProperty.class);

            String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
            Stream<String> lines;
            try {
                lines = new BufferedReader(new FileReader(path)).lines();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            // citim toate proprietatile
            Map<String, String> propertiesMap =
                    lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));

            if (annotation != null){
                String value;
                if (annotation.value().isEmpty()){
                    value = propertiesMap.get(declaredField.getName());
                }else{
                    value = propertiesMap.get(annotation.value());
                }
                declaredField.setAccessible(true);
                try {
                    declaredField.set(t,value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return t;
    }
}

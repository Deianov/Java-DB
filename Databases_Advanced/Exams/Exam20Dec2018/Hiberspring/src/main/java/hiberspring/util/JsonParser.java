package hiberspring.util;

import java.io.FileNotFoundException;

public interface JsonParser {
    <T> T objectFromFile(String path, Class<T> tClass) throws FileNotFoundException;

    <T> void objectToFile(T obj, String path) throws FileNotFoundException;

    <T> T fromJson(String jsonString, Class<T> tClass);

    <T> String toJson(T obj);
}

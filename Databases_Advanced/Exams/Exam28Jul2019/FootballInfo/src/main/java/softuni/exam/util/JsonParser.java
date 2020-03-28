package softuni.exam.util;

import java.io.IOException;

public interface JsonParser {
    <T> T objectFromFile(String path, Class<T> tClass) throws IOException;

    <T> void objectToFile(T obj, String path) throws IOException;

    <T> T fromJson(String jsonString, Class<T> tClass);

    <T> String toJson(T obj);
}

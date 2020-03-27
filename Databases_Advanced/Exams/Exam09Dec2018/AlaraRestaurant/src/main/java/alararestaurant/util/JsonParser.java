package alararestaurant.util;

public interface JsonParser {
    <T> T objectFromFile(String path, Class<T> tClass);

    <T> void objectToFile(T obj, String path);

    <T> T fromJson(String jsonString, Class<T> tClass);

    <T> String toJson(T obj);
}

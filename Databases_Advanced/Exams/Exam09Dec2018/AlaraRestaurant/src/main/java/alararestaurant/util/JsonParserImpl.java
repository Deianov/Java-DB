package alararestaurant.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

@Component
public class JsonParserImpl implements JsonParser {
    private static final Charset charset = StandardCharsets.UTF_8;
    private final Gson gson;

    @Autowired
    public JsonParserImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> T objectFromFile(String path, Class<T> tClass) {
        try {
            return this.fromJson(Files.readString(Path.of(path), charset), tClass);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> void objectToFile(T obj, String path)  {
        try {
            Files.write(Path.of(path), Collections.singleton(toJson(obj)), charset);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T fromJson(String jsonString, Class<T> tClass) {
        return this.gson.fromJson(jsonString, tClass);
    }

    @Override
    public <T> String toJson(T obj) {
        return this.gson.toJson(obj);
    }
}

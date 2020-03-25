package softuni.exam.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

@Component
public class JsonParserImpl implements JsonParser {

    private final Gson gson;
    private static final Charset charset = StandardCharsets.UTF_8;

    public JsonParserImpl() {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public <T> T objectFromFile(String path, Class<T> tClass) throws IOException {
        return this.fromJson(Files.readString(Path.of(path), charset), tClass);
    }

    @Override
    public <T> void objectToFile(T obj, String path) throws IOException {
        Files.write(Path.of(path), Collections.singleton(toJson(obj)), charset);
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

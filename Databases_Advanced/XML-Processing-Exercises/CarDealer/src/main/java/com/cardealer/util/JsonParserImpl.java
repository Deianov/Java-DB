package com.cardealer.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonParserImpl implements JsonParser {

    private final Gson gson;
    private final FileUtil fileUtil;

    @Autowired
    public JsonParserImpl(Gson gson, FileUtil fileUtil) {
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public <T> T objectFromFile(String path, Class<T> tClass) throws IOException {
        return this.fromJson(fileUtil.read(path), tClass);
    }

    @Override
    public <T> void objectToFile(T obj, String path) throws IOException {
        fileUtil.write(toJson(obj), path);
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

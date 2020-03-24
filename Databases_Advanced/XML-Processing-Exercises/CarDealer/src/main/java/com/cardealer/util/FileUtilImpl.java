package com.cardealer.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class FileUtilImpl implements FileUtil {

    @Override
    public String read(String filePath) throws IOException {
        String result = Files
                .readAllLines(Paths.get(filePath))
                .stream()
                .filter(x -> !x.isEmpty())
                .collect(Collectors.joining(System.lineSeparator()));
        return result;
    }

    @Override
    public void write(String content, String filePath) throws IOException {
        Path path = Paths.get(filePath);
//        File file = path.toFile();
//
//        if (!file.exists()){
//            boolean mkdirs = file.getParentFile().mkdirs();
//            boolean newFile = file.createNewFile();
//        }

        Files.write(path, Collections.singleton(content), StandardCharsets.UTF_8);
    }
}

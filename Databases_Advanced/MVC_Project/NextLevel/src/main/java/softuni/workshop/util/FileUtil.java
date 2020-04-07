package softuni.workshop.util;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public final class FileUtil {
    private static final Charset charset = StandardCharsets.UTF_8;

    public static String read(String filePath) {
        Path path = Paths.get(filePath);
        String result = null;
        try {
            result = Files.readString(path, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void write(String content, String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.write(path, Collections.singleton(content), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package softuni.exam.util;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public final class FileUtil {
    private static final Charset charset = StandardCharsets.UTF_8;

    public static String read(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String result = Files.readString(path, charset);
        return result;
    }

    public static void write(String content, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, Collections.singleton(content), charset);
    }
}

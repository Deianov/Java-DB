package alararestaurant.util;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtilImpl implements FileUtil{
    private static final Charset charset = StandardCharsets.UTF_8;

    @Override
    public String readFile(String filePath) {
        Path path = Paths.get(filePath);
        String result = null;
        try {
            result = Files.readString(path, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

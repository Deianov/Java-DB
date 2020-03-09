package com.springdata.exercises.utils;

import java.io.IOException;
import java.util.List;

public interface FileUtil {
    List<String> readFileContent(String filePath) throws IOException;
}

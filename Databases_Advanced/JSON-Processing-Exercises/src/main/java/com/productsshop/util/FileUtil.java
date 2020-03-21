package com.productsshop.util;

import java.io.IOException;

public interface FileUtil {
    String read(String filePath) throws IOException;

    void write(String content, String filePath) throws IOException;
}

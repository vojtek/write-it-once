package org.simart.writeonce.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtils {

    public static String package2Path(Class<?> cls) {
        return cls.getPackage().getName().replace('.', File.separatorChar);
    }

    public static String package2Path(Class<?> cls, char separator) {
        return cls.getPackage().getName().replace('.', separator);
    }

    public static void write(String filePath, String content) throws IOException {
        final Path path = Paths.get(filePath);
        final Path parentDir = path.getParent();
        if (!Files.exists(parentDir))
            Files.createDirectories(parentDir);
        Files.write(path, content.getBytes());
    }

    public static String read(final String path) throws IOException {
        return read(path, Charset.forName("UTF-8"));
    }

    public static String read(final String path, final Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private FileUtils() {
    }
}

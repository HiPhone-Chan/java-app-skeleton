package tech.hiphone.test.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;

import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

public class FileDataUtil {

    public static String read(String fileName) {
        URL url = FileDataUtil.class.getClassLoader().getResource(fileName);
        try {
            String path = url.getPath();
            path = URLDecoder.decode(path, "utf-8");
            return FileUtils.readFileToString(new File(path), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream readAsStream(String fileName) {
        return FileDataUtil.class.getClassLoader().getResourceAsStream(fileName);
    }

}

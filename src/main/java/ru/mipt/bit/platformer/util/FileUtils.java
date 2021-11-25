package ru.mipt.bit.platformer.util;

import java.net.URL;

public class FileUtils {
    public static String readFileFromResources(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL resourceUrl = classloader.getResource("level.txt");
        return resourceUrl.getFile();
    }
}

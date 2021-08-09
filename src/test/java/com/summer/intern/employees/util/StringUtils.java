package com.summer.intern.employees.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StringUtils {

    public static String read(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file))).replaceAll(" ","").replaceAll("\n","");
    }
}

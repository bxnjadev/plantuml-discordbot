package net.ibxnjadev.plantuml.util;

import java.io.*;

public class PlantumlFiles {

    private PlantumlFiles() {
    }

    public static boolean createFileIfNotExists(String path,
                                         String content) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return false;
        }

        boolean response = file.createNewFile();
        if(!content.isEmpty()){
            try (FileWriter writer = new FileWriter(path)) {
                writer.write(content);
            }
        }

        return response;
    }

    public static boolean createFileIfNotExists(String path) throws IOException {
        return createFileIfNotExists(path, "");
    }

}

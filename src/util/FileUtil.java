package util;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void writeFile(String filename, String content) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(content);
            fileWriter.close();
            System.out.println("File generated: " + filename + ".");
        } catch (IOException e) {
            System.err.println("Cannot write calendar! " + e.getMessage());
        }
    }
}

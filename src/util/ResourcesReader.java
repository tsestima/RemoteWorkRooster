package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class ResourcesReader {

    public static Hashtable<String, Integer> readResourcesFromCSV(final String file) {

        Hashtable<String, Integer> resources = new Hashtable<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            br.readLine(); // Ignore header

            while ((line = br.readLine()) != null) {

                if (!line.startsWith("#") && !line.trim().isEmpty()) {
                    String[] splits = line.split(",");
                    resources.put(splits[0], Integer.parseInt(splits[1]));
                }

            }

            br.close();
        } catch (IOException e) {
            System.err.println("Cannot read resources file " + file + "! Abort!");
            System.exit(-1);
        }

        return resources;
    }

}

import engines.AllocationGenerator;
import util.ResourcesReader;

import java.util.Hashtable;

public class GenerateAllocation {

    public static void main(final String[] args) {

        int year = 0;
        int month = 0;
        String outputFilename = "";

        try {
            outputFilename = args[0];
            year = Integer.parseInt(args[1]);
            month = Integer.parseInt(args[2]);
        } catch (Exception e) {
            System.err.println("Usage: java GenerateAllocation <output csv filename> <year> <month>");
            System.exit(1);
        }

        Hashtable<String, Integer> resources = ResourcesReader.readResourcesFromCSV("conf/resources.csv");

        AllocationGenerator generator = new AllocationGenerator();
        generator.generateCalendarAllocation(outputFilename, year, month, resources);

    }
}

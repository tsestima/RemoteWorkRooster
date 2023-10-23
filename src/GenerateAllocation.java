import engines.AllocationGenerator;
import util.ResourcesReader;

import java.util.Hashtable;

public class GenerateAllocation {

    public static void main(final String[] args) {

        int year = 0;
        int month = 0;
        String outputFilename = "";

        try {
            year = Integer.parseInt(args[0]);
            month = Integer.parseInt(args[1]);
            outputFilename = args[2];
        } catch (Exception e) {
            System.err.println("Usage: java " + GenerateAllocation.class.getName() + " <year> <month> <output csv filename>");
            System.exit(1);
        }

        Hashtable<String, Integer> resources = ResourcesReader.readResourcesFromCSV("conf/resources.csv");

        AllocationGenerator generator = new AllocationGenerator();
        generator.generateCalendarAllocation(outputFilename, year, month, resources);

    }
}

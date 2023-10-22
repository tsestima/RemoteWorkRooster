import calendar.AllocationGenerator;

import java.util.Hashtable;

public class GenerateCsv {

    public static void main(String[] args) {

        int year = 2023;
        int month = 11;

        String[] resources = new String[] {"MD", "OA", "TE"};

        Hashtable<String, Integer> initialShift = new Hashtable<>();
        initialShift.put("MD", 0);
        initialShift.put("OA", 1);
        initialShift.put("TE", 2);

        AllocationGenerator generator = new AllocationGenerator();
        generator.generateCalendarAllocation("out.csv", year, month, resources, initialShift);

    }
}

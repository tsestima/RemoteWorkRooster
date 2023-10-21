import model.MonthMap;
import model.Shift;
import model.WeekResourceAllocation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;

public class MonthAllocationGenerator {

    private final Hashtable<Integer, ArrayList<String>> dayToResources;

    Shift shift = new Shift();

    public MonthAllocationGenerator() {
        dayToResources = new Hashtable<>();
    }

    public void generateCalendarAllocation(final MonthMap monthMap, int year, int month) {


        Hashtable<Integer, WeekResourceAllocation> map = monthMap.getMonthMap();

        LocalDate date = LocalDate.of(year, month, 1);
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        while (date.getMonthValue() == month) {
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            int weekNumber = date.get(weekFields.weekOfYear());

            WeekResourceAllocation allocation = map.get(weekNumber);

            if (allocation == null) {
                System.out.println("Error: No allocation for week " + weekNumber);
                System.exit(1);
            }

            Set<String> resources = allocation.getResources();

            for (String resource: resources) {
                 Integer shiftNbr = allocation.get(resource);
                 String[] weekdays = shift.getShift(shiftNbr);

                 for (String weekDay: weekdays) {
                     if (weekDay.equals(dayOfWeek)) {
                         ArrayList<String> rs  = dayToResources.get(date.getDayOfMonth());

                         if (rs == null) {
                             rs = new ArrayList<>();
                         }

                         rs.add(resource);
                         dayToResources.put(date.getDayOfMonth(), rs);
                     }
                 }
            }

            date = date.plusDays(1);
        }
    }

    private String convertToString(ArrayList<String> resources) {

        StringBuilder sb = new StringBuilder();
        for (String resource : resources) {
            sb.append(resource);
            sb.append(" ");
        }

        return sb.toString();
    }

    private List<Integer> getSortedListOfKeys() {
        Set<Integer> keys = dayToResources.keySet();
        ArrayList<Integer> sorted = new ArrayList<>(keys);
        Collections.sort(sorted);
        return sorted;
    }

    public void dump() {

        List<Integer> days = getSortedListOfKeys();
        for(Integer dayOfMonth: days){
            System.out.println(dayOfMonth + " " + convertToString(dayToResources.get(dayOfMonth)));
        }
    }

    public void dumpCsv(String filename) {

        List<Integer> days = getSortedListOfKeys();

        StringBuilder txt = new StringBuilder();

        for (Integer dayOfTheMonth : days) {
            txt.append("\n");
            txt.append(dayOfTheMonth);
            txt.append(",");
            txt.append(convertToString(dayToResources.get(dayOfTheMonth)));
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(txt.toString());
            writer.close();
        } catch (Exception e) {
            System.err.println("Cannot write to file " + filename + ".");
        }
    }


    private static final String USAGE = "java "
            + MonthAllocationGenerator.class.getName() + " <year> <month> <resources list>";

    public static void main(String[] args) {

        int year;
        int month;

        try {
            year = Integer.parseInt(args[0]);
            month = Integer.parseInt(args[1]);



        } catch (Exception e) {
            System.err.println(USAGE);
            System.exit(-1);
        }

    }

}

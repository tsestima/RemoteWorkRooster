import model.MonthMap;
import model.Shift;
import model.WeekResourceAllocation;

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

    public void generateCalendarAllocation(final MonthMap monthMap) {

        int year = monthMap.getYear();
        int month = monthMap.getMonth();

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
                         //System.out.println(date.getDayOfMonth() + " " + dayOfWeek + " " + resource);

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

    public void dump() {
        Enumeration<Integer> e = dayToResources.keys();

        while (e.hasMoreElements()) {
            int dayOfMonth = e.nextElement();
            System.out.println(dayOfMonth + " " + Arrays.toString(dayToResources.get(dayOfMonth).toArray()));
        }

    }

    public static void main(String[] args) {

        MonthMap monthMap = new MonthMap(2023, 10);
        String[] resources = new String[]{"MD", "OA", "TE"};

        Hashtable<String, Integer> initialShift = new Hashtable<>();
        initialShift.put("MD", 0);
        initialShift.put("OA", 1);
        initialShift.put("TE", 2);

        monthMap.calculateAllocations(resources, new Shift(), initialShift);
        monthMap.dump();

        System.out.println("\n\n");
        MonthAllocationGenerator generator = new MonthAllocationGenerator();
        generator.generateCalendarAllocation(monthMap);
        generator.dump();
    }
}

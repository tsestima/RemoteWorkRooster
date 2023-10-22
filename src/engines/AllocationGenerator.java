package engines;

import model.MonthMap;
import model.Shift;
import model.WeekResourceAllocation;
import util.CalendarUtil;
import util.FileUtil;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;

public class AllocationGenerator {

    private final Hashtable<Integer, ArrayList<String>> dayToResources;

    private final Shift shift;

    public AllocationGenerator() {
        dayToResources = new Hashtable<>();
        shift = new Shift();
    }

    public void generateCalendarAllocation(final String filename,
                                           final int year,
                                           final int month,
                                           final Hashtable<String, Integer> resourcesShifts) {


        ArrayList<Integer> weekNumbers = CalendarUtil.getWeekNumbers(year, month);

        MonthMap monthMap = new MonthMap();
        monthMap.calculateAllocations(new Shift(), weekNumbers, resourcesShifts);

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

        dumpCsv(filename, year, month);
    }

    private String convertToString(final ArrayList<String> resources) {

        Collections.sort(resources);
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
        for(Integer dayOfMonth: days) {
            System.out.println(dayOfMonth + " " + convertToString(dayToResources.get(dayOfMonth)));
        }
    }

    public void dumpCsv(final String filename, int year, int month) {

        List<Integer> days = getSortedListOfKeys();
        StringBuilder txt = new StringBuilder();

        txt.append("# Calendar for: " + year + "/" + month + "\n");
        txt.append("# Day of month, Resources\n");

        for (Integer dayOfTheMonth : days) {
            txt.append(dayOfTheMonth);
            txt.append(",");
            txt.append(convertToString(dayToResources.get(dayOfTheMonth)));
            txt.append("\n");
        }

        FileUtil.writeFile(filename, txt.toString());
    }
}

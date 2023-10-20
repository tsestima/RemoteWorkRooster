package model;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;


public class MonthMap {

    private int year;

    private int month;

    private final ArrayList<Integer> weekNumbers;

    private Hashtable<Integer, WeekResourceAllocation> monthMap;

    public MonthMap(int year, int month) {
        this.year = year;
        this.month = month;
        monthMap = new Hashtable<>();
        weekNumbers = getWeekNumbers(year, month);
    }

    public void calculateAllocations(final String[] resources, final Shift shifts, Hashtable<String, Integer> resShif) {

        for (Integer w : weekNumbers) {

            WeekResourceAllocation weekToShift = new WeekResourceAllocation(w);

            for (String r : resources) {
                Integer s = resShif.get(r);

                weekToShift.put(r, s);
                monthMap.put(w , weekToShift);
                s++;

                if (s >= shifts.size()) {
                    s = 0;
                }
                resShif.put(r, s);
            }
        }
    }

    private ArrayList<Integer> getWeekNumbers(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        ArrayList<Integer> weeks = new ArrayList<>();

        // Loop through the days of the month
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        while (date.getMonthValue() == month) {
            String dayOfMonth = String.valueOf(date.getDayOfMonth());
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            int weekNumber = date.get(weekFields.weekOfYear());
            if (!weeks.contains(weekNumber))
                weeks.add(weekNumber);

            // Move to the next day
            date = date.plusDays(1);
        }

        return weeks;
    }

    public Hashtable<Integer, WeekResourceAllocation> getMonthMap() {
        return monthMap;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void dump() {

        System.out.println("Dump allocation for " + year + " month " + month);
        Hashtable<Integer, WeekResourceAllocation> mm = getMonthMap();

        Enumeration<Integer> en = mm.keys();

        while (en.hasMoreElements()) {
            Integer week = en.nextElement();
            WeekResourceAllocation wra = mm.get(week);
            Hashtable<String, Integer> rtos = wra.getResourceToShift();

            Enumeration<String> res = rtos.keys();
            while(res.hasMoreElements()) {
                String r = res.nextElement();
                System.out.println("Week " + week + " " + r + " " + rtos.get(r));
            }
        }
    }

}

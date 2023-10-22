package model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

public class MonthMap {

    /** Week number to allocation.*/
    private final Hashtable<Integer, WeekResourceAllocation> monthMap;

    public MonthMap() {
        monthMap = new Hashtable<>();
    }

    /**
     * Calculate allocations for given week numbers.
     *
     * @param shifts the available shifts.
     * @param weekNumbers The week numbers to generate.
     * @param resShif The initial week allocation.
     */
    public void calculateAllocations(final Shift shifts,
                                     final ArrayList<Integer> weekNumbers,
                                     final Hashtable<String, Integer> resShif) {


        Set<String> resourcesList = resShif.keySet();

        for (Integer w : weekNumbers) {
            WeekResourceAllocation weekToShift = new WeekResourceAllocation(w);

            for (String r : resourcesList) {
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

    public Hashtable<Integer, WeekResourceAllocation> getMonthMap() {
        return monthMap;
    }

    public void dump() {

        System.out.println("Dump allocation");
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

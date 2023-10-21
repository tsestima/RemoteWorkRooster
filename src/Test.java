import model.MonthMap;
import model.Shift;
import util.CalendarUtil;

import java.util.ArrayList;
import java.util.Hashtable;

public class Test {

    public static void main(String[] args) {

        int year = 2023;
        int month = 10;

        MonthMap monthMap = new MonthMap();
        ArrayList<Integer> weekNumbers = CalendarUtil.getWeekNumbers(year, month);

        String[] resources = new String[] {"MD", "OA", "TE"};

        Hashtable<String, Integer> initialShift = new Hashtable<>();
        initialShift.put("MD", 0);
        initialShift.put("OA", 1);
        initialShift.put("TE", 2);

        monthMap.calculateAllocations(resources, new Shift(), weekNumbers, initialShift);
        //monthMap.dump();

        MonthAllocationGenerator generator = new MonthAllocationGenerator();
        generator.generateCalendarAllocation(monthMap, year, month);
        //generator.dump();

        generator.dumpCsv("out.csv");
    }
}

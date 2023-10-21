package util;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Utility class.
 */
public class CalendarUtil {

    public static ArrayList<Integer> getWeekNumbers(int year, int month) {
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
}

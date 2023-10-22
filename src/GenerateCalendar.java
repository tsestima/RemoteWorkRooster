import calendar.CalendarGenerator;

import java.util.List;

public class GenerateCalendar {

    public static void main(String[] args) {
        int year = 2023; // Replace with the desired year
        int month = 11; // Replace with the desired month (1-12)

        CalendarGenerator.generateCalendarHTML("out.csv","Calendário Presencial ASD: ", year, month);
    }
}

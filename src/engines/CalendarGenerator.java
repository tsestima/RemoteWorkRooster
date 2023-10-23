package engines;

import util.FileUtil;
import util.HolidaysReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

public class CalendarGenerator {

    public static final int DAYS_IN_THE_WEEK = 7;
    public static final int ROWS = 6;
    private List<String> events;

    private int year;

    private int month;

    private final Hashtable<LocalDate, String> holidays;

    public CalendarGenerator() {
        holidays = HolidaysReader.readHolidaysFile("resources/holidays.csv");
    }

    public void readAllocationFromCsv(final String file) {

        events = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            // Parse first line with the date
            String firstLine = br.readLine();
            String date = firstLine.split(":")[1];
            String[] dateSplit = date.split("/");

            year = Integer.parseInt(dateSplit[0].trim());
            month = Integer.parseInt(dateSplit[1].trim());

            br.readLine(); // Ignore header

            while ((line = br.readLine()) != null) {
                events.add(line);
            }

            br.close();

        } catch (IOException e) {
            System.err.println("Cannot read " + file + "! Abort!");
            System.exit(-1);
        }
    }

    private String getHtmlHeader() {
        return "<html>\n<head>\n" +
                "\t<link rel='stylesheet' type='text/css' href='resources/calendar.css'>\n" +
                "</head>\n";
    }

    public void generateCalendarHTML(final String csvFilename,
                                     final String title,
                                     final String htmlFilename) {

        readAllocationFromCsv(csvFilename);

        StringBuilder html = new StringBuilder(getHtmlHeader());
        String titleString = title + " - " + year + "/" + String.format("%02d", month)
                ;

        html.append("<body><br><h2>");
        html.append(titleString);
        html.append("</h2>\n<br>");
        html.append("<table class='calendar'>\n<tr>\n");
        html.append("<th>Sun</th>\n<th>Mon</th>\n<th>Tue</th>\n");
        html.append("<th>Wed</th>\n<th>Thu</th>\n<th>Fri</th>\n<th>Sat</th>\n</tr>\n");

        // Calculate the first day of the month
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);  // Neet to start in the previous month (week can start before)

        int firstDayOfMonth = cal.get(Calendar.DAY_OF_WEEK);
        int numDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        int day = 1;

        for (int row = 1; row <= ROWS; row++) {

            if (row > 1 && day > numDaysInMonth) {
                break; // Exit the loop if all days have been processed
            }

            html.append("<tr>\n");

            for (int col = 1; col <= DAYS_IN_THE_WEEK; col++) {

                if (row == 1 && col < firstDayOfMonth) {

                    html.append("<td></td>\n");

                } else if (day <= numDaysInMonth) {

                    StringBuilder cellContent = new StringBuilder(day + "<br>");
                    String holiday = holidays.get(LocalDate.of(year, month, day));

                    if (holiday != null) {
                        cellContent.append("<spam style=\"font-size:10;color:#990033;\">");
                        cellContent.append(holiday + "</spam><br><br>");
                    }

                    for (String event : events) {

                        String[] eventData = event.split(",");

                        if (!eventData[0].isEmpty() && Integer.parseInt(eventData[0]) == day) {
                            cellContent.append(eventData[1]).append("<br>");
                        }
                    }

                    html.append("<td>").append(cellContent).append("</td>\n");
                    day++;

                } else {
                    html.append("<td></td>\n");
                }
            }

            html.append("</tr>\n");
        }

        html.append("</table>\n</body>\n</html>");
        FileUtil.writeFile(htmlFilename, html.toString());
    }
}
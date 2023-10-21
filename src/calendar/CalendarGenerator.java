package calendar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalendarGenerator {


    public static List<String> readEventsFromCSV(String filePath) {
        List<String> events = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                events.add(line);
                System.out.println("ADding " + line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static void generateCalendarHTML(int year, int month,  List<String> events) {
        StringBuilder html = new StringBuilder();
        html.append("<html>\n<head>\n");
        html.append("\t<link rel='stylesheet' type='text/css' href='resources/calendar.css'>\n");
        html.append("</head>\n");
        html.append("<body><h2>Calendar for " + month + "/" + year + "</h2>\n");
        html.append("<table class='calendar'>\n<tr>\n");
        html.append("<th>Sun</th>\n<th>Mon</th>\n<th>Tue</th>\n");
        html.append("<th>Wed</th>\n<th>Thu</th>\n<th>Fri</th>\n<th>Sat</th>\n</tr>\n");

        // Calculate the first day of the month
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month - 1, 1);
        int firstDayOfMonth = cal.get(java.util.Calendar.DAY_OF_WEEK);

        // Get the number of days in the month
        int numDaysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

        int day = 1;
        for (int row = 1; row <= 6; row++) {

            if (row > 1 && day > numDaysInMonth) {
                break; // Exit the loop if all days have been processed
            }

            html.append("<tr>\n");
            for (int col = 1; col <= 7; col++) {

                if (row == 1 && col < firstDayOfMonth) {
                    html.append("<td></td>\n");
                } else if (day <= numDaysInMonth) {
                    String cellContent = day + "<br>";

                    for (String event : events) {
                        String[] eventData = event.split(",");

                        System.out.println("-" + eventData[0] + "-");
                        if (!eventData[0].equals("") && Integer.parseInt(eventData[0]) == day) {
                            cellContent += eventData[1] + "<br>";
                        }
                    }

                    html.append("<td>" + cellContent + "</td>\n");
                    day++;
                } else {
                    html.append("<td></td>\n");
                }
            }
            html.append("</tr>\n");
        }

        html.append("</table>\n</body>\n</html>");

        // Write the HTML to a file
        try {
            FileWriter fileWriter = new FileWriter("calendar.html");
            fileWriter.write(html.toString());
            fileWriter.close();
            System.out.println("Calendar HTML generated and saved as 'calendar.html'");
        } catch (IOException e) {
            System.err.println("Cannot write calendar! " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int year = 2023; // Replace with the desired year
        int month = 10; // Replace with the desired month (1-12)

        List<String> events = readEventsFromCSV("out.csv");

        // Sample
        generateCalendarHTML(year, month, events);
    }
}
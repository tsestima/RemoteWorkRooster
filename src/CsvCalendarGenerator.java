import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class CsvCalendarGenerator {

    private boolean isWeekDay(LocalDate date) {
        return (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY);
    }

    public void generate(final int year, final int month, final String[] resources) {

        // Define the output CSV file path
        String csvFilePath = "calendar.csv";

        try (FileWriter csvWriter = new FileWriter(csvFilePath)) {
            // Write the CSV header
            csvWriter.append("Day of the Month,Day of the Week\n");

            // Create a LocalDate for the first day of the specified month and year
            LocalDate date = LocalDate.of(year, month, 1);

            // Loop through the days of the month
            while (date.getMonthValue() == month) {
                String dayOfMonth = String.valueOf(date.getDayOfMonth());
                String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

                // Write the day of the month and day of the week to the CSV
                csvWriter.append(dayOfMonth).append(",").append(dayOfWeek);

                if (isWeekDay(date)) {
                    csvWriter.append(",").append("TE");
                }

                csvWriter.append("\n");

                // Move to the next day
                date = date.plusDays(1);
            }

            System.out.println("CSV calendar created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CsvCalendarGenerator generator =  new CsvCalendarGenerator();
        generator.generate(2023, 10, new String[] {"MD", "OA", "TE"});
    }
}

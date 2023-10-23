package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Hashtable;

public class HolidaysReader {

    public static Hashtable<LocalDate, String> readHolidaysFile(String filename) {

        Hashtable<LocalDate, String> holidays = new Hashtable<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            String line;
            br.readLine(); // Ignore header

            while ((line = br.readLine()) != null) {

                if (!line.startsWith("#") && !line.trim().isEmpty()) {
                    String[] splits = line.split(",");

                    String dateS = splits[0];
                    String event = splits[1];

                    String[] dateSplits = dateS.split("-");

                    LocalDate date = LocalDate.of(Integer.parseInt(dateSplits[0]),
                            Integer.parseInt(dateSplits[1]),
                            Integer.parseInt(dateSplits[2]));

                    holidays.put(date, event);
                }
            }

            br.close();

        } catch (IOException e) {
            System.err.println("Cannot read holidays file " + filename + "! Abort!");
            System.exit(-1);
        }

        return holidays;
    }
}

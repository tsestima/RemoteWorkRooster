import engines.CalendarGenerator;

public class GenerateCalendar {

    public static void main(final String[] args) {

        String inputCsv = "";
        String outputhtml = "";

        try {
            inputCsv = args[0];
            outputhtml = args[1];
        } catch (Exception e) {
            System.err.println("Usage java " + GenerateCalendar.class.getName() + " <input csv filename> <output html filename>");
            System.exit(-1);
        }

        CalendarGenerator generator = new CalendarGenerator();
        generator.generateCalendarHTML(inputCsv,"Calendário Presencial equipa ASD", outputhtml);
    }
}

import java.util.Hashtable;

public class Shift {

    private final Hashtable<Integer, String[]> shifts;

    public Shift() {
        shifts = new Hashtable<>();
        shifts.put(1, new String[] {"Monday", "Tuesday", "Wednesday"});
        shifts.put(2, new String[] {"Tuesday", "Wednesday", "Thursday"});
        shifts.put(3, new String[] {"Wednesday", "Thursday", "Friday"});
    }

    public String[] getShift(Integer i) {
        return shifts.get(i);
    }

}

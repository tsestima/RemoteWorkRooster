package old;

import java.util.Hashtable;

public class Week {

    private int week;

    private Hashtable<String, Integer> resourceToShift;

    private Week(int week) {
        this.week = week;
        this.resourceToShift = new Hashtable<>();
    }

    public void putShift(String resource, int shift) {
        resourceToShift.put(resource, shift);
    }

    public int getShift(String resource) {
        return resourceToShift.get(resource);
    }
}

package model;

import java.util.Hashtable;
import java.util.Set;

public class WeekResourceAllocation {

    int weekNumber;

    private Hashtable<String, Integer> resourceToShift;

    public WeekResourceAllocation(int weekNumber) {
        resourceToShift = new Hashtable<>();
        this.weekNumber = weekNumber;
    }

    public void put(String resource, Integer shift) {
        resourceToShift.put(resource, shift);
    }

    public Integer get(String resource) {
        return resourceToShift.get(resource);
    }

    public Hashtable<String, Integer> getResourceToShift() {
        return resourceToShift;
    }

    public Set<String> getResources() {
        return resourceToShift.keySet();
    }
}

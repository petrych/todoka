package petrych.todoka.model;

import java.util.ArrayList;

/**
 * Representations for all the valid time periods for tasks.
 */

public enum TimePeriod {
    TODAY, WEEK, LATER, COMPLETED;

    public static ArrayList<TimePeriod> getAllTimePeriods() {
        ArrayList<TimePeriod> list = new ArrayList<>();
        list.add(TimePeriod.TODAY);
        list.add(TimePeriod.WEEK);
        list.add(TimePeriod.LATER);
        list.add(TimePeriod.COMPLETED);

        return list;
    }
}

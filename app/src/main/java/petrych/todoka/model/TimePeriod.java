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

    public static TimePeriod getTimePeriodFromString(String str) {
        TimePeriod timePeriod = null;
        if (str == TODAY.toString()) {
            timePeriod = TODAY;
        }
        if (str == WEEK.toString()) {
            timePeriod = WEEK;
        }
        if (str == LATER.toString()) {
            timePeriod = LATER;
        }
        if (str == COMPLETED.toString()) {
            timePeriod = COMPLETED;
        }

        return timePeriod;
    }
}

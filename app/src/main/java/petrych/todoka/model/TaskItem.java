package petrych.todoka.model;

/**
 * A task consists of a name (text field), time period, a category and a completed status.
 * A task can be created using a default task name and default time period.
 *
 * A task is always stored in one list, Today by default.
 * May also be listed in one (only one) category.
 */


public class TaskItem {

    // Task name in case no other name is provided
    private final static String DEFAULT_TASK_NAME = "<?>";
    private final static TimePeriod DEFAULT_TIME_PERIOD = TimePeriod.TODAY;

    private String taskName;
    private TimePeriod timePeriod;
    private String category;
    private boolean completed;

    public TaskItem() {
        this.taskName = DEFAULT_TASK_NAME;
        this.timePeriod = DEFAULT_TIME_PERIOD;
        this.category = null;
        this.completed = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String name) {
        this.taskName = name;
    }

    public static String getDefaultTaskName() {
        return DEFAULT_TASK_NAME;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        this.completed = true;
    }
}

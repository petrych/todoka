/** * A task consists of a text field (name) and a checkbox (for marking the task as completed). * A task can be created, removed and completed. * A task is stored always in one time list (mandatory; Today by default). * May also be listed in one (only one) category. * * * IMPLEMENTATION status: * * (in progress) "create a task" * */public class TaskItem {    private String taskName; // text field of a task    private boolean completed;    private TimePeriod timePeriod;    private String category;    // Create a task with the given name.    public TaskItem(String taskName) {        this.taskName = taskName;        this.completed = false;        this.timePeriod = TimePeriod.TODAY;        this.category = null;    }    public TaskItem showTask() {        return this;    }    public String getName() {        return taskName;    }    public void setName(String name) {        this.taskName = name;    }    public TimePeriod getTimePeriod() {        return timePeriod;    }    public void setTimePeriod(TimePeriod timePeriod) {        this.timePeriod = timePeriod;    }    public String getCategory() {        return category;    }    public void setCategory(String category) {        this.category = category;    }    public boolean isCompleted() {        return completed;    }    public void setCompleted() {        this.completed = true;    }}
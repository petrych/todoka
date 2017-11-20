/** * A task consists of a text field (name) and a checkbox (for marking the task as completed). * A task can be created, removed and completed. * A task is stored always in one time list (mandatory; Today by default). * May also be listed in one (only one) category. * * * IMPLEMENTATION status: * * (in progress) "create a task" * */public class TaskItem {    private String taskName; // text field of a task    private boolean completed;    private TimePeriod timePeriod;    private String category;    // Create a task with the given name.    public TaskItem(String taskName) {        this.taskName = taskName;        this.completed = false;        this.timePeriod = TimePeriod.TODAY;    // Default time period.        this.category = null;    }    public TaskItem(String taskName, TimePeriod timePeriod) {        this.taskName = taskName;        this.completed = false;        this.timePeriod = timePeriod;        this.category = null;    }    public TaskItem(String taskName, TimePeriod timePeriod, String category) {        this.taskName = taskName;        this.completed = false;        this.timePeriod = timePeriod;        this.category = category;    }    public String taskToString() {        String str = "Task name: " + this.getName() +                ", time period: " + this.getTimePeriod();        if (this.isCompleted()) {            str = str + ", completed status: yes";        } else {            str = str + ", completed status: no";        }        if (this.getCategory() == null) {            str = str + ", category: empty";        } else {            str = str + ", category: " + this.getCategory().toString();        }        return str;    }    public String getName() {        return taskName;    }    public void changeName(String name) {        this.taskName = name;    }    public TimePeriod getTimePeriod() {        return timePeriod;    }    public void setTimePeriod(TimePeriod timePeriod) {        this.timePeriod = timePeriod;    }    public void setTimePeriodFromString(String str) {        if (str.equals("t")) {            this.timePeriod = TimePeriod.TODAY;        }        else if (str.equals("w")) {            this.timePeriod = TimePeriod.WEEK;        }        else if (str.equals("l")) {            this.timePeriod = TimePeriod.LATER;        }        else if (str.equals("c")) {            this.timePeriod = TimePeriod.COMPLETED;            this.setCompleted();        }    }    public String getCategory() {        return category;    }    public void setCategory(String category) {        this.category = category;    }    public boolean isCompleted() {        return completed;    }    public void setCompleted() {        this.completed = true;    }}
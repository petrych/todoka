import java.util.ArrayList;

public class TaskDatabase {
    ArrayList<TaskItem> todayTasks;
    ArrayList<TaskItem> weekTasks;
    ArrayList<TaskItem> laterTasks;
    ArrayList<TaskItem> completedTasks;

    public TaskDatabase() {
        this.todayTasks = new ArrayList<>();
        this.weekTasks = new ArrayList<>();
        this.laterTasks = new ArrayList<>();
        this.completedTasks = new ArrayList<>();
    }

    public void addTask(TaskItem task) {

        if (task.isCompleted()) {
            task.setTimePeriod(TimePeriod.COMPLETED);
            completedTasks.add(task);
        }
        else {
            if (task.getTimePeriod() == TimePeriod.TODAY) {
                todayTasks.add(task);
            }
            if (task.getTimePeriod() == TimePeriod.WEEK) {
                weekTasks.add(task);
            }
            if (task.getTimePeriod() == TimePeriod.LATER) {
                laterTasks.add(task);
            }
        }
    }

    // Return the serial number of task, NOT its index.
    public int getTaskNumberInList(TaskItem task, ArrayList<TaskItem> list) {
        TimePeriod timePeriod = task.getTimePeriod();
        int taskIndex = 0;

        if (task.getTimePeriod() == TimePeriod.TODAY) {
            taskIndex = todayTasks.indexOf(task);
        }
        if (task.getTimePeriod() == TimePeriod.WEEK) {
            taskIndex = weekTasks.indexOf(task);
        }
        if (task.getTimePeriod() == TimePeriod.LATER) {
            taskIndex = laterTasks.indexOf(task);
        }
        if (task.getTimePeriod() == TimePeriod.COMPLETED) {
            taskIndex = completedTasks.indexOf(task);
        }

        return taskIndex + 1;
    }

    public ArrayList<TaskItem> getAllTasks(ArrayList<TaskItem> list) {
        if (list == todayTasks) {
            return todayTasks;
        }
        if (list == weekTasks) {
            return weekTasks;
        }
        if (list == laterTasks) {
            return laterTasks;
        }
        if (list == completedTasks) {
            return completedTasks;
        }
        return todayTasks;
    }
}

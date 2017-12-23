package petrych.todoka.controller;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import petrych.todoka.model.TaskItem;
import petrych.todoka.model.TimePeriod;

/**
 * This class defines that tasks are stored in the task lists.
 */

public class TaskDatabase implements Parcelable {
    // Singleton database instance
    private static TaskDatabase db = null;

    // Storage for the tasks in the corresponding list.
    private ArrayList<TaskItem> todayTasks;
    private ArrayList<TaskItem> weekTasks;
    private ArrayList<TaskItem> laterTasks;
    private ArrayList<TaskItem> completedTasks;

    private ArrayList<ArrayList<TaskItem>> allTaskLists;

    // Task name in case no other name is provided
    private final static String DEFAULT_TASK_NAME = "<?>";

    // TODO - make connections with Firebase
    private TaskDatabase() {
        this.todayTasks = new ArrayList<>();
        this.weekTasks = new ArrayList<>();
        this.laterTasks = new ArrayList<>();
        this.completedTasks = new ArrayList<>();

        this.allTaskLists = new ArrayList<>();
        allTaskLists.add(todayTasks);
        allTaskLists.add(weekTasks);
        allTaskLists.add(laterTasks);
        allTaskLists.add(completedTasks);

        // TODO Test - populate Today list with 30 tasks
//        for (int i = 0; i < 30; i++) {
//            todayTasks.add(new TaskItem("task " + i));
//        }

    }

    /**
     * Return the only database object.
     * Creates a new object if the current one is null.
     * (use of the Singleton pattern)
     * @return
     */
    public static TaskDatabase getInstance() {
        if (db == null) {
            db = new TaskDatabase();
        }

        return db;
    }

    /**
     * This method is a part of the implementation of Parcelable interface
     * which makes the data transferrable between activities.
     * @param in
     */
    protected TaskDatabase(Parcel in) {
        // read list by using TaskItem.CREATOR
        this.todayTasks = new ArrayList<>();
        in.readTypedList(todayTasks, TaskItem.CREATOR);

        this.weekTasks = new ArrayList<>();
        in.readTypedList(weekTasks, TaskItem.CREATOR);

        this.laterTasks = new ArrayList<>();
        in.readTypedList(laterTasks, TaskItem.CREATOR);

        this.completedTasks = new ArrayList<>();
        in.readTypedList(completedTasks, TaskItem.CREATOR);
    }

    /**
     * This method is a part of the implementation of Parcelable interface
     * which makes the data transferrable between activities.
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(todayTasks);
        dest.writeTypedList(weekTasks);
        dest.writeTypedList(laterTasks);
        dest.writeTypedList(completedTasks);
    }

    /**
     * This method is a part of the implementation of Parcelable interface
     * which makes the data transferrable between activities.
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * This method is a part of the implementation of Parcelable interface
     * which makes the data transferrable between activities.
     */
    public static final Creator<TaskDatabase> CREATOR = new Creator<TaskDatabase>() {
        @Override
        public TaskDatabase createFromParcel(Parcel in) {
            return new TaskDatabase(in);
        }

        @Override
        public TaskDatabase[] newArray(int size) {
            return new TaskDatabase[size];
        }
    };

    /**
     * Creates a new task with the given task name,
     * even if the task name is empty.
     * @param taskName
     * @return
     */
    public TaskItem createTask(String taskName, TimePeriod timePeriod, String category) {
        TaskItem task = new TaskItem(DEFAULT_TASK_NAME);

        // Check the task name
        if (taskName.isEmpty()) {
            task.changeName(DEFAULT_TASK_NAME);
        }
        else {
            task.changeName(taskName);
        }

        // No check for time period is needed, because it is always available
        task.setTimePeriod(timePeriod);

        // Check the category
        if (category.isEmpty()) {
            task.setCategory("");
        }
        else {
            task.setCategory(category);
        }

        return task;
    }

    /**
     * Adds the given task to the corresponding list using a time period of the task.
     * @param task The task to be added to the corresponding task list.
     * @return The list of tasks.
     */
    public ArrayList<TaskItem> addTaskToList(TaskItem task) {
        if (task.isCompleted()) {
            task.setTimePeriod(TimePeriod.COMPLETED);
            completedTasks.add(task);
            return completedTasks;
        }
        else {
            if (task.getTimePeriod() == TimePeriod.TODAY) {
                todayTasks.add(task);
                return todayTasks;
            }
            if (task.getTimePeriod() == TimePeriod.WEEK) {
                weekTasks.add(task);
                return weekTasks;
            }
            if (task.getTimePeriod() == TimePeriod.LATER) {
                laterTasks.add(task);
                return laterTasks;
            }
        }
        return null;
    }

    /**
     * Returns a list of the tasks with the given time period.
     * @param timePeriod The time period of tasks in the needed list.
     * @return The the list of tasks with the given time period.
     **/

    public ArrayList<TaskItem> getListWithTasks(TimePeriod timePeriod) {
        if (timePeriod == TimePeriod.TODAY) {
            return todayTasks;
        }
        if (timePeriod == TimePeriod.WEEK) {
            return weekTasks;
        }
        if (timePeriod == TimePeriod.LATER) {
            return laterTasks;
        }
        if (timePeriod == TimePeriod.COMPLETED) {
            return completedTasks;
        }

        return todayTasks;
    }

    /**
     * Returns a list of the tasks which names contain the given string.
     * @param str The string to match in the task name.
     * @return The list of matching tasks.
     */
    public ArrayList<TaskItem> findTasksByName(String str) {
        ArrayList<TaskItem> result = new ArrayList<>();

        for (ArrayList list : allTaskLists) {
            for (Object taskObj : list) {
                TaskItem task = (TaskItem) taskObj;
                if (task.getName().contains(str)) {
                    result.add(task);
                }
            }
        }

        return result;
    }
}



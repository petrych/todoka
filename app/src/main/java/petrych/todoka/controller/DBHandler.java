package petrych.todoka.controller;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import petrych.todoka.model.TaskItem;
import petrych.todoka.model.TimePeriod;

/**
 * This class defines that tasks are stored in the task lists.
 */

public class DBHandler {
    // Singleton database instance
    private static DBHandler dbHandler = null;

    private DatabaseReference tasksDB;
    private ChildEventListener childEventListener;

    // Storage for the tasks in the corresponding list
    private ArrayList<TaskItem> todayTasks;
    private ArrayList<TaskItem> weekTasks;
    private ArrayList<TaskItem> laterTasks;
    private ArrayList<TaskItem> completedTasks;

    private ArrayList<ArrayList<TaskItem>> allTaskLists;
    private List<DataLoadedListener> dataLoadedListeners;

    private DBHandler() {
        tasksDB = FirebaseDatabase.getInstance().getReference().child("tasks");

        this.todayTasks = new ArrayList<>();
        this.weekTasks = new ArrayList<>();
        this.laterTasks = new ArrayList<>();
        this.completedTasks = new ArrayList<>();

        this.dataLoadedListeners = new ArrayList<>();

        readTaskListFromDB(TimePeriod.TODAY.toString());
        readTaskListFromDB(TimePeriod.WEEK.toString());
        readTaskListFromDB(TimePeriod.LATER.toString());

        this.allTaskLists = new ArrayList<>();
        allTaskLists.add(todayTasks);
        allTaskLists.add(weekTasks);
        allTaskLists.add(laterTasks);
        allTaskLists.add(completedTasks);

        // TODO Test - populate Today list with 12 tasks
//        for (int i = 0; i < 12; i++) {
//            TaskItem task = createTask("task " + i, TimePeriod.TODAY, "cat " + i);
//            todayTasks.add(task);
//            tasksDB.child(TimePeriod.TODAY.toString()).push().setValue(task);
//        }
//
//        for (int i = 0; i < 12; i++) {
//            TaskItem task = createTask("task " + i, TimePeriod.WEEK, "cat " + i);
//            weekTasks.add(task);
//            tasksDB.child(TimePeriod.WEEK.toString()).push().setValue(task);
//        }
//
//        for (int i = 0; i < 12; i++) {
//            TaskItem task = createTask("task " + i, TimePeriod.LATER, "cat " + i);
//            laterTasks.add(task);
//            tasksDB.child(TimePeriod.LATER.toString()).push().setValue(task);
//        }
    }

    public void addDataLoadedListener(DataLoadedListener dataLoadedListener){
        this.dataLoadedListeners.add(dataLoadedListener);
    }

    /**
     * Creates and attaches ChildEventListener to a specific task list in the database.
     * @param timePeriodString the TimePeriod string for a specific task list
     * @return created ChildEventListener
     */
    private ChildEventListener attachChildEventListener(String timePeriodString) {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TaskItem taskItem = dataSnapshot.getValue(TaskItem.class);
                addTaskToList(taskItem);
                for (DataLoadedListener listener: dataLoadedListeners) {
                    listener.onDataLoaded();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) { }
        };
        tasksDB.child(timePeriodString).addChildEventListener(childEventListener);

        return childEventListener;
    }

    /**
     * Gets task list from Firebase database.
     * @param timePeriodString
     */
    public void readTaskListFromDB(final String timePeriodString) {
        attachChildEventListener(timePeriodString);
    }

    /**
     * Detaches database listener.
     */
    public void detachDatabaseReadListener() {
        if (childEventListener != null) {
            tasksDB.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    /**
     * Returns the only one database object.
     * Creates a new object if the current one is null.
     * (use of the Singleton pattern)
     * @return database object
     */
    public static DBHandler getInstance() {
        if (dbHandler == null) {
            dbHandler = new DBHandler();
        }

        return dbHandler;
    }

    /**
     * Creates a new task with the given task name,
     * even if the task name is empty.
     * @param taskName
     * @return
     */
    public TaskItem createTask(String taskName, TimePeriod timePeriod, String category) {
        TaskItem task = new TaskItem();

        // Check the task name
        if (taskName.isEmpty()) {
            task.setTaskName(TaskItem.getDefaultTaskName());
        }
        else {
            task.setTaskName(taskName);
        }

        // No check for time period is needed, because it is always available
        task.setTimePeriod(timePeriod);

        // Check the category
        if (category.isEmpty()) {
            task.setCategory(null);
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
                if (task.getTaskName().contains(str)) {
                    result.add(task);
                }
            }
        }

        return result;
    }
}



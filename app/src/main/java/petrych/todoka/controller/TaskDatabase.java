package petrych.todoka.controller;

import java.util.ArrayList;

import petrych.todoka.model.TaskItem;
import petrych.todoka.model.TimePeriod;

/**
 * This class defines that tasks are stored in the task lists.
 */

public class TaskDatabase {

    // Storage for the tasks in the corresponding list.
    private ArrayList<TaskItem> todayTasks;
    private ArrayList<TaskItem> weekTasks;
    private ArrayList<TaskItem> laterTasks;
    private ArrayList<TaskItem> completedTasks;

    private ArrayList<ArrayList<TaskItem>> allTaskLists;

    // TODO - make connections with Firebase
    public TaskDatabase() {
        this.todayTasks = new ArrayList<>();
        todayTasks.add(new TaskItem("Task 1"));
        todayTasks.add(new TaskItem("Task 2"));
        todayTasks.add(new TaskItem("Task 3"));

        this.weekTasks = new ArrayList<>();
        weekTasks.add(new TaskItem("Task 4"));
        weekTasks.add(new TaskItem("Task 5"));
        weekTasks.add(new TaskItem("Task 6"));

        this.laterTasks = new ArrayList<>();
        laterTasks.add(new TaskItem("Task 7"));
        laterTasks.add(new TaskItem("Task 8"));
        laterTasks.add(new TaskItem("Task 9"));

        this.completedTasks = new ArrayList<>();
        completedTasks.add(new TaskItem("Task 10"));
        completedTasks.add(new TaskItem("Task 11"));
        completedTasks.add(new TaskItem("Task 12"));

        this.allTaskLists = new ArrayList<>();
        allTaskLists.add(todayTasks);
        allTaskLists.add(weekTasks);
        allTaskLists.add(laterTasks);
        allTaskLists.add(completedTasks);
    }

    public TaskItem createTask(String taskName) {
        TaskItem task = new TaskItem(taskName);
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



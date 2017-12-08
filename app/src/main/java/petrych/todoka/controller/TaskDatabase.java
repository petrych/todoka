package petrych.todoka.controller;

/**
 * This class defines that tasks are stored both in the task lists and in the corresponding files
 * which are located in the root of the app folder.
 */

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TaskDatabase {
    // Get the absolute file path to the app folder 'todoka'.
    String appPathAbsolute = getAppDir();

    // Paths for files of tasks with different time periods.
    public String fileTodayTasks = appPathAbsolute + "todayTasks.txt";
    public String fileWeekTasks = appPathAbsolute + "weekTasks.txt";
    public String fileLaterTasks = appPathAbsolute + "laterTasks.txt";
    public String fileCompletedTasks = appPathAbsolute + "completedTasks.txt";

    private JsonHandler jsonHandler;

    // Storage for the tasks from the corresponding file.
    private ArrayList<TaskItem> todayTasks;
    private ArrayList<TaskItem> weekTasks;
    private ArrayList<TaskItem> laterTasks;
    private ArrayList<TaskItem> completedTasks;

    private ArrayList<ArrayList<TaskItem>> allTaskLists;

    public TaskDatabase() throws IOException, ParseException {
        jsonHandler = new JsonHandler();

        // Populate task lists from the corresponding files.
        this.todayTasks = new ArrayList<>();
        todayTasks = jsonHandler.readTaskItemList(fileTodayTasks);

        this.weekTasks = new ArrayList<>();
        weekTasks = jsonHandler.readTaskItemList(fileWeekTasks);

        this.laterTasks = new ArrayList<>();
        laterTasks = jsonHandler.readTaskItemList(fileLaterTasks);

        this.completedTasks = new ArrayList<>();
        completedTasks = jsonHandler.readTaskItemList(fileCompletedTasks);

        this.allTaskLists = new ArrayList<>();
        allTaskLists.add(todayTasks);
        allTaskLists.add(weekTasks);
        allTaskLists.add(laterTasks);
        allTaskLists.add(completedTasks);
    }

    private String getAppDir() {
        Path appDir = Paths.get("...").toAbsolutePath();
        String appDirToString = appDir.toString();
        String appPathAbsolute = appDirToString.substring(0, appDirToString.length() - 3);

        return appPathAbsolute;
    }

    /**
     * Writes task to file using its time period.
     * @param timePeriod The time period of a task that needs to be written to file.
     * @throws IOException
     * @throws ParseException
     */
    public void writeTaskToFile(TimePeriod timePeriod) throws IOException, ParseException {
        ArrayList<TaskItem> taskList = getListWithTasks(timePeriod);
        try {
            if (taskList != null)
                jsonHandler.saveTaskListToJson(taskList, getFileByTimePeriod(timePeriod));
        }
        catch(IOException e) {
            System.out.println("Error: Couldn't write to the file. Try again.");
            e.printStackTrace();
        }
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
     * Prints the list of the tasks with the given time period.
     * @param timePeriod The time period of tasks in the list.
     **/
    public void showTasksFromFile(TimePeriod timePeriod) {
        ArrayList<TaskItem> taskList = getListWithTasks(timePeriod);
        if (taskList.isEmpty()) {
            System.out.println("The required " + timePeriod.toString() + " task list is empty.");
        }

        for (TaskItem task : taskList) {
            System.out.println("Tasks in the " + timePeriod.toString() + " task list:");
            System.out.println(task.taskToString());
        }
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
     * Returns the path of a file by the given time period.
     * @param timePeriod The time period of tasks in the needed file.
     * @return The path of the file containing tasks with the given time period.
     */
    public String getFileByTimePeriod(TimePeriod timePeriod) {
        if (timePeriod == TimePeriod.TODAY) {
            return fileTodayTasks;
        }
        if (timePeriod == TimePeriod.WEEK) {
            return fileWeekTasks;
        }
        if (timePeriod == TimePeriod.LATER) {
            return fileLaterTasks;
        }
        if (timePeriod == TimePeriod.COMPLETED) {
            return fileCompletedTasks;
        }

        return fileTodayTasks;
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

    public TaskItem createNewTask(String taskName, String deadline) {
        TaskItem task = new TaskItem(taskName);
        if (deadline.equals(""))
            return null;
        task.setDeadline(deadline);
        return task;
    }
}


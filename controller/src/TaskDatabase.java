import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TaskDatabase {
    // Paths for files of tasks with different time periods.
    public String fileTodayTasks = "/Users/nastya/repos/todoka/controller/todayTasks.txt";
    public String fileWeekTasks = "/Users/nastya/repos/todoka/controller/weekTasks.txt";
    public String fileLaterTasks = "/Users/nastya/repos/todoka/controller/laterTasks.txt";
    public String fileCompletedTasks = "/Users/nastya/repos/todoka/controller/completedTasks.txt";

    private JsonHandler jsonHandler;

    // Storage for the tasks from the corresponding filePath.
    private ArrayList<TaskItem> todayTasks;
    private ArrayList<TaskItem> weekTasks;
    private ArrayList<TaskItem> laterTasks;
    private ArrayList<TaskItem> completedTasks;

    public TaskDatabase() throws IOException, ParseException {
        jsonHandler = new JsonHandler();

        // Populate the task lists from the corresponding files
        this.todayTasks = new ArrayList<>();
        todayTasks = jsonHandler.readTaskItemList(fileTodayTasks);

        this.weekTasks = new ArrayList<>();
        weekTasks = jsonHandler.readTaskItemList(fileWeekTasks);

        this.laterTasks = new ArrayList<>();
        laterTasks = jsonHandler.readTaskItemList(fileLaterTasks);

        this.completedTasks = new ArrayList<>();
        completedTasks = jsonHandler.readTaskItemList(fileCompletedTasks);

    }

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

    // This method is used in the method of writing a task to the corresponding filePath.
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

    public void showTasksFromFile(TimePeriod period) {
        ArrayList<TaskItem> taskList = getListWithTasks(period);
        if (taskList.isEmpty()) {
            System.out.println("The task list is empty.");
        }

        for (TaskItem task : taskList) {
            System.out.println(task.taskToString());
        }
    }

    public ArrayList<TaskItem> getListWithTasks(TimePeriod period) {
        if (period == TimePeriod.TODAY) {
            return todayTasks;
        }

        if (period == TimePeriod.WEEK) {
            return weekTasks;
        }

        if (period == TimePeriod.LATER) {
            return laterTasks;
        }

        if (period == TimePeriod.COMPLETED) {
            return completedTasks;
        }

        return todayTasks;
    }

    public ArrayList<TaskItem> getListWithTasks(TaskItem task) {
        if (todayTasks.contains(task)) {
            return todayTasks;
        }

        if (weekTasks.contains(task)) {
            return weekTasks;
        }

        if (laterTasks.contains(task)) {
            return laterTasks;
        }

        if (completedTasks.contains(task)) {
            return completedTasks;
        }

        return todayTasks;
    }

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

    // TODO - in progress. Finding task name
    public TaskItem findTaskByName(String str, ArrayList<TaskItem> taskList) {
        TaskItem result = null;
        boolean taskFound = false;

        while (!taskFound) {
            for (TaskItem task : taskList) {
                if (task.getName().startsWith(str)) {
                    result = task;
                    taskFound = true;
                }
            }
        }

        return result;
    }

    // TODO - add functionality: "remove task"
    public void removeTask(TaskItem taskToRemove) {
        // ???
        System.out.println("The task is removed.");
    }
}

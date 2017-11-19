import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskDatabase {
    // Paths for files of tasks with different time periods.
    public String fileTodayTasks = "/Users/nastya/repos/todoka/controller/todayTasks.txt";
    public String fileWeekTasks = "/Users/nastya/repos/todoka/controller/weekTasks.txt";
    public String fileLaterTasks = "/Users/nastya/repos/todoka/controller/laterTasks.txt";
    public String fileCompletedTasks = "/Users/nastya/repos/todoka/controller/completedTasks.txt";

    // Default file for reading and writing.
    private String file = setDefaultFile();

    private FileReader fileReader;
    private FileWriter fileWriter;
    private JsonHandler jsonHandler;

    // Storage for the tasks from the corresponding file.
    private ArrayList<TaskItem> todayTasks;
    private ArrayList<TaskItem> weekTasks;
    private ArrayList<TaskItem> laterTasks;
    private ArrayList<TaskItem> completedTasks;

    // Mapping a CommandWord to its ArrayList.
    HashMap<CommandWord, ArrayList<TaskItem>> commandWordToList;

    // Mapping a CommandWord to the corresponding file with tasks.
    HashMap<CommandWord, String> commandWordToFile;

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

        // Create a reader and writer objects for the default file.
        createFileReader(file);
        createFileWriter(file);
    }

    private void createFileReader(String file) throws IOException {
        try {
            fileReader = new FileReader(file);
        }
        catch (FileNotFoundException exception) {
            System.out.println("Unable to open file '" + file + "'.");
        }
    }

    private void createFileWriter(String file) throws IOException {
        try {
            fileWriter = new FileWriter(file, true);
        }
        catch (IOException ex) {
            System.out.println("Error writing to file '" + file + "'.");
        }
    }

    // Set default file for reading and writing. Depends on the default timePeriod value in model/TaskItem.
    private String setDefaultFile() {
        if (TaskItem.getDefaultTimePeriod() == TimePeriod.TODAY) {
            return fileTodayTasks;
        }

        if (TaskItem.getDefaultTimePeriod() == TimePeriod.WEEK) {
            return fileWeekTasks;
        }

        if (TaskItem.getDefaultTimePeriod() == TimePeriod.LATER) {
            return fileLaterTasks;
        }
        else {
            return fileCompletedTasks;
        }
    }

    // Return the default file for reading and writing.
    public String getDefaultFile() {
        return file;
    }

    // Return the right file for the task
    public String getFileWithTasks(TaskItem task) {
        if (task.getTimePeriod() == TimePeriod.TODAY) {
            return fileTodayTasks;
        }

        if (task.getTimePeriod() == TimePeriod.WEEK) {
        return fileWeekTasks;
        }

        if (task.getTimePeriod() == TimePeriod.LATER) {
            return fileLaterTasks;
        }

        else {
            return fileCompletedTasks;
        }
    }

    public void writeTaskToFile(TaskItem task, String file) throws IOException, ParseException {
        ArrayList list = addTaskToList(task);

        try {
            if (list != null)
            jsonHandler.saveTaskListToJson(list, file);
        }
        catch(IOException e) {
            System.out.println("Error: Couldn't write to the file. Try again.");
            e.printStackTrace();
        }
    }

    // This method is used in the method of writing a task to the corresponding file.
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

    public void showTasksFromFile(String filePath) throws IOException {
        fileReader = new FileReader(filePath);
        if (fileReader.read() == -1) {
            System.out.println("The task list is empty.");
        }
        fileReader = new FileReader(filePath);

        try {
            while (fileReader.read() != -1) {
                // read the task list and return each task as a new line
                for (TaskItem task : getListWithTasks(filePath)) {
                    System.out.println(task.taskToString());
                }
            }
        }
        catch(IOException e) {
            System.out.println("Error: Couldn't read from the file. Try again.");
            e.printStackTrace();
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

    public ArrayList<TaskItem> getListWithTasks(String file) {
        if (file == fileTodayTasks) {
            return todayTasks;
        }

        if (file == fileWeekTasks) {
            return weekTasks;
        }

        if (file == fileLaterTasks) {
            return laterTasks;
        }

        if (file == fileCompletedTasks) {
            return completedTasks;
        }

        return todayTasks;
    }
}

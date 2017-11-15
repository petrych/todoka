import javafx.concurrent.Task;

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

    // Storage for the tasks from the corresponding file.
    private ArrayList<TaskItem> todayTasks;
    private ArrayList<TaskItem> weekTasks;
    private ArrayList<TaskItem> laterTasks;
    private ArrayList<TaskItem> completedTasks;

    // Mapping a CommandWord to its ArrayList.
    HashMap<CommandWord, ArrayList<TaskItem>> commandWordToList;

    // Mapping a CommandWord to the corresponding file with tasks.
    HashMap<CommandWord, String> commandWordToFile;

    public TaskDatabase() throws IOException {
        this.todayTasks = new ArrayList<>();
        this.weekTasks = new ArrayList<>();
        this.laterTasks = new ArrayList<>();
        this.completedTasks = new ArrayList<>();

        // Create a reader and write objects for default file.
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

    public void writeTaskToFile(TaskItem task, String file) throws IOException {
        try {
            fileWriter.write(taskToString(task));
            fileWriter.write("\n");           // add a line break
            fileWriter.flush();
        }
        catch(IOException e) {
            System.out.println("Error: Couldn't write to the file. Try again.");
            e.printStackTrace();
        }

        // Add to a corresponding collection only if writing to the file was successful.
        addTaskToList(task);
    }

    // This method is used in the method of writing a task to the corresponding file.
    public void addTaskToList(TaskItem task) {
        if (task.isCompleted()) {
            task.setTimePeriod(TimePeriod.COMPLETED);
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

    public ArrayList<TaskItem> getListWithTasks(ArrayList<TaskItem> list) {
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

    public void showTasksFromFile(String file) throws IOException {
        int character;
        fileReader = new FileReader(file);
        if (fileReader.read() == -1) {
            System.out.println("The task list is empty. Enter " + CommandWord.CREATE_TASK + " to create a task.");
        }
        fileReader = new FileReader(file);

        try {
            while ((character = fileReader.read())!= -1) {
                System.out.print((char)character);
            }
        }
        catch (IOException e) {
            System.out.println("Error: Couldn't read from the file. Try again.");
            e.printStackTrace();
        }
    }

    public static String taskToString(TaskItem task) {
        return "TODO: " + task.getName() +
                ", timePeriod is: " + task.getTimePeriod() +
                ", completed: " + task.isCompleted();
    }
}

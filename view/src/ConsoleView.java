import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class ConsoleView {
    private TaskDatabase db;
    private Scanner consoleReader;
    private FileReader fileReader;
    private FileWriter fileWriter;
    private String fileName = "/Users/nastya/repos/todoka/controller/todayTasks.txt";
    private CommandMap commands;

    // todo - rethink and redo the reader and the writer; move some of their functionality to the Controller
    public ConsoleView() {
        db = new TaskDatabase();
        consoleReader = new Scanner(System.in);
        commands = new CommandMap();

        // Create a reader object for the given file.
        try {
            fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to open file '" + fileName + "'.");
        }

        // Create a writer object for the given file.
        try {
            fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'.");
        }
    }
        // Handling the input from the console.
        public void mainLoop() throws IOException {

            printGreetingMessage();

            while (true) {
                commands.printCommands();
                String line;

                try {
                    line = consoleReader.nextLine();
                    if (line.equals(CommandWord.CREATE_TASK.toString())) {
                        createTask();
                    }
                    else if (line.equals(CommandWord.SHOW_TODAY_TASKS.toString())) {
                        displayFileContent();
                    }
                    else if (line.equals(CommandWord.QUIT.toString())) {
                        break;
                    }
                    else {
                        System.out.println("Cannot recognize a command. Please reenter.");
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        private void displayFileContent() throws IOException {
            int character;
            fileReader = new FileReader(fileName);
            if (fileReader.read() == -1) {
                System.out.println("The task list is empty. Enter 'ct' to create a task.");
            }
            fileReader = new FileReader(fileName);
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

        private void createTask() throws IOException {
            System.out.println("Enter a task name or press 'q' to go back.");
            String line = consoleReader.nextLine();
            if ("q".equals(line)) {
                return;
            }
            else {
                addTaskToDatabase(new TaskItem(line));
            }
        }

        private void addTaskToDatabase(TaskItem task) throws IOException {
            try {
                fileWriter.write(taskToString(task));
                fileWriter.write("\n");           // add a line break
                fileWriter.flush();
            }
           catch(IOException e) {
               System.out.println("Error: Couldn't write to the file. Try again.");
               e.printStackTrace();
            }
            db.addTask(task);
        }

        public static String taskToString(TaskItem task) {
            return "Name: " + task.getName() +
                    ", timePeriod is: " + task.getTimePeriod() +
                    ", completed: " + task.isCompleted();
        }

        private void printGreetingMessage() {
            System.out.println("TODO-ka 0.1");
            System.out.println("What would you like to do next? Enter a command:");
        }
}

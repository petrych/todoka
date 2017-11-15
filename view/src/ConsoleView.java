import java.io.*;
import java.util.Scanner;

public class ConsoleView {
    private TaskDatabase db;
    private Scanner consoleReader;

    private CommandMap commands;

    // todo - rethink and redo the reader and the writer; move some of their functionality to the Controller
    public ConsoleView() throws IOException {
        db = new TaskDatabase();
        consoleReader = new Scanner(System.in);
        commands = new CommandMap();
    }
        // Handling the input from the console.
        public void mainLoop() throws IOException {
            printGreetingMessage();
            while (true) {
                commands.printCommands();
                String line;

                // Process a command entered.
                try {
                    line = consoleReader.nextLine();
                    if (line.equals(CommandWord.CREATE_TASK.toString())) {
                        createTask();
                    }
                    else if (line.equals(CommandWord.SHOW_TODAY_TASKS.toString())) {
                        db.showTasksFromFile(db.fileTodayTasks);
                    }
                    else if (line.equals(CommandWord.SHOW_WEEK_TASKS.toString())) {
                        db.showTasksFromFile(db.fileWeekTasks);
                    }
                    else if (line.equals(CommandWord.SHOW_LATER_TASKS.toString())) {
                        db.showTasksFromFile(db.fileLaterTasks);
                    }
                    else if (line.equals(CommandWord.SHOW_COMPLETED_TASKS.toString())) {
                        db.showTasksFromFile(db.fileCompletedTasks);
                    }
                    else if (line.equals(CommandWord.QUIT.toString())) {
                        printByeMessage();
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

        private void createTask() throws IOException {
            System.out.println("Enter a task name or press 'q' to go back.");
            String line = consoleReader.nextLine();
            if (line.equals(CommandWord.QUIT)) {
                return;
            }
            else {
                TaskItem task = new TaskItem(line);
                db.writeTaskToFile(task, db.getFileWithTasks(task));
                System.out.println("The task is added successfully.");
            }
        }

        private void printGreetingMessage() {
            System.out.println("TODO-ka 0.1");
        }

        private void printByeMessage() {
            System.out.println();
            System.out.println("Bye! See you later :)");
        }
}

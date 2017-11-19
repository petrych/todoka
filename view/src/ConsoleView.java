import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleView {
    private TaskDatabase db;
    private Scanner consoleReader;
    private CommandMap commands;

    public ConsoleView() throws IOException, ParseException {
        db = new TaskDatabase();
        consoleReader = new Scanner(System.in);
        commands = new CommandMap();
    }
        // Handling the input from the console.
        public void mainLoop() throws IOException, ParseException {
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
                        db.showTasksFromFile(TimePeriod.TODAY);
                    }
                    else if (line.equals(CommandWord.SHOW_WEEK_TASKS.toString())) {
                        db.showTasksFromFile(TimePeriod.WEEK);
                    }
                    else if (line.equals(CommandWord.SHOW_LATER_TASKS.toString())) {
                        db.showTasksFromFile(TimePeriod.LATER);
                    }
                    else if (line.equals(CommandWord.SHOW_COMPLETED_TASKS.toString())) {
                        db.showTasksFromFile(TimePeriod.COMPLETED);
                    }
                    else if (line.equals(CommandWord.EDIT_TASK.toString())) {
                        // TODO in progress
                        //editTask(db.findTaskByName(line, recentFilePath));
                        editTask(db.getListWithTasks(TimePeriod.TODAY).get(0));
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

        private void createTask() throws IOException, ParseException {
            System.out.println("Enter a task name or press '" + CommandWord.QUIT + "' to go back.");
            String line = consoleReader.nextLine();
            if (line.equals(CommandWord.QUIT.toString())) {
                return;
            } else {
                TaskItem task = new TaskItem(line);
                db.addTaskToList(task);
                db.writeTaskToFile(task.getTimePeriod());
                System.out.println("The task is added successfully.");
            }
        }

        private void editTask(TaskItem task) throws IOException, ParseException {
            System.out.println("Enter 1 - to change the task name, 2 - to mark the task as completed,");
            System.out.println("3 - to change the time period, 4 - to change the category.");
            String line = consoleReader.nextLine();

            // Process a command entered.

            if (line.equals("1")) {
                System.out.println("Enter a new task name:");
                line = consoleReader.nextLine();

                task.changeName(line);
                db.writeTaskToFile(task.getTimePeriod());

                System.out.println("The task name is changed successfully.");
            }
            // TODO - add changes for "2 - to mark as completed"
            else if (line.equals("2")) {
                System.out.println("Enter 'y' - to mark the task as completed" +
                        " or any other key to return back");
                line = consoleReader.nextLine();

                if (line.equals("y")) {
                    ArrayList<TaskItem> taskListOld = db.getListWithTasks(task);
                    task.setCompleted();
                    db.writeTaskToFile(task.getTimePeriod());

                    db.addTaskToList(task);
                    taskListOld.remove(task);

                    System.out.println("The task is marked as completed");
                }
                else return;
            }

            else if (line.equals("3")) {
                System.out.println("Enter a new time period: t - today, w - week, l - later, c - completed.");

                line = consoleReader.nextLine();
                TimePeriod oldTimePeriod = task.getTimePeriod();
                ArrayList<TaskItem> taskListOld = db.getListWithTasks(oldTimePeriod);

                TimePeriod newTimePeriod = TimePeriod.TODAY;
                if (line.equals("t")) {
                    newTimePeriod = TimePeriod.TODAY;
                }
                else if (line.equals("w")) {
                    newTimePeriod = TimePeriod.WEEK;
                }
                else if (line.equals("l")) {
                    newTimePeriod = TimePeriod.LATER;
                }
                else if (line.equals("c")) {
                    newTimePeriod = TimePeriod.COMPLETED;
                    task.setCompleted();
                }

                if (newTimePeriod == oldTimePeriod)
                    // TODO add message
                    return;

                task.setTimePeriod(newTimePeriod);

                ArrayList<TaskItem> taskListNew = db.getListWithTasks(newTimePeriod);
                taskListOld.remove(task);
                taskListNew.add(task);
                db.writeTaskToFile(oldTimePeriod);
                db.writeTaskToFile(newTimePeriod);

                System.out.println("The time period of the task is changed successfully.");

            }
            // TODO - add changes for "4 - to change the category"

        }

        private void printGreetingMessage() {
            System.out.println("TODO-ka 0.1");
        }

        private void printByeMessage() {
            System.out.println();
            System.out.println("Bye! See you later :)");
        }
}

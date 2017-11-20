/**
 * This class is responsible for a console view of the app.
 *
 * A user manipulates the app by entering the available commands via a keyboard.
 *
 * The user can create a task which is immediately stored in a corresponding
 * task list (according to the time period of the task)
 * and written to the corresponding file (also according to the time period).
 *
 * Details of the newly created task can be edited:
 * - change the task name
 * - set as completed
 * - change the time period
 * - change the category
 *
 * The user can view a task list (Today, Week, Later, Completed).
 *
 * The user can edit any task in any list by entering a piece of its name and then following the instructions.
 */

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

                        System.out.println("Enter several characters of the task name:");
                        line = consoleReader.nextLine();
                        ArrayList<TaskItem> matchedTaskList = db.findTasksByName(line);
                        TaskItem taskToEdit = null;

                        if (matchedTaskList.isEmpty()) {
                            System.out.println("No tasks match the search.");
                        }
                        // Start editing when there's only 1 task matches the search.
                        else if (matchedTaskList.size() == 1) {
                            taskToEdit = matchedTaskList.get(0);
                            editTask(taskToEdit);
                        }
                        else {
                            // Print all the tasks that match the search.
                            for (TaskItem task : matchedTaskList) {
                                System.out.println(task.taskToString());
                            }

                            while (taskToEdit == null) {
                                System.out.println("To choose task for editing, enter the line number with the task. To go back, enter '" + CommandWord.QUIT.toString() + "'.");
                                line = consoleReader.nextLine();

                                // Go back to main menu when the user enters the quit command.
                                if (line.equals(CommandWord.QUIT.toString())) {
                                    break;
                                }

                                int lineNumber = Integer.parseInt(line);

                                if (lineNumber >= 0 && lineNumber <= matchedTaskList.size()) {
                                    taskToEdit = matchedTaskList.get(lineNumber - 1);
                                    editTask(taskToEdit);
                                }
                                else {
                                    System.out.println("The line number is incorrect.");
                                }
                            }
                        }
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

    /**
     * Creates a task by reading the user input for the task name.
     * @return created task.
     * @throws IOException
     * @throws ParseException
     */
        private TaskItem createTask() throws IOException, ParseException {
            System.out.println("Enter a task name or exit to the main menu by pressing '" + CommandWord.QUIT + "'.");
            String line = consoleReader.nextLine();

            // Go back when the user enters the quit command.
            if (line.equals(CommandWord.QUIT.toString())) {
                return null;
            }

            TaskItem task = new TaskItem(line);

            db.addTaskToList(task);
            db.writeTaskToFile(task.getTimePeriod());
            System.out.println("The task is added successfully. " + task.taskToString() + ".");

            System.out.println("To edit this task, press '" + CommandWord.EDIT_TASK + "'.");
            line = consoleReader.nextLine();

            if (line.equals(CommandWord.EDIT_TASK.toString())) {
                editTask(task);
            }

            return task;
        }

    /**
     * Edits the given task by reading the user input for a new task name or its time period
     * or its category or marking the task as completed.
     * Also moves the task to the corresponding task list and file if the time period or completed status is changed.
     * @param task The task to be edited.
     * @throws IOException
     * @throws ParseException
     */
        private void editTask(TaskItem task) throws IOException, ParseException {
            System.out.println("You've chosen to edit the following task:");
            System.out.println(task.taskToString() + "\n");
            System.out.println("Enter 1 - to change the task name, 2 - to mark the task as completed,");
            System.out.println("3 - to change the time period, 4 - to change the category.");
            System.out.println("exit to the main menu by pressing '" + CommandWord.QUIT + "'.");
            String line = consoleReader.nextLine();

            // Process a command entered.

            // Go back when the user enters the quit command.
            if (line.equals(CommandWord.QUIT.toString())) {
                return;
            }
            else if (line.equals("1")) {
                System.out.println("Enter a new task name:");
                line = consoleReader.nextLine();

                task.changeName(line);
                db.writeTaskToFile(task.getTimePeriod());

                System.out.println("The task name is changed successfully.");
            }
            else if (line.equals("2")) {
                System.out.println("Enter 'y' - to mark the task as completed" +
                        " or any other key to return back");
                line = consoleReader.nextLine();

                // Go back when user enters any symbol except 'y'.
                if (!line.equals("y")) {
                    System.out.println("");
                    return;
                }

                TimePeriod oldTimePeriod = task.getTimePeriod();
                ArrayList<TaskItem> taskListOld = db.getListWithTasks(oldTimePeriod);

                task.setCompleted();

                ArrayList<TaskItem> taskListNew = db.getListWithTasks(TimePeriod.COMPLETED);

                taskListOld.remove(task);
                taskListNew.add(task);

                db.writeTaskToFile(oldTimePeriod);
                db.writeTaskToFile(TimePeriod.COMPLETED);

                System.out.println("The task is marked as completed and moved to the Completed list.");
            }
            else if (line.equals("3")) {
                System.out.println("Enter a new time period: t - today, w - week, l - later, c - completed.");

                line = consoleReader.nextLine();
                TimePeriod oldTimePeriod = task.getTimePeriod();
                ArrayList<TaskItem> taskListOld = db.getListWithTasks(oldTimePeriod);

                TimePeriod newTimePeriod = TimePeriod.TODAY;
                task.setTimePeriodFromString(line);

                if (newTimePeriod == oldTimePeriod) {
                    System.out.println("The time period of the task remains the same.");
                    return;
                }

                task.setTimePeriod(newTimePeriod);

                ArrayList<TaskItem> taskListNew = db.getListWithTasks(newTimePeriod);
                taskListOld.remove(task);
                taskListNew.add(task);
                db.writeTaskToFile(oldTimePeriod);
                db.writeTaskToFile(newTimePeriod);

                System.out.println("The time period of the task is changed successfully.");

            }
            else if (line.equals("4")) {
                System.out.println("Enter a new category name:");
                line = consoleReader.nextLine();

                task.setCategory(line);
                db.writeTaskToFile(task.getTimePeriod());

                System.out.println("The category of the task is changed successfully.");
            }
            else {
                System.out.println("Cannot recognize the command. Please reenter.");
            }
        }

    /**
     * Prints a greeting message.
     */
    private void printGreetingMessage() {
            System.out.println("TODO-ka 0.1");
        }

    /**
     * Prints a bye message.
     */
    private void printByeMessage() {
            System.out.println();
            System.out.println("Bye! See you later :)");
        }
}

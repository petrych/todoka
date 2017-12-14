package petrych.todoka.model;

/**
 * IMPORTANT
 *
 * This class is responsible for storing and printing the command words defined in the separate class.
 *
 * In v.1.0 only the constructor is used, not the methods.
 * Methods are intended for use in future releases.
 **/

import java.util.HashMap;

public class CommandMap {
    private HashMap<String, CommandWord> commands;

    public CommandMap() {
        commands = new HashMap<>();
        for (CommandWord command : CommandWord.values()) {
            if (command != CommandWord.UNKNOWN) {
                commands.put(command.toString(), command);
            }
        }
    }

    public CommandWord getCommandWord(String commandWord) {
        CommandWord command = commands.get(commandWord);
        if (command != null) {
            return command;
        } else {
            return CommandWord.UNKNOWN;
        }
    }

    // Check whether a given String is a valid command word.
    public boolean isCommand(String aString) {
        return commands.containsKey(aString);
    }

    public void printCommands() {
        System.out.println("");
        System.out.println("What would you like to do? Enter a command:");
        System.out.println(getCommandsAsString());
    }

    /**
     * Returns available commands as one string
     * @return Available commands on separate lines
     */
    public String getCommandsAsString() {
        return CommandWord.QUIT.toString() + " - quit" + "\n" +
                CommandWord.CREATE_TASK.toString() + " - create new task" + "\n" +
                CommandWord.EDIT_TASK.toString() + " - edit task (change name, time period, category and mark as completed)" + "\n" +
                CommandWord.SHOW_TODAY_TASKS.toString() + " - show Today tasks" + "\n" +
                CommandWord.SHOW_WEEK_TASKS.toString() + " - show Week tasks" + "\n" +
                CommandWord.SHOW_LATER_TASKS.toString() + " - show Later tasks" + "\n" +
                CommandWord.SHOW_COMPLETED_TASKS.toString() + " - show Completed tasks";
    }
}


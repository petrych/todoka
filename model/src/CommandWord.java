/**
 * This class is responsible for defining command words used in the app.
 */

public enum CommandWord {
    UNKNOWN("?"),
    QUIT("0"),

    // Commands for tasks
    CREATE_TASK("10"),
    EDIT_TASK("11"),
    REMOVE_TASK("12"),

        SHOW_TODAY_TASKS("20"),
        SHOW_WEEK_TASKS("21"),
        SHOW_LATER_TASKS("22"),
        SHOW_COMPLETED_TASKS("23"),

    // Commands for categories
    CREATE_CATEGORY("30"),
    EDIT_CATEGORY("31"),
    REMOVE_CATEGORY("32"),

        SHOW_CATEGORY("40");

    // The string value of a command word.
    private String commandString;

    // Initialize with the corresponding command string.
    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    public String toFriendlyString() {
        if (commandString.equals("0")) {
            return "0 - quit";
        }
        if (commandString.equals("10")) {
            return "10 - create new task";
        }
        if (commandString.equals("11")) {
            return "11 - edit task";
        }
        if (commandString.equals("12")) {
            return "12 - remove task";
        }
        if (commandString.equals("20")) {
            return "20 - show Today tasks";
        }
        if (commandString.equals("21")) {
            return "21 - show Week tasks";
        }
        if (commandString.equals("22")) {
            return "22 - show Later tasks";
        }
        if (commandString.equals("23")) {
            return "23 - show Completed tasks";
        }

        return "";
    }

    public String toString(){
        return commandString;
    }
}

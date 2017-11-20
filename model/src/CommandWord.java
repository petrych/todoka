import com.sun.org.apache.regexp.internal.RE;

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



    public String toString(){
        return commandString;
    }
}

public enum CommandWord {
    UNKNOWN("?"),
    QUIT("0"),
    CREATE_TASK("1"),
    SHOW_TODAY_TASKS("2");

    // The command string.
    private String commandString;

    // Message for the case when the command is unknown.
    //private String unknownCommandMessage = "Unknown command. Please reenter.";

    // Initialize with the corresponding command string.
    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    public String toFriendlyString() {
        if (commandString.equals("0")) {
            return "0 - quit";
        }
        if (commandString.equals("1")) {
            return "1 - create new task";
        }
        if (commandString.equals("2")) {
            return "2 - show Today tasks";
        }
        return "";
    }

    public String toString(){
        return commandString;
    }
}

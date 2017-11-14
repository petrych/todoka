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
        for (CommandWord command : commands.values()) {
            System.out.println(command.toFriendlyString());
        }
        System.out.println();
    }
}

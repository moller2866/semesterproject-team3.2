package oceanCleanup.src.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CommandWordsImplementation implements CommandWords {
    private HashMap<String, Commands> validCommands;

    public CommandWordsImplementation() {
        validCommands = new HashMap<String, Commands>();
        for (Commands command : Commands.values()) {
            if (command != Commands.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    //Text-based lookup for specific command.
    @Override
    public Commands getCommand(String commandWord) {
        Commands command = validCommands.get(commandWord);
        return Objects.requireNonNullElse(command, Commands.UNKNOWN);
    }

    @Override
    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString);
    }

    @Override
    public List<String> getCommandWords() {
        return new ArrayList<>(validCommands.keySet());
    }
}

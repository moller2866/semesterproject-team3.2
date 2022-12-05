package oceanCleanup.src.domain;

public enum Commands {
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    UNKNOWN("?"),
    TALK("talk"),
    GET("get"),
    DROP("drop"),
    INVENTORY("inventory"),
    EMPTY("empty");

    private String commandName;

    Commands(String commandString) {
        this.commandName = commandString;
    }

    public String toString() {
        return commandName;
    }
}

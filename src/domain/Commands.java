package oceanCleanup.src.domain;

public enum Commands
{
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    UNKNOWN("?"),
    TALK("talk"),
    GET("get"),
    DROP("drop"),
    INVENTORY("inventory"),
    EMPTY("empty"),
    MINIGAME("minigame");

    
    private String commandName;
    
    Commands(String commandString)
    {
        this.commandName = commandString;
    }
    
    public String toString()
    {
        return commandName;
    }
}

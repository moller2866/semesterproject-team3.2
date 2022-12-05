package oceanCleanup.src.GUI;

public enum GoDirection {
    DOCKTOSHIP("north"),
    DOCKTORECYCLINGCENTER("east"),
    RECYCLINGCENTERTODOCK("west"),
    RECYCLINGCENTERTOCONTAINER("east"),
    CONTAINERTORECYCLINGCENTER("west"),
    SHIPTODOCK("south"),
    SHIPTOWHEELHOUSE("west"),
    WHEELHOUSETOSHIP("east");

    private String commandName;

    GoDirection(String commandString) {
        this.commandName = commandString;
    }

    public String toString() {
        return commandName;
    }
}


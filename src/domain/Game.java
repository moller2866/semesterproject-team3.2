package oceanCleanup.src.domain;

import javafx.scene.control.ChoiceBox;

import java.util.List;

public class Game {

    protected Room currentRoom;
    private CommandWords commands;
    Inventory playerInventory = new Inventory();
    Bucket playerBucket = new Bucket();

    public Game() {
        createRooms();
        commands = new CommandWordsImplementation();
    }

    private void createRooms() {
        Room dock, recyclingCenter, ship, wheelhouse, ocean, container;

        dock = Room.fromJson("src/data/roomdescriptions/dock.json");
        recyclingCenter = Room.fromJson("src/data/roomdescriptions/recyclingcenter.json");
        ship = Room.fromJson("src/data/roomdescriptions/ship.json");
        wheelhouse = Room.fromJson("src/data/roomdescriptions/wheelhouse.json");
        ocean = Room.fromJson("src/data/roomdescriptions/ocean.json");
        container = Room.fromJson("src/data/roomdescriptions/container.json");

        dock.setExit("east", recyclingCenter);
        dock.setExit("north", ship);

        recyclingCenter.setExit("west", dock);
        recyclingCenter.setExit("east", container);

        container.setExit("west", recyclingCenter);

        ship.setExit("south", dock);
        ship.setExit("west", wheelhouse);

        wheelhouse.setExit("east", ship);
        wheelhouse.setExit("north", ocean);

        ocean.setExit("south", wheelhouse);


        currentRoom = dock;
    }

    public boolean goRoom(Command command) {

        if (!command.hasCommandValue()) {
            //No direction on command.
            //Can't continue with GO command.
            return false;
        }

        String direction = command.getCommandValue();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            return false;
        } else {
            currentRoom = nextRoom;
            return true;
        }
    }

    public boolean getCommandChecker(Command command) {
        if (!command.hasCommandValue()) {
            //No second command value
            return false;
        }

        String secondValue = command.getCommandValue();

        return getItem(secondValue);
    }

    public boolean getItem(String secondValue) {
        if (!currentRoom.hasItem()) {
            return false;
        } else if (playerInventory.hasBucket()) {
            for (int i = 0; i < currentRoom.getItemAmount(); i++) {
                if (currentRoom.getItem(i).getName().toLowerCase().equals(secondValue)) {
                    if (playerBucket.addPlastic((Plastic) currentRoom.getItem(i))) {
                        currentRoom.removeItem(currentRoom.getItem(i));
                        return true;
                    }
                }
            }
        } else {
            for (int i = 0; i < currentRoom.getItemAmount(); i++) {
                if (currentRoom.getItem(i).getName().toLowerCase().equals(secondValue)) {
                    if (playerInventory.addItem(currentRoom.getItem(i))) {
                        currentRoom.removeItem(currentRoom.getItem(i));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean dropCommandChecker(Command command) {
        if (!command.hasCommandValue()) {
            //No second command value
            return false;
        }

        String secondValue = command.getCommandValue();

        return dropItem(secondValue);
    }

    public boolean dropItem(String secondValue) {
        if (playerInventory.isInventoryEmpty()) {
            return false;
        } else {
            if (secondValue.equals("all")) {
                dropAllItems();
                return !dropAllItems();
            } else {
                for (int i = 0; i < playerInventory.getInventorySize(); i++) {
                    if (!playerInventory.getSingleItem(i).getName().toLowerCase().equals(secondValue)) {
                        return false;
                    } else {
                        currentRoom.setItem(playerInventory.getSingleItem(i));
                        playerInventory.remove(playerInventory.getSingleItem(i).getName());
                    }
                }
            }
        }
        return true;
    }

    public boolean quit(Command command) {
        if (command.hasCommandValue()) {
            return false;
        } else {
            return true;
        }
    }

    public String getRoomDescription() {
        return currentRoom.getLongDescription();
    }

    public CommandWords getCommands() {
        return commands;
    }

    public List<String> getCommandDescriptions() {
        return commands.getCommandWords();
    }

    public Command getCommand(String word1, String word2) {
        return new CommandImplementation(commands.getCommand(word1), word2);
    }

    public String startTalk() {
        if (currentRoom.hasNPC()) {
            String output = "";
            for (int i = 0; i < currentRoom.getNPCAmount(); i++) {
                output += currentRoom.getNPC(i).startTalk() + "\n";
            }
            return output;
        }
        return "You are talking with yourself. Kinda weird...";
    }

    public String seeInventory() {
        try {
            if (playerInventory.hasBucket()) {
                return playerInventory.toString() + playerBucket.getContent();
            }
        } catch (NullPointerException e) {
        }
        return playerInventory.toString();
    }

    public boolean dropAllItems() {
        if (!playerInventory.isInventoryEmpty()) {
            currentRoom.setItem(playerInventory.removeAllFromInventory());
            return true;
        }
        return false;
    }

    public boolean emptyBucketInRoom() {
        if (!playerBucket.isEmpty()) {
            currentRoom.setItem(playerBucket.emptyBucket());
            return true;
        }
        return false;
    }

    public boolean hasMinigame() {
        if (currentRoom.getShortDescription().contains("ship 002")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRoomFull () {
        if (currentRoom.isRoomContainer()) {
            if (currentRoom.getItemAmount() >= 10) {
                return true;
            }
        }
        return false;
    }


    public Room getCurrentRoom() {
        return currentRoom;
    }
}

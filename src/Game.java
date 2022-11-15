package oceanCleanup.src;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private Room currentRoom;
    private CommandWords commands;
    Inventory playerInventory = new Inventory();

    public Game() {
        createRooms();
        commands = new CommandWordsImplementation();
    }

    private void createRooms() {
        Room dock, recyclingCenter, ship, wheelhouse, ocean, container;

        dock = new Room("on the dock at the shipyard");
        recyclingCenter = new Room("in the recycling center.\nYou see a guy with a reflex vest. He might be working here?");
        ship = new Room("onboard the ship 002\nYou see a lot of plastic.\nMaybe you can help the captain to unload the cargo?");
        wheelhouse = new Room("in the ship's wheelhouse.\nThe captain is here.\nHe greets you with a 'g'day mate!'.");
        ocean = new Room("on the Pacific Ocean.\nYou see the overview of two ships dragging a big net\nThey are gathering plastic from the ocean.");
        container = new Room("in the container for plastic waste.\nPlastic should be dropped in this container.");

        dock.setExit("east", recyclingCenter);
        dock.setExit("north", ship);
        dock.setItem(new Plastic("plastic"));
        wheelhouse.setNPC(new Captain("Jack"));
        recyclingCenter.setNPC(new Worker("Brian"));

        recyclingCenter.setExit("west", dock);
        recyclingCenter.setExit("east", container);

        container.setExit("west", recyclingCenter );

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

        if (!currentRoom.hasItem()) {
            return false;
        } else {
            if (secondValue.equals("all")) {
                pickUpAllItems();
                return true;
            } else {
                {
                    for (int i = 0; i < currentRoom.getItemAmount(); i++) {
                        if (!currentRoom.getItem(i).getName().toLowerCase().equals(secondValue)) {
                            return false;
                        } else if (currentRoom.getItem(i).getName().toLowerCase().equals(secondValue)) {
                            playerInventory.addItem(currentRoom.getItem(i));
                            currentRoom.removeItem(currentRoom.getItem(i));
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean dropCommandChecker(Command command)  {
        if (!command.hasCommandValue()) {
            //No second command value
            return false;
        }

        String secondValue = command.getCommandValue();

        if (playerInventory.isInventoryEmpty()) {
            return false;
        } else {
            if (secondValue.equals("all")) {
                dropAllItems();
                return true;
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

    public String startTalk () {
        if (currentRoom.hasNPC()) {
            String output = "";
            for (int i = 0; i < currentRoom.getNPCAmount(); i++) {
                output += currentRoom.getNPC(i).startTalk()+ "\n";
            }
            return output;
        }
        return "You are talking with yourself. Kinda weird...";
    }

    public String seeInventory () {
        return playerInventory.toString();
    }

    public String pickUpAllItems() {
        if (currentRoom.hasItem()) {
            for (int i = 0; i < currentRoom.getItemAmount(); i++) {
                playerInventory.addItem(currentRoom.getItem(i));
                currentRoom.removeItem(currentRoom.getItem(i));
            }
            return "Added items to inventory!";
        } else {
            return "No items here mate!";
        }
    }

    public String dropAllItems() {
        if (playerInventory.isInventoryEmpty()) {
            return "No items in inventory!";
        } else {
            
        }
        return "Items were dropped!";
    }

}

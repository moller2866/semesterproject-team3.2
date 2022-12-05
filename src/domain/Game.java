package oceanCleanup.src.domain;


import java.util.List;

public class Game {

    protected Room currentRoom;
    private CommandWords commands;
    private boolean talkedToCaptain = false;
    Inventory playerInventory = new Inventory();

    public Bucket getPlayerBucket() {
        return playerBucket;
    }

    Bucket playerBucket;

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
        dock.setExit("torecyclingcenter", recyclingCenter);
        dock.setExit("toship", ship);

        recyclingCenter.setExit("west", dock);
        recyclingCenter.setExit("east", container);
        recyclingCenter.setExit("todock", dock);
        recyclingCenter.setExit("tocontainer", container);

        container.setExit("west", recyclingCenter);
        container.setExit("torecyclingcenter", recyclingCenter);

        ship.setExit("south", dock);
        ship.setExit("west", wheelhouse);
        ship.setExit("todock", dock);
        ship.setExit("towheelhouse", wheelhouse);

        wheelhouse.setExit("east", ship);
        wheelhouse.setExit("north", ocean);
        wheelhouse.setExit("toship", ship);
        wheelhouse.setExit("toocean", ocean);

        ocean.setExit("south", wheelhouse);
        ocean.setExit("towheelhouse", wheelhouse);


        currentRoom = dock;
    }

    public boolean goRoom(Command command) {

        if (!command.hasCommandValue()) {
            //No direction on command.
            //Can't continue with GO command.
            return false;
        }

        String direction = command.getCommandValue();

        return goRoomDirection(direction);
    }

    public boolean goRoomDirection(String direction) {
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
                        if (currentRoom.getItem(i) instanceof Bucket) {
                            playerBucket = (Bucket) currentRoom.getItem(i);
                        }
                        currentRoom.removeItem(currentRoom.getItem(i));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean getItem(String secondValue, double x, double y) {
        if (!currentRoom.hasItem()) {
            return false;
        } else if (playerInventory.hasBucket()) {
            for (int i = 0; i < currentRoom.getItemAmount(); i++) {
                if (currentRoom.getItem(i).getName().toLowerCase().equals(secondValue)) {
                    if (currentRoom.getItem(i).getX() == x && currentRoom.getItem(i).getY() == y) {
                        if (playerBucket.addPlastic((Plastic) currentRoom.getItem(i))) {
                            currentRoom.removeItem(currentRoom.getItem(i));
                            return true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < currentRoom.getItemAmount(); i++) {
                if (currentRoom.getItem(i).getName().toLowerCase().equals(secondValue)) {
                    if (currentRoom.getItem(i).getX() == x && currentRoom.getItem(i).getY() == y) {
                        if (playerInventory.addItem(currentRoom.getItem(i))) {
                            if (currentRoom.getItem(i) instanceof Bucket) {
                                playerBucket = (Bucket) currentRoom.getItem(i);
                            }
                            currentRoom.removeItem(currentRoom.getItem(i));
                            return true;
                        }
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
                playerBucket = null;
                return !dropAllItems();
            } else {
                for (int i = 0; i < playerInventory.getInventorySize(); i++) {
                    if (!playerInventory.getSingleItem(i).getName().toLowerCase().equals(secondValue)) {
                        return false;
                    } else {
                        if (playerInventory.getSingleItem(i) instanceof Bucket) {
                            playerBucket = null;
                        }
                        currentRoom.setItem(playerInventory.getSingleItem(i));
                        playerInventory.remove(playerInventory.getSingleItem(i).getName());
                    }
                }
            }
        }
        return true;
    }

    public boolean quit(Command command) {
        return !command.hasCommandValue();
    }

    public String getRoomDescriptionCLI() {
        return currentRoom.getLongDescriptionCLI();
    }

    public String getRoomDescriptionGUI() {
        return currentRoom.getShortDescription();
    }

    public List<String> getCommandDescriptions() {
        return commands.getCommandWords();
    }

    public Command getCommand(String word1, String word2) {
        return new CommandImplementation(commands.getCommand(word1), word2);
    }

    public String startTalk() {
        if (currentRoom.hasNPC()) {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < currentRoom.getNPCAmount(); i++) {
                if (currentRoom.getNPC(i).getJob().equals("Captain")) {
                    setTalkedToCaptain(true);
                }
                if (!hasTalkedToCaptain()) {
                    return currentRoom.getNPC(i).getJob() + """
                            :
                                                       
                            Hello stranger!
                                                       
                            Have you talked to the Captain yet?
                                                       
                            I'm sure he has something for you to do.
                             
                            """;
                }
                output.append(currentRoom.getNPC(i).startTalk());
            }
            return output.toString();
        } else if (currentRoom.getName().equals("ocean"))
            return """
                    Unfortunately, fish cannot speak.

                    They actually communicate by gesture and motion!

                    Although it would be cool if they could talk, right?""";
        else
            return """
                    You are talking with yourself.

                    We all do that sometimes, don't we?

                    Maybe you can find someone to talk to.""";
    }

    public boolean currentRoomHasNPC() {
        return currentRoom.hasNPC();
    }

    public String seeInventory() {
        try {
            if (playerInventory.hasBucket()) {
                return playerInventory.toString() + playerBucket.getContent();
            }
        } catch (NullPointerException ignored) {
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

    public boolean isRoomFull() {
        if (currentRoom.isRoomContainer()) {
            return currentRoom.getItemAmount() >= 10;
        }
        return false;
    }

    public boolean hasTalkedToCaptain() {
        return talkedToCaptain;
    }

    public void setTalkedToCaptain(boolean talkedToCaptain) {
        this.talkedToCaptain = talkedToCaptain;
    }


    public Room getCurrentRoom() {
        return currentRoom;
    }
}

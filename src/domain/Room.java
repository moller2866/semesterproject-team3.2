package oceanCleanup.src.domain;

import oceanCleanup.src.data.RoomDataParser;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Room {
    private final String description;
    private final HashMap<String, Room> exits;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<NPC> NPC = new ArrayList<>();

    private ArrayList<ArrayList<Double>> borders;

    private final String name;

    public Room(String description, String name) {
        this.name = name;
        this.description = description;
        exits = new HashMap<>();
    }

    public static Room fromJson(String jsonPath) {
        String filename = jsonPath.substring(jsonPath.lastIndexOf("/") + 1, jsonPath.lastIndexOf("."));
        RoomDataParser data = new RoomDataParser(jsonPath);
        Room room = new Room(data.getDescription(), filename);

        for (HashMap<String, ?> m : data.getItems()) {
            String type = (String) m.get("type");
            double x = (double) m.get("x");
            double y = (double) m.get("y");
            room.setItem(ItemFactory.create(type, x, y));
        }

        for (HashMap<String, ?> m : data.getNPCs()) {
            String type = (String) m.get("type");
            String name = (String) m.get("name");
            double x = (double) m.get("x");
            double y = (double) m.get("y");
            room.setNPC(NPCFactory.create(type, name, x, y));
        }
        // if data has key "borders" then set boundaries
        if (data.getJsonContent().containsKey("borders")) {
            room.setBorders((data.getBorders()));
        }


        return room;
    }

    private void setBorders(ArrayList<ArrayList<Double>> borders) {
        this.borders = borders;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public void setItem(Item newItem) {
        items.add(newItem);
    }

    public void setItem(ArrayList<Item> input) {
        items.addAll(input);
    }

    public void removeItem(Item newItem) {
        items.remove(newItem);
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public boolean hasItem() {
        return !items.isEmpty();
    }

    public int getItemAmount() {
        ArrayList<Item> temp = new ArrayList<>(items);
        return temp.size();
    }

    public String getAllItemNames() {
        StringBuilder output = new StringBuilder();
        for (Item item : items) {
            output.append(item.getName()).append(" ");
        }
        return output.toString();
    }

    public String getShortDescription() {
        return "You are " + description;
    }

    public String getLongDescriptionCLI() {
        return "You are " + description + ".\n" + getExitStringCLI();
    }

    private String getExitStringCLI() {
        StringBuilder returnString = new StringBuilder("\nExits:");
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString.append(" ").append(exit);
        }
        returnString.append("\nItems: ").append(getAllItemNames());
        returnString.append("\nPeople: ").append(getAllNPCNames());
        return returnString.toString();
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }


    public void setNPC(NPC newNPC) {
        NPC.add(newNPC);
    }

    public NPC getNPC(int index) {
        return NPC.get(index);
    }

    public String getAllNPCNames() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < NPC.size(); i++) {
            output.append(NPC.get(i).getName());
            if (i != NPC.size() - 1) {
                output.append(" & ");
            }
        }
        return output.toString();

    }

    public boolean hasNPC() {
        return !NPC.isEmpty();
    }

    public int getNPCAmount() {
        return NPC.size();
    }

    public boolean isRoomContainer() {
        return getShortDescription().contains("container");
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public ArrayList<NPC> getNPCs() {
        return NPC;
    }

    public ArrayList<ArrayList<Double>> getBorders() {
        return borders;
    }
}

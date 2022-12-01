package oceanCleanup.src.domain;

import oceanCleanup.src.data.RoomDataParser;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<NPC> NPC = new ArrayList<>();

    private String name;

    public Room(String description, String name) {
        this.name = name;
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    public static Room fromJson(String jsonPath) {
        String filename = jsonPath.substring(jsonPath.lastIndexOf("/") + 1, jsonPath.lastIndexOf("."));
        RoomDataParser data = new RoomDataParser(jsonPath);
        Room room = new Room(data.getDescription(), filename);

        for (HashMap<String, ?> m : data.getItems()) {
            Object type = m.get("type");
            double x = (double) m.get("x");
            double y = (double) m.get("y");
            room.setItem(ItemFactory.create((String) type, x, y));
        }

        for (HashMap<String, String> m : data.getNPCs()) {
            String type = m.get("type");
            String name = m.get("name");
            room.setNPC(NPCFactory.create(type, name));
        }
        return room;
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
        if (items.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public int getItemAmount() {
        ArrayList<Item> temp = new ArrayList<>();
        temp.addAll(items);
        return temp.size();
    }

    public String getAllItemNames() {
        String output = "";
        for (int i = 0; i < items.size(); i++) {
            output += items.get(i).getName() + " ";
        }
        return output;
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescriptionCLI() {
        return "You are " + description + ".\n" + getExitStringCLI();
    }

    public String getLongDescriptionGUI() {
        return "You are " + description + ".\n" + getExitStringGUI();
    }

    private String getExitStringCLI() {
        String returnString = "\nExits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        returnString += "\nItems: "
                + getAllItemNames();
        returnString += "\nPeople: "
                + getAllNPCNames();
        return returnString;
    }

    private String getExitStringGUI() {
        String returnString = "";
        if (hasItem()) {
            returnString += "It looks like there are some items here.\n";
        } else {
            returnString += "There are no items here.\n";
        }
        if (hasNPC()) {
            returnString += getAllNPCNames() + " is nearby.\n";
        } else {
            returnString += "There are no people nearby.\n";
        }
        returnString += "\nYou can go:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
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
        String output = "";
        for (int i = 0; i < NPC.size(); i++) {
            output += NPC.get(i).getName();
            if (i != NPC.size() - 1) {
                output += " & ";
            }
        }
        return output;

    }

    public boolean hasNPC() {
        if (NPC.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public int getNPCAmount() {
        return NPC.size();
    }

    public boolean isRoomContainer() {
        if (getShortDescription().contains("container")) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }
}

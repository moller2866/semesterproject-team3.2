package oceanCleanup.src;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<NPC> NPC = new ArrayList<>();


    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public void setItem(Item newItem) {
        items.add(newItem);
    }

    public Item getItem(int index) {
        return items.get(index);
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

    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    private String getExitString() {
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
            if (i<NPC.size()-1) {
                output += NPC.get(i).getName() + " & ";
            } else if (i == NPC.size()-1) {
                output += NPC.get(i).getName();
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

    public int getNPCAmount () {
        return NPC.size();
    }
}

package oceanCleanup.src.domain;

import java.util.ArrayList;

public class Inventory {

    private final int limit;
    ArrayList<Item> items = new ArrayList<>();

    public Inventory() {
        this.limit = 2;
    }

    public boolean addItem(Item item) {
        if (items.size() < this.limit) {
            items.add(item);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Item> removeAllFromInventory() {
        ArrayList<Item> temp = new ArrayList<>(items);
        items.clear();
        return temp;
    }

    public boolean hasBucket() {
        for (Item item : items) {
            if (item instanceof Bucket) {
                return true;
            }
        }
        return false;
    }

    public boolean isInventoryEmpty() {
        return items.isEmpty();
    }

    public int getInventorySize() {
        return items.size();
    }

    public Item getSingleItem(int index) {
        return items.get(index);
    }


    public void remove(String name) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getName().equals(name)) {
                items.remove(i);
                return;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (Item item : items) {
            temp.append("- ").append(item.getName()).append("\n");
        }
        return "Inventory: \n"
                + temp;
    }

}


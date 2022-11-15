package oceanCleanup.src;

import java.util.ArrayList;

public class Inventory {

    private int limit;
    ArrayList<Item> items = new ArrayList<>();

    public Inventory() {
        this.limit = 2;
    }

    public Inventory(int limit) {
        this.limit = limit;
    }



    public ArrayList<Item> getInventory() {
        return items;
    }

    public boolean addItem(Item item) {
        if (items.size() < this.limit) {
            items.add(item);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Item> removeAllFromInventory (ArrayList<Item> input) {
        ArrayList<Item> returnedArrayList = new ArrayList<>();
        input.addAll(returnedArrayList);
        input.clear();
        return returnedArrayList;
    }

    public boolean isInventoryEmpty() {
        if (items.isEmpty()) return true;
        else return false;
    }

    public int getInventorySize() {
        return items.size();
    }

    public Item getSingleItem(int index) {
        return items.get(index);
    }


    public Item remove(String name) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getName().equals(name)) {
                return items.remove(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Inventory " +
                "items = " + items;
    }

    // test
    public static void main(String[] args) {
        Inventory K = new Inventory(2);
        Item item = new Bucket();
        Item item2 = new Plastic();
        Item item3 = new Plastic();

        K.addItem(item);
        K.addItem(item2);
        K.addItem(item3);



            System.out.println(K.remove("bucket2").getName());

        System.out.println();
        System.out.println(K);
    }

}


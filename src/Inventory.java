package oceanCleanup.src;

import java.util.ArrayList;

public class Inventory {

    ArrayList<Item> items = new ArrayList<>();
    int limit;

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


    public Item remove(String name) throws ItemNonExistingException {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getName().equals(name)) {
                return items.remove(i);
            }
        }
        throw new ItemNonExistingException();
    }

    @Override
    public String toString() {
        return "Inventory" +
                "items =" + items +
                '}';
    }

    // test
    public static void main(String[] args) {
        Inventory K = new Inventory(2);
        Item item = new Bucket();
        Item item2 = new Plastic("Micro");
        Item item3 = new Plastic("Micro");

        K.addItem(item);
        K.addItem(item2);
        K.addItem(item3);

        try {
            System.out.println(K.remove("bucket2").getName());
        } catch (ItemNonExistingException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println(K);
    }

}


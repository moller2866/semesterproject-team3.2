package oceanCleanup.src;

import java.util.ArrayList;

public class Inventory {

    ArrayList<Item> items = new ArrayList<>();


    public ArrayList<Item> getInventory() {
        return items;

    }


    public void addItem(Item item) {
        items.add(item);

    }


    public Item remove(String name) throws ItemNotExistingException {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getName().equals(name)) {
                return items.remove(i);
            }
        }
        throw new ItemNotExistingException();
    }

    @Override
    public String toString() {
        return "Inventory" +
                "items =" + items +
                '}';
    }

    // test
    public static void main(String[] args) {
        Inventory K = new Inventory();
        Item item = new Tool("bucket");
        Item item2 = new Plastic("Micro");

        K.addItem(item);
        K.addItem(item2);

        try {
            System.out.println(K.remove("bucket2").getName());
        } catch (ItemNotExistingException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println(K);
    }

}


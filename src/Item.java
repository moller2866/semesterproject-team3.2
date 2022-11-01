package oceanCleanup.src;

/**
 * Item class to create different types of items in game
 * e.g. plastic garbage to be picked up by player
 */
public class Item {
    String type;
    static int count;

    public Item(String type) {
        this.type = type;
        count += 1;
    }

    public String getType() {
        return type;
    }

    // Test main method
    public static void main(String[] args) {
        Item item1 = new Item("plastic");
        Item item2 = new Item("plastic");
        System.out.println(Item.count);
    }
}

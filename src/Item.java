package oceanCleanup.src;

public abstract class Item implements Interactable{
    String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

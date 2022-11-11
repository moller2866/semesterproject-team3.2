package oceanCleanup.src;

public abstract class Item implements Interactable{
    String name;

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean isInteractable() {
        return true;
    }

    @Override
    public Item pickUp() {
        return null;
    }

    @Override
    public Item drop() {
        return null;
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

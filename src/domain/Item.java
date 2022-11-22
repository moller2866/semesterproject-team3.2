package oceanCleanup.src.domain;

public abstract class Item {
    String name;

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                '}';
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

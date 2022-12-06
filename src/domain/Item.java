package oceanCleanup.src.domain;

/**
 * This is the interface for the Item classes, e.g. Plastic or Tool (Bucket).
 * @author Kasper, Jonas
 */
public abstract class Item {
    private double y;
    private double x;
    String name;

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                '}';
    }

    public Item(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public String getName() {
        return name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}

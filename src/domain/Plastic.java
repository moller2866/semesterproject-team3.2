package oceanCleanup.src.domain;

public class Plastic extends Item {
    static int count;

    public Plastic(double x, double y) {
        super("Plastic", x, y);
        count++;
    }
    public Plastic() {
        super("Plastic", 0, 0);
        count++;
    }

    public static int getCount() {
        return count;
    }

}

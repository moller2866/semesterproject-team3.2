package oceanCleanup.src;

public class Plastic extends Item{
    static int count;

    public Plastic() {
        super("Plastic");
        count++;
    }

    public static int getCount () {
        return count;
    }

}

package oceanCleanup.src.domain;

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

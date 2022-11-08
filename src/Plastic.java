package oceanCleanup.src;

public class Plastic extends Item{
    static int count;

    public Plastic(String name) {
        super(name);
        count++;
    }

    public static int getCount () {
        return count;
    }

    @Override
    public boolean isInteractable() {
        return false;
    }

    @Override
    public Item pickUp() {
        return null;
    }

    @Override
    public Item drop() {
        return null;
    }
}

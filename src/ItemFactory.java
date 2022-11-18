package oceanCleanup.src;

public class ItemFactory {

    public static Item create(String type) throws IllegalStateException {
        return switch (type.toLowerCase()) {
            case "plastic" -> new Plastic();
            case "bucket" -> new Bucket();
            default -> throw new IllegalStateException("Unknown Item type. your input was: " + type);
        };
    }
}

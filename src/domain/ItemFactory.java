package oceanCleanup.src.domain;

/**
 * This abstract factory creates item objects from JSON data.
 * @author Kasper, Jonas
 */
public class ItemFactory {

    public static Item create(String type, double x, double y) throws IllegalStateException {
        return switch (type.toLowerCase()) {
            case "plastic" -> new Plastic(x, y);
            case "bucket" -> new Bucket(x, y);
            default -> throw new IllegalStateException("Unknown Item type. your input was: " + type);
        };
    }
}

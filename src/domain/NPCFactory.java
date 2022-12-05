package oceanCleanup.src.domain;

public class NPCFactory {

    public static NPC create(String type, String name, double x, double y) throws IllegalStateException {
        return switch (type.toLowerCase()) {
            case "captain" -> new Captain(name, x, y);
            case "worker" -> new Worker(name, x, y);
            default -> throw new IllegalStateException("Unknown NPC type. your input was: " + type);
        };
    }
}

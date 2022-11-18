package oceanCleanup.src;

public class NPCFactory {

    public static NPC create(String type, String name) throws IllegalStateException {
        return switch (type.toLowerCase()) {
            case "captain" -> new Captain(name);
            case "worker" -> new Worker(name);
            default -> throw new IllegalStateException("Unknown NPC type. your input was: " + type);
        };
    }
}

package oceanCleanup.src.domain;

/**
 * This is the interface for Tools, e.g. the Bucket.
 * @author Kasper, Jonas
 */
public abstract class Tool extends Item {

    Tool(String name, double x, double y) {
        super(name, x, y);
    }

}

package oceanCleanup.src.domain;

import java.util.ArrayList;

public class Bucket extends Tool {

    ArrayList<Plastic> content = new ArrayList<>();

    Bucket(double x, double y) {
        super("Bucket", x, y);
    }

    public boolean addPlastic(Plastic plastic) {
        if (content.size() < 10) {
            content.add(plastic);
            return true;
        } else {
            return false;
        }
    }

    public String getContent() {
        return "Bucket:\n" +
                "- Contains " + content.size() + "/10 pieces of plastic";
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    public int getSize() {
        return content.size();
    }

    public ArrayList<Item> emptyBucket() {
        ArrayList<Item> temp = new ArrayList<>(content);
        content.clear();
        return temp;
    }

}

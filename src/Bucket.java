package oceanCleanup.src;

import java.util.ArrayList;

public class Bucket extends Tool {

    ArrayList<Plastic> content = new ArrayList<>();
    Bucket() {
        super("Bucket");
    }

    public String getInfo() {
        return null;
    }

    public boolean addPlastic (Plastic plastic) {
        if (content.size() < 10) {
            content.add(plastic);
            return true;
        } else {
            return false;
        }
    }

    public String getContent () {
        String temp = "Bucket:\n";
        for (Item p : content) {
            String type = p.getName();
            temp += "- "+ type + "\n";
        }
        return temp;
    }

    public boolean isEmpty () {
        return content.isEmpty();
    }

    public int getSize () {
        return content.size();
    }

    public ArrayList<Item> emptyBucket () {
        ArrayList<Item> temp = new ArrayList<>();
        temp.addAll(content);
        content.clear();
        return temp;
    }

    public static void main(String[] args) {
        Bucket buck = new Bucket();
        Item[] items = {new Plastic(),
                new Plastic(),
                new Plastic(),
                new Plastic(),
                new Plastic(),
                new Plastic(),
                new Plastic(),
                new Plastic(),
                new Plastic(),
                new Plastic()};
        for (Item p : items) {
            buck.addPlastic((Plastic) p);
        }
        buck.addPlastic(new Plastic()); // should not add this
        System.out.println(buck.getContent());
    }
}

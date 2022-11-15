package oceanCleanup.src;

import java.util.ArrayList;

/*
 * Bucket class which extends the abstract Tools class
 * This also contains the buckets inventory
 * Which is an ArrayList
 */

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
        String temp = "";
        for (Item p : content) {
            String type = p.getName();
            temp += type + ", ";
        }
        return temp;
    }

    public int getSize () {
        return content.size();
    }

    public void empty () {
        content.clear();
    }


    @Override
    public boolean isInteractable() {
        return true;
    }

    @Override
    public Item pickUp() {
        return null;
    }

    @Override
    public Item drop() {
        return null;
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

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
        Item[] items = {new Plastic("1"),
                new Plastic("2"),
                new Plastic("3"),
                new Plastic("4"),
                new Plastic("5"),
                new Plastic("6"),
                new Plastic("7"),
                new Plastic("8"),
                new Plastic("9"),
                new Plastic("10")};
        for (Item p : items) {
            buck.addPlastic((Plastic) p);
        }
        buck.addPlastic(new Plastic("11")); // should not add this
        System.out.println(buck.getContent());
    }
}

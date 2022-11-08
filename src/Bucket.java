package oceanCleanup.src;

import java.util.ArrayList;

/*
 * Bucket class which extends the abstract Tools class
 * This also contains the buckets inventory
 * Which is an ArrayList
 */

public class Bucket extends Tools {

    ArrayList<Item> content = new ArrayList<>();
    Bucket() {
        super("Bucket");
    }

    public String getToolInfo() {
        return null;
    }

    public boolean addToBucket (Item plastic) {
        if (content.size() <= 10) {
            content.add(plastic);
            return true;
        } else {
            return false;
        }

    }

    public String seeBucketContent () {
        String temp = "";
        for (Item p : content) {
            String type = p.getType();
            temp += type + ", ";
        }
        return temp;
    }

    public int seeBucketSize () {
        return content.size();
    }

    public void emptyBucket () {
        content.clear();
    }


}

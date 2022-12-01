package oceanCleanup.src.domain;

import java.util.ArrayList;

public abstract class NPC {

    protected String name;
    protected String job;
    protected ArrayList<String> talkText = new ArrayList<>();
    private double x;

    private double y;

    public NPC (String name, String job,double x, double y) {
        this.name = name;
        this.job = job;
        this.x = x;
        this.y = y;
    }


    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public abstract void setTalk(String talk);

    public String startTalk() {
        String output = "";
        for (String s : talkText) {
            output += s +"\n";
        }
        return output;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}

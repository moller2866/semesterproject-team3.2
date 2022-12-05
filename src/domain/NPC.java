package oceanCleanup.src.domain;

import java.util.ArrayList;

public abstract class NPC {

    protected String name;
    protected String job;
    protected ArrayList<String> talkText = new ArrayList<>();
    private final double x;

    private final double y;

    public NPC(String name, String job, double x, double y) {
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
        StringBuilder output = new StringBuilder();
        for (String s : talkText) {
            output.append(s).append("\n");
        }
        return output.toString();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}

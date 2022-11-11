package oceanCleanup.src;

import java.util.ArrayList;

public abstract class NPC {

    protected String name;
    protected String job;
    protected ArrayList<String> talkText = new ArrayList<>();

    public NPC (String name, String job) {
        this.name = name;
        this.job = job;
    }


    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public abstract void startTalk();

}

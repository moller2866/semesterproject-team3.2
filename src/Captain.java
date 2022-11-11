package oceanCleanup.src;

public class Captain extends NPC {

    private String talk;

    Captain(String name) {
        super(name, "Captain");
    }


    public void setTalk(String talk) {
        talkText.add(talk);
    }

    public String getTalk() {
        return talk;
    }

    @Override
    public void startTalk() {
        System.out.println(getTalk());
    }


}

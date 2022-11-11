package oceanCleanup.src;

public class Captain extends NPC {

    Captain(String name) {
        super(name, "Captain");
        setTalk(getName()+":");
        setTalk("I am "+getName()+", the Captain of the Ship.");
        setTalk("I have worked here for 40 years.");
    }


    public void setTalk(String talk) {
        talkText.add(talk);
    }

}

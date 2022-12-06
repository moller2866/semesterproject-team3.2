package oceanCleanup.src.domain;

/**
 *
 * @author Kasper, Jonas, Duncan
 */
public class Captain extends NPC {

    Captain(String name, double x, double y) {
        super(name, "Captain", x, y);
        setTalk(getName() + ":\n");
        setTalk("I am " + getName() + ", the Captain of the Ship.\n");
        setTalk("""
                This ship is working for the Ocean Cleanup Project and it's goal is to help clean the ocean of plastic. We aim to clean up 90% of floating ocean plastic pollution.
                    
                It is estimated that there are more then 150 million tons of plastic in the ocean today, and around 13.000 pieces of plastic per square kilometer. So we need help!
                    
                If you want to help then you can start unloading the ship. You can go to the recycling center by the dock, and talk to the person working there for help.""");

    }


    public void setTalk(String talk) {
        talkText.add(talk);
    }

}

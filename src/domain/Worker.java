package oceanCleanup.src.domain;

public class Worker extends NPC{

    Worker(String name) {
        super(name,"Worker");
        setTalk(getName()+":");
        setTalk("I am "+getName()+", the recycling worker here.");
        setTalk("""
                The Captain sent you?
                Great, if you want to help unload the ship, you should
                grab the bucket here in the recycling center. You can
                fill it with the plastic from the ship and then empty
                it in the container to the east of the recycling center.
                You should be able to fit 10 pieces of plastic in the bucket.
                
                Hey did you know that it is estimated that every year between
                4.8 and 12.7 million tons of plastic enter the ocean. That is equivalent
                to one garbage truck full of plastic is emptied into the ocean
                every minute.
                
                We have our work cut out for us!""");
    }
    public void setTalk(String talk) {
        talkText.add(talk);
    }}

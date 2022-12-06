package oceanCleanup.src.domain;

import java.util.Date;

/**
 *
 * @author Kasper, Jonas
 */
public class Minigame extends Game {

    boolean isStarted = false;
    long startTime;
    long endTime;


    public String endMinigame() {
        return "You have finished the minigame!\nYou did it in " + getScore() + " seconds!";
    }


    public void startTimer() {
        startTime = (new Date().getTime()) / 1000;
        this.isStarted = true;
    }

    public void endTimer() {
        endTime = (new Date().getTime()) / 1000;
    }

    public long getScore() {
        return endTime - startTime;
    }

    public boolean isStarted() {
        return isStarted;
    }


}

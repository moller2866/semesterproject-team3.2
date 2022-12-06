/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oceanCleanup.src.textUI;

import oceanCleanup.src.domain.Command;
import oceanCleanup.src.domain.Commands;
import oceanCleanup.src.domain.Game;

/**
 * @author ancla
 */
/**
 *
 * @author Kasper, Jonas, Duncan, Zubeyr
 */
public class CommandLineClient {

    private Parser parser;
    private Game game;

    public CommandLineClient() {
        game = new Game();
        parser = new Parser(game);
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println();
        System.out.println("Thank you for your help. Good bye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("> Welcome to The Ocean Cleanup adventure game <");
        System.out.println();
        System.out.println("This game is based on The Ocean Cleanup Project.");
        System.out.println();
        System.out.println("""
                It is a non-profit organization developing and
                scaling technologies to rid the oceans of plastic.""");
        System.out.println();
        System.out.println("""
                We need your help!
                If you want to help then you should go north and head
                west to speak to the Captain in the wheelhouse,
                he will tell you what to do.
                If you don't care for the planet then type 'quit'""");
        System.out.println();
        System.out.println("Type '" + Commands.HELP + "' if you need help.");
        System.out.println();
        System.out.println(game.getRoomDescriptionCLI());
    }

    private void printHelp() {
        for (String str : game.getCommandDescriptions()) {
            System.out.println(str + " ");
        }
    }

    //Controller
    public boolean processCommand(Command command) {
        boolean wantToQuit = false;

        Commands commandWord = command.getCommandName();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;
            case HELP: //fall through
                System.out.println();
                System.out.println("You are lost. You don't know what to do."
                        + "\n" +
                        "You wander around.");
                System.out.println();
                System.out.println("You have the following commands:");
                printHelp();
                break;
            case GO: //fall through
                if (game.goRoom(command)) {
                    System.out.println(game.getRoomDescriptionCLI());
                } else {
                    System.out.println("You can't go there.");
                }
                break;
            case QUIT: //fall through
                if (game.quit(command)) {
                    wantToQuit = true;
                } else {
                    System.out.println("Quit what?");
                }
                break;
            case TALK:
                System.out.println(game.startTalk());
                break;
            case GET:
                if (game.getCommandChecker(command)) {
                    System.out.println("Item were added!");
                } else {
                    System.out.println("Can't do that");
                }
                break;
            case DROP: //fall through
                if (game.dropCommandChecker(command)) {
                    System.out.println("Item were dropped!");
                } else {
                    System.out.println("Can't do that");
                }
                break;
            case INVENTORY:
                System.out.println(game.seeInventory());
                break;
            case EMPTY:
                if (game.emptyBucketInRoom()) {
                    if (game.isRoomFull()) {
                        System.out.println("You have filled the container with plastic!");
                        System.out.println("You have completed the task!");
                        wantToQuit = true;
                    } else {
                        System.out.println("You have emptied the bucket!");
                    }
                } else {
                    System.out.println("Nothing to empty!");
                }
                break;

        }
        return wantToQuit;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oceanCleanup.src.textUI;

import oceanCleanup.src.domain.Command;
import oceanCleanup.src.domain.Commands;
import oceanCleanup.src.domain.Game;
import oceanCleanup.src.domain.Minigame;

/**
 * @author ancla
 */
public class CommandLineClient {

    private Parser parser;
    private Game game;
    private Minigame minigame;

    public CommandLineClient() {
        game = new Game();
        parser = new Parser(game);
        minigame = new Minigame();
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
        System.out.println(game.getRoomDescription());
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

        if (commandWord == Commands.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == Commands.HELP) {
            System.out.println();
            System.out.println("You are lost. You don't know what to do."
                    + "\n" +
                    "You wander around.");
            System.out.println();
            System.out.println("You have the following commands:");
            printHelp();
        } else if (commandWord == Commands.GO) {
            if (game.goRoom(command)) {
                System.out.println(game.getRoomDescription());
            } else {
                System.out.println("Can't walk in that direction.");
            }
        } else if (commandWord == Commands.QUIT) {
            if (game.quit(command)) {
                wantToQuit = true;
            } else {
                System.out.println("Quit what?");
            }
        } else if (commandWord == Commands.TALK) {
            System.out.println(game.startTalk());
        } else if (commandWord == Commands.GET) {
            if (game.getCommandChecker(command)) {
                System.out.println("Item were added!");
            } else {
                System.out.println("Can't do that");
            }
        } else if (commandWord == Commands.DROP) {
            if (game.dropCommandChecker(command)) {
                if (minigame.isStarted()) {
                    if (game.isContainer()) {
                        if (game.isContainerFull()) {
                            minigame.endTimer();
                            System.out.println(minigame.endMinigame());
                        } else {
                            System.out.println("Keep going!");
                        }
                    }
                } else {
                    System.out.println("Item were dropped!");
                }
            } else {
                System.out.println("Can't do that");
            }
        } else if (commandWord == Commands.INVENTORY) {
            System.out.println(game.seeInventory());
        } else if (commandWord == Commands.EMPTY) {
            if (game.emptyBucketInRoom()) {
                System.out.println("Emptied bucket!");
            } else {
                System.out.println("Nothing to empty!");
            }
        } else if (commandWord == Commands.MINIGAME) {
            if (game.hasMinigame()) {
                System.out.println("Minigame started, lets clean up the ocean!");
                minigame.startTimer();
            } else {
                System.out.println("Can't do that");
            }
        }
        return wantToQuit;
    }
}

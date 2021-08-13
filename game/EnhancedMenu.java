package game;

import edu.monash.fit2099.engine.*;
import game.gamemode.Challenge;
import game.gamemode.Sandbox;

import java.util.*;

/**
 * A game menu, but even better
 */
public class EnhancedMenu extends Menu {
    /**
     * Display a menu to the user and have them select an option.
     * Ignores more than 26 options. Go on, write a better one.
     *
     * @param actor   the Actor representing the player
     * @param actions the Actions that the user can choose from
     * @param display the I/O object that will display the map
     * @return the Action selected by the user
     */
    @Override
    public Action showMenu(Actor actor, Actions actions, Display display) {
//        display.println("Would you like to play Challenge or Sandbox?");
        System.out.println("If you want to play Challenge ？ Please input 0");
        System.out.println("If you want to play Sandbox ？ Please input 1");
        System.out.println("If you want to exit this game ？ Please input 2");
//get input from player
        Scanner reader=new Scanner(System.in);
        //judge the input to give reaction
        if (0==reader.nextInt()){
            Challenge challenge = new Challenge();
            challenge.challengeRun();
        }if (1==reader.nextInt()){
            Sandbox sandbox = new Sandbox();
            sandbox.sandboxRun();
        }
        if (2==reader.nextInt()){
            System.exit(0);

        }

        return super.showMenu(actor, actions, display);
    }
}


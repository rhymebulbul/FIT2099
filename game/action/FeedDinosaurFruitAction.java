package game.action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.Player;
import game.actor.dinosaur.Dinosaur;
import game.actor.dinosaur.Stegosaur;
import game.vendingmachine.Fruit;
/**
 * Feeds a dinosaur, if nearby
 */
public class FeedDinosaurFruitAction extends Action{
    protected Dinosaur dinosaur;
    protected Fruit fruit;

    /**
     * Constructor.
     * @param dinosaur the dinosaur to feed
     */
    public FeedDinosaurFruitAction(Dinosaur dinosaur) {
        this.dinosaur = dinosaur;
        this.fruit = new Fruit();
    }

    /**
     * Increases the dinosaur health and removes fruit
     *
     * @see Action#execute(Actor, GameMap)
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a suitable description to display in the UI
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if(dinosaur.getDisplayChar()=='B' || dinosaur.getDisplayChar()=='S'){
            dinosaur.heal(20);
            actor.removeItemFromInventory(fruit);
            return menuDescription(actor);
        }
        return "Dinosaur is not herbivourous!";
    }

    /**
     * Describe the action in a format suitable for displaying in the menu.
     *
     * @see Action#menuDescription(Actor)
     * @param actor The actor performing the action.
     * @return a string, e.g. "Player picks up the rock"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " feeds " + fruit + " to the " + dinosaur;
    }

}

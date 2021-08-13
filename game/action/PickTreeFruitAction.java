package game.action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.vegetation.Tree;
import game.vendingmachine.Fruit;

/**
 * Picks fruit from a tree
 */
public class PickTreeFruitAction extends Action {
    protected Tree tree;

    /**
     * Constructor
     * @param tree the tree we are picking fruit from
     */
    public PickTreeFruitAction(Tree tree) {
        this.tree = tree;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        tree.removeFruit();
        actor.addItemToInventory(new Fruit());
        return menuDescription(actor);
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " picks fruit from the tree ";
    }
}

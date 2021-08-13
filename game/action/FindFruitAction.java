package game.action;

import edu.monash.fit2099.engine.*;
import game.vegetation.Bush;
import game.vegetation.Dirt;
import game.vegetation.Tree;
import game.vendingmachine.Fruit;

import java.util.List;

/**
 * The action of looking for new fruit on the map
 */
public class FindFruitAction extends Action {

    /**
     * Constructor.
     */
    public FindFruitAction() {
    }

    /**
     * Find the fruit
     * @param actor The actor performing the action
     * @param map The map the actor is on
     * @return a description of the action suitable for feedback in the UI
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int x = map.getXRange().max();
        int y = map.getYRange().max();
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= y; j++) {
                Location location = map.at(x, y);
                if(Tree.class.isAssignableFrom(map.locationOf(actor).getGround().getClass())){
                    Tree tree = (Tree) map.locationOf(actor).getGround();
                    if(tree.hasFruit()){
                        new MoveActorAction(location, " towards tree ").execute(actor, map);
                        new PickTreeFruitAction(tree).execute(actor, map);
                    }
                }else if(Bush.class.isAssignableFrom(map.locationOf(actor).getGround().getClass())){
                    Bush bush = (Bush) map.locationOf(actor).getGround();
                    if(bush.hasFruit()){
                        new MoveActorAction(location, " towards bush ").execute(actor, map);
                        new PickBushFruitAction(bush).execute(actor, map);
                    }
                }else if(Dirt.class.isAssignableFrom(map.locationOf(actor).getGround().getClass())){
                    List itemsOnGround = map.locationOf(actor).getItems();
                    for (Object item:itemsOnGround) {
                        if(Fruit.class.isAssignableFrom(item.getClass())){
                            new MoveActorAction(location, " towards fruit on the ground ").execute(actor, map);
                            new PickUpItemAction((Fruit)item).execute(actor, map);
                        }
                    }
                }
            }
        }
        return menuDescription(actor);
    }

    /**
     * A string describing the action suitable for displaying in the UI menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player drops the potato"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " searches for nearby fruit";
    }

    @Override
    public String hotkey() {
        return "0";
    }

}

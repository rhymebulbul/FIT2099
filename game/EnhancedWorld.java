package game;

import edu.monash.fit2099.engine.*;
import game.action.FindFruitAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EnhancedWorld extends World {
    protected Display display;
    protected ArrayList<GameMap> gameMaps = new ArrayList<GameMap>();
    protected ActorLocations actorLocations = new ActorLocations();
    protected Actor player; // We only draw the particular map this actor is on.
    protected Map<Actor, Action> lastActionMap = new HashMap<Actor, Action>();
    /**
     * Constructor.
     *
     * @param display the Display that will display this World.
     */
    public EnhancedWorld(Display display) {
        super(display);
        Objects.requireNonNull(display);
    }

    /**
     * Gives an Actor its turn.
     * <p>
     * The Actions an Actor can take include:
     * <ul>
     * <li>those conferred by items it is carrying</li>
     * <li>movement actions for the current location and terrain</li>
     * <li>actions that can be done to Actors in adjacent squares</li>
     * <li>actions that can be done using items in the current location</li>
     * <li>skipping a turn</li>
     * </ul>
     *
     * @param actor the Actor whose turn it is.
     */
    @Override
    protected void processActorTurn(Actor actor) {
        Location here = actorLocations.locationOf(actor);
        GameMap map = here.map();

        Actions actions = new Actions();
        for (Item item : actor.getInventory()) {
            actions.add(item.getAllowableActions());
            // Game rule. If you're carrying it, you can drop it.
            actions.add(item.getDropAction());
        }

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();

            // Game rule. You don't get to interact with the ground if someone is standing
            // on it.
            if (actorLocations.isAnActorAt(destination)) {
                actions.add(actorLocations.getActorAt(destination).getAllowableActions(actor, exit.getName(), map));
            } else {
                actions.add(destination.getGround().allowableActions(actor, destination, exit.getName()));
            }
            actions.add(destination.getMoveAction(actor, exit.getName(), exit.getHotKey()));
        }

        for (Item item : here.getItems()) {
            actions.add(item.getAllowableActions());
            // Game rule. If it's on the ground you can pick it up.
            actions.add(item.getPickUpAction());
        }
        actions.add(new DoNothingAction());
        actions.add(new FindFruitAction());

        Action action = actor.playTurn(actions, lastActionMap.get(actor), map, display);
        lastActionMap.put(actor, action);

        String result = action.execute(actor, map);
        display.println(result);
    }
}

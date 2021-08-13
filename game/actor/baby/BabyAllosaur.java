package game.actor.baby;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.Behaviour;
import game.actor.dinosaur.Allosaur;
import game.actor.dinosaur.Brachiosaur;
import game.WanderBehaviour;
import game.actor.dinosaur.Dinosaur;
import game.actor.dinosaur.Stegosaur;

/**
 * Class to represent Baby Allosaur
 */
public class BabyAllosaur extends Dinosaur {
    private Behaviour behaviour;
    private int growingTime;

    /**
     * Constructor
     * @param name name of the dinosaur
     */
    public BabyAllosaur(String name) {
        super(name, 'a', 20, 100, 100);
        behaviour = new WanderBehaviour();
    }

    /**
     * Constructor
     * @param name name of the dinosaur
     * @param b display character representing the dinosaur on the map
     */
    public BabyAllosaur(String name, char b) {
        super(name, b,50, 100, 100);
        behaviour = new WanderBehaviour();
    }

    /**
     * Grows the baby dinosaur into a adult one
     * @param map
     */
    private void growUp(GameMap map){
        if(growingTime==50){
            Location location = map.locationOf(this);
            map.removeActor(this);
            map.addActor(new Stegosaur("Stegosaur"),location);
        }
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action wander = behaviour.getAction(this, map);
        if (wander != null)
            return wander;
        growingTime++;
        growUp(map);
        return new DoNothingAction();
    }
}

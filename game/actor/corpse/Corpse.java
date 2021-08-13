package game.actor.corpse;

import edu.monash.fit2099.engine.*;

/**
 * Class representing dead dinosaurs
 */
public class Corpse extends Actor {
    private int timeToRot;
    private int hitPoints;

    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Constructor.
     * @param name        the name of the Actor
     */
    public Corpse(String name, int hitpoints) {
        super(name, '%', hitpoints);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        timeToRot++;
        if(timeToRot==20 && this.name.equals("Allosaur")){
            map.removeActor(this);
        }
        if(timeToRot==20 && this.name.equals("Stegosaur")){
            map.removeActor(this);
        }
        if(timeToRot==40 && this.name.equals("Brachisaur")){
            map.removeActor(this);
        }
        if(timeToRot==40 && this.name.equals("Pterodactyl")){
            map.removeActor(this);
        }
        return null;
    }
}

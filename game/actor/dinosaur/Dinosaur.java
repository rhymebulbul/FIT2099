package game.actor.dinosaur;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.Behaviour;
import game.WanderBehaviour;
import game.actor.corpse.Corpse;
import game.vegetation.Bush;
import game.vegetation.Dirt;
import game.vegetation.Lake;
import game.vegetation.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent dinosaurs of all sorts, shapes & sizes
 */
public class Dinosaur extends Actor {
    private Behaviour behaviour;
    protected int maxWaterPoints;
    protected int waterPoints = 60;
    protected int unconsciousness = 0;
    protected boolean isUnconscious = false;
    /**
     * Constructor.
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints, int maxHitPoints, int maxWaterPoints) {
        super(name, displayChar, hitPoints);
        this.maxHitPoints = maxHitPoints;
        this.maxWaterPoints = maxWaterPoints;
        behaviour = new WanderBehaviour();
    }

    /**
     * Add points to the current Actor's waterpoint total.
     * <p>
     * This cannot take the waterpoints over the Actor's maximum. If there is an
     * overflow, waterpoints are silently capped at the maximum.
     * <p>
     * Does not check for consciousness: unconscious Actors can still be healed
     * if the game client allows.
     *
     * @param waterPoints number of waterpoints to add.
     */
    public void hydrate(int waterPoints) {
        this.waterPoints += waterPoints;
        this.waterPoints = Math.min(waterPoints, maxWaterPoints);
    }

    /**
     * Do some damage to the current Actor.
     * <p>
     * If the Actor's waterPoints go down to zero, it will be knocked out.
     *
     * @param waterPoints number of waterPoints to deduct.
     */
    public void dehydrate(int waterPoints) {
        this.waterPoints -= waterPoints;
    }

    /**
     * Keeps counting till the starving dinosaur falls unconscious
     *
     * @return whether the dinosaur is still starving
     */
    protected boolean fallUnconscious() {
        this.unconsciousness++;
        this.isUnconscious = true;
        return true;
    }

    /**
     * Moves to nearby corpses to feed
     * @param map Gamemap of the world
     */
    protected Corpse moveToCorpse(GameMap map) {
        int x = map.getXRange().max();
        int y = map.getYRange().max();
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= y; j++) {
                Location corpseLocation = map.at(x, y);
                if (corpseLocation.getActor().getDisplayChar() == '%') {
                    this.moveTo(map.at(x, y), map);
                    return (Corpse) map.getActorAt(corpseLocation);
                }
            }
        }
        return null;
    }

    /**
     * Keep hurting the corpse as the dinosaur heals itself
     * @param corpse Corpse the dinosaur will be feeding from
     */
    protected void feedOnCorpse(Corpse corpse) {
        if(corpse.getHitPoints()>=10){
            corpse.hurt(10);
            this.heal(10);
        }
    }

    /**
     * Moves to nearby tree to eat
     *
     * @param map Gamemap of the world
     */
    protected Tree moveToTree(GameMap map) {
        int x = map.getXRange().max();
        int y = map.getYRange().max();
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= y; j++) {
                Ground groundType = map.at(x, y).getGround();
                if (Tree.class.isAssignableFrom(groundType.getClass())) {
                    this.moveToSurrounds(map.at(i,j), map);
                    return (Tree) groundType;
                }
            }
        }
        return null;
    }

    /**
     * Moves to nearby bush to eat
     * @param map Gamemap of the world
     */
    protected Bush moveToBush(GameMap map) {
        int x = map.getXRange().max();
        int y = map.getYRange().max();
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= y; j++) {
                Ground groundType = map.at(x, y).getGround();
                if (Bush.class.isAssignableFrom(groundType.getClass())) {
                    this.moveTo(map.at(x, y), map);
                    return (Bush) groundType;
                }
            }
        }
        return null;
    }

    /**
     * Moves to nearby lakes to fish or drink
     *
     * @param map Gamemap of the world
     */
    protected Lake moveToLake(GameMap map) {
        int x = map.getXRange().max();
        int y = map.getYRange().max();
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= y; j++) {
                Ground groundType = map.at(x, y).getGround();
                if (Lake.class.isAssignableFrom(groundType.getClass())) {
                    this.moveToSurrounds(map.at(x, y), map);
                    return (Lake) groundType;
                }
            }
        }
        return null;
    }

    /**
     * Moves dinosaur to target location
     * @param targetLocation where we want to move the dinosaur
     * @param map Gamemap of the world
     */
    public void moveTo(Location targetLocation, GameMap map){
        map.removeActor(this);
        map.addActor(this, targetLocation);
    }

    /**
     * Moves dinosaur to the nearest location beside a particular object
     * @param targetLocation where we want to move the dinosaur
     * @param map Gamemap of the world
     */
    public void moveToSurrounds(Location targetLocation, GameMap map){
        moveTo(findSurrounds(targetLocation, map), map);
    }

    /**
     * Finds the surrounding areas that are of a particular terrain around a given location
     * @param location location we want the surrounds around
     * @param map Gamemap of the world
     * @return Arraylist of all the surrounding locations
     */
    public Location findSurrounds(Location location, GameMap map){
        int x =  location.x()-1;
        int y = location.y()-1;
        for(int i=x; i<=x+2; i++) {
            for (int j = y; j <= 2; j++) {
                Location surround = new Location(map, i, j);
                if (i == x + 1 && j == y + 1) {
                    i++;
                    j++;
                }else if(surround.getGround() instanceof Dirt){
                    return surround;
                }
            }
        }
        return null;
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

        return new DoNothingAction();

    }
}

package game.actor.dinosaur;

import edu.monash.fit2099.engine.*;
import game.*;
import game.actor.corpse.Corpse;
import game.actor.baby.BabyPterodactyl;
import game.actor.corpse.PterodactylCorpse;
import game.vegetation.Tree;
import game.vendingmachine.egg.PterodactylEgg;
import game.vegetation.Lake;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TransferQueue;

/**
 * Pterodactyl, a flying carnivourous dinosaur that can fish but feeds on rotting corpses
 */
public class Pterodactyl extends BabyPterodactyl {
    private Behaviour behaviour;
    private final String sex;
    private final Chance sexChance = new Chance(50);
    private int pregnancyTime = 0;
    private int flightTime = 0;
    private boolean canFly = true;

    /**
     * Constructor.
     * All Pterodactyl are represented by a 'P' and have 100 hit points.
     * @param name the name of this Pterodactyl
     * @param sex  male female
     */
    public Pterodactyl(String name, String sex) {
        super(name, 'P');
        this.sex = sex;
        behaviour = new WanderBehaviour();
    }

    /**
     * Constructor.
     * All Pterodactyl are represented by a 'P' and have 100 hit points.
     * @param name the name of this Pterodactyl
     */
    public Pterodactyl(String name) {
        super(name, 'P');
        if (sexChance.isChance()) {
            this.sex = "F";
        } else {
            this.sex = "M";
        }
        behaviour = new WanderBehaviour();
    }

    /**
     * Accessor
     * @return sex of this dinosaur
     */
    public String getSex() {
        return sex;
    }

    /**
     * Checks if partner is of the opposite sex so that they can breed
     *
     * @param partner propective mate
     * @return whether they can breed
     */
    private boolean isCompatible(Pterodactyl partner) {
        return this.getSex().equals("F") && partner.getSex().equals("M") && this.hitPoints >= 70 && partner.hitPoints >= 70;
    }

    /**
     * Allows to Pterodactyls to breed
     *
     * @param partner male pterodactyls to breed with
     * @param map     Gamemap of the world
     * @return An egg the pair laid
     */
    private PterodactylEgg breed(Pterodactyl partner, GameMap map) {
        pregnancyTime++;
        if (this.getSex().equals("F") && isCompatible(partner) && pregnancyTime == 10) {
            Ground groundType = map.locationOf(this).getGround();
            if (Tree.class.isAssignableFrom(groundType.getClass())) {
                PterodactylEgg egg = new PterodactylEgg("Pterodactyl Egg", map.locationOf(this));
                map.locationOf(this).addItem(egg);
                return egg;
            }
        }
        return null;
    }

    /**
     * Tries to catch all 3 fish that fit in the Pterodactyls beak
     *
     * @param map gamemap of the world
     * @return array containing all fish caught
     */
    private Fish[] fish(GameMap map) {
        Lake lake = null;
        int fishCaught = new Random().nextInt(3);
        Fish[] allfishCaught = new Fish[3];
        try {
            lake = getLake(map);
            for (int i = 0; i < fishCaught; i++) {
                assert lake != null;
                allfishCaught[i] = lake.removeFish();
            }
        } catch (Exception ignored) {

        }
        return allfishCaught;
    }

    /**
     * Eats all fish fished from this lake
     * @param map Gamemap of the world
     */
    private void eatFish(GameMap map) {
        Fish[] allfishCaught = fish(map);
        for (int i = 0; i <= allfishCaught.length; i++) {
            if (allfishCaught[i]!=null) {
                this.heal(30);
            }
        }

    }

    /**
     * Finds lake below the Pterodactyl and makes the Pterodactyl hydrate from it
     *
     * @param map Gamemap of the world
     * @return The lake below the Pterodactyl
     */
    private Lake getLake(GameMap map){
        Location pterodactylLocation = map.locationOf(this);
            if (Lake.class.isAssignableFrom(pterodactylLocation.getGround().getClass())) {
                this.hydrate(30);
                return (Lake) pterodactylLocation.getGround();
            }
        return null;
    }

    /**
     * Kills the poor flying thing
     *
     * @param map Gamemap of the world
     */
    void death(GameMap map) {
        Location location = map.locationOf(this);
        map.removeActor(this);
        PterodactylCorpse corpse = new PterodactylCorpse("Pterodactyl Corpse");
        map.addActor(corpse, location);
    }

    /**
     * whether the Pterodactyl can fly or is walking
     *
     * @return whether the Pterodactyl is walking or flying
     */
    public boolean isCanFly() {
        return canFly;
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

        flightTime++;
        if (flightTime >= 30) {
            canFly = false;
        } else if (flightTime >= 60) {
            flightTime = 0;
            canFly = true;
        }

        this.hurt(1);
        if (this.hitPoints <= 90) {
            display.println(this.name + " at " + map.locationOf(this).x() + "," + map.locationOf(this).y() + " on the map is getting  hungry!");
            this.moveToLake(map);

            Corpse corpse = this.moveToCorpse(map);

            if (corpse.getHitPoints() >= 10) {
                this.feedOnCorpse(corpse);
            }
            if (this.hitPoints <= 0) {
                this.fallUnconscious();
            }
            if (this.unconsciousness == 20) {
                this.death(map);
            }
            if(this.hitPoints>=10){
                this.unconsciousness=0;
                this.isUnconscious = true;
            }

            this.eatFish(map);

            this.dehydrate(1);
            if (this.waterPoints <= 40) {
                display.println(this.name + " at " + map.locationOf(this).x() + "," + map.locationOf(this).y() + " on the map is getting thirsty!");
                moveToLake(map);
                hydrate(30);
            }

        }
        return new DoNothingAction();
    }
}
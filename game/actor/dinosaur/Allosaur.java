package game.actor.dinosaur;

import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.actor.baby.BabyAllosaur;
import game.Behaviour;
import game.Chance;
import game.actor.corpse.AllosaurCorpse;
import game.actor.corpse.Corpse;
import game.vegetation.Lake;
import game.vendingmachine.egg.AllosaurEgg;
import game.WanderBehaviour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A carnivorous dinosaur that feeds on harmless Stegosaurs.
 * The Allosaur
 */
public class Allosaur extends BabyAllosaur {
    private Behaviour behaviour;
    private final String sex;
    private final Chance sexChance = new Chance(50);
    private int pregnancyTime = 0;
    private final Map<Stegosaur, Integer> attackedStegosaurs = new HashMap<>();

    /**
     * Constructor.
     * All Allosaur are represented by a 'A' and have 100 hit points.
     *
     * @param name the name of the Allosaur
     */
    public Allosaur(String name) {
        super(name,'A');
        if(sexChance.isChance()){
            this.sex = "F";
        }else{
            this.sex = "M";
        }
        behaviour = new WanderBehaviour();
    }

    /**
     * Constructor
     * All Allosaur are represented by a 'A' and have 100 hit points.
     * @param name the name of the Allosaur
     * @param sex sex of the Allosaur
     */

    public Allosaur(String name, String sex) {
        super(name, 'A');
        this.sex = sex;
        behaviour = new WanderBehaviour();
    }

    /**
     * Accessor
     * @return sex of the Pterodactyl
     */
    public String getSex() {
        return sex;
    }

    /**
     * Checks if partner is of the opposite sex so that they can breed
     * @param partner propective mate
     * @return whether they can breed
     */
    private boolean isCompatible(Allosaur partner){
        return this.getSex().equals("F") && partner.getSex().equals("M") && this.hitPoints>=50 && partner.hitPoints>=50;
    }

    /**
     * Allows to Allosaur to breed
     * @param partner male Allosaur to breed with
     * @param map Gamemap of the world
     * @return An egg the pair laid
     */
    private AllosaurEgg breed(Allosaur partner, GameMap map){
        pregnancyTime++;

        if(this.getSex().equals("F") && isCompatible(partner) && pregnancyTime==20){
            return new AllosaurEgg("Allosaur Egg", map.locationOf(this));
        } else
            return null;
    }

    /**
     * Attacks any nearby Stegosaurs hurting them & healing itself
     * @param stegosaur the Stegosaur it is attacking
     */
    private void attackStegosaur(Stegosaur stegosaur, GameMap map){
        for (Integer each : attackedStegosaurs.values()) {
            if(each>=20){
                this.moveTo(map.locationOf(stegosaur), map);
                stegosaur.hurt(20);
                this.heal(20);
            }
        }
    }

    /**
     * Finds & attacks any nearby Stegosaurs, kills them, & doesn't attack them again for 20 turns if it fails
     * @param map The Game map the dinosaurs are located on
     */
    private void eat(GameMap map){
//        List items = null;
//        Location selfLocation = map.locationOf(this);
        int x =  map.getXRange().max();
        int y = map.getYRange().max();
        for(int i=0; i<=x; i++){
            for(int j=0; j<=y; j++){
                Location preyLocation = map.at(x,y);
                if(preyLocation.getActor().getDisplayChar()=='S'){
                    Stegosaur prey =  (Stegosaur) map.getActorAt(preyLocation);
                    attackStegosaur(prey, map);
                    if(prey.isConscious()){
                        prey.addAttack(this);
                        this.attackedStegosaurs.put(prey, 0);
                    }else{
                        prey.death(map);
                        this.heal(10);
                    }
                }
            }
        }
    }

    /**
     * Kills this instance of a dinosaur & replaces it on the map with a corpse
     * @param map The Game map the dinosaurs are located on
     */

    private void death(GameMap map){
        Location location = map.locationOf(this);
        map.removeActor(this);
        AllosaurCorpse corpse = new AllosaurCorpse("Allosaur Corpse");
        map.addActor(corpse, location);
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

        this.hurt(1);
        if(this.hitPoints<=90) {
            display.println(this.name + " at " + map.locationOf(this).x() + "," + map.locationOf(this).y() + " on the map is getting  hungry!");
            this.eat(map);
        }
        if(this.hitPoints<=0){
            this.fallUnconscious();
        }
        if(this.unconsciousness>=20){
            this.death(map);
        }
        if(this.hitPoints>=10){
            this.unconsciousness=0;
            this.isUnconscious = true;
        }

        for (Map.Entry<Stegosaur, Integer> entry : attackedStegosaurs.entrySet()) {
            Stegosaur key = entry.getKey();
            Integer value = entry.getValue();
            attackedStegosaurs.replace(key, value+1);
            if(value>=20){
                attackedStegosaurs.remove(key);
            }
        }

        this.dehydrate(1);
        if(this.waterPoints<=40){
            display.println(this.name + " at " + map.locationOf(this).x() + "," + map.locationOf(this).y() + " on the map is getting thirsty!");
            this.moveToLake(map);
            this.hydrate(30);
        }

        return new DoNothingAction();
    }
}

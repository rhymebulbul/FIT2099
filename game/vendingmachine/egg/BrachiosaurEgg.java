package game.vendingmachine.egg;

import edu.monash.fit2099.engine.Location;
import game.actor.baby.BabyBrachiosaur;

/**
 * Egg that will hatch into a Brachisaur
 */
public class BrachiosaurEgg extends Egg {
    private int hatchTime;

    /**
     * Constructor
     * @param name name of the dinosaur egg
     */
    public BrachiosaurEgg(String name, Location location) {
        super(name, 1000, location);
        this.hatchTime = 0;
    }

    /**
     * hatches egg into a baby dinosaur and removes the egg shell pieces
     */
    private void hatch(){
        Location location = super.getLocation();
        location.removeItem(this);
        location.addActor(new BabyBrachiosaur("Baby Brachiosaur"));
    }

    /**
     * counts time and hatches egg upon 10 turns
     * @param location location on the gamemap where the egg is situated
     */
    public void tick(Location location){
        super.tick(location);
        hatchTime++;
        if(hatchTime==10){
            hatch();
        }
    }
}

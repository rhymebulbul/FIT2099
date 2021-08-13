package game.vendingmachine.egg;

import edu.monash.fit2099.engine.*;

/**
 * Class to represent dinosaur eggs
 */
public class Egg extends Item {
    protected int ecoPoints;
    private int hatchTime;
    private Location location;

    public Location getLocation() {
        return location;
    }

    /**
     * Constructor
     * @param name kinds of egg
     */
    public Egg(String name, int ecoPoints, Location location) {
        super(name, 'E',true);
        this.location = location;
        this.ecoPoints = ecoPoints;
        this.hatchTime = 0;
    }



}

package game.vendingmachine;

import edu.monash.fit2099.engine.*;

/**
 * A juicy fruit, to alleviate hunger, if of a dinosaur
 */
public class Fruit extends Item {
    private int age = 0;
    private boolean ripe = false;
    private boolean fallen = false;
    private boolean rotten = false;
    private int ecoPoints = 30;
    //private Chance chance;
    protected char displayChar;

    /**
     *use f represent fuit on map
     */
    public Fruit(int ecoPoints) {
        super("Fruit", 'f', true);
        this.ecoPoints = ecoPoints;
        rippen();
    }

    public Fruit() {
        super("Fruit", 'f', true);
        this.ecoPoints = 0;
        rippen();
    }

    /**
     * Rippens fruit
     */
    public void rippen(){
        this.ripe = true;
    }

    /**
     * Rots away fruit lying on the ground
     */
    public void rot(){
        this.rotten = true;
    }

    /**
     * makes ripe fruit fall from the tree to the ground
     */
    public void fall(){
        this.fallen = true;
    }

    public void tick(Location location) {
        //super.tick(location);

        age++;
        if (age == 10)
            displayChar = 'f';
        if (age == 20)
            displayChar = 'F';
    }

    /**
     * checks if the fruit is fallen on the ground
     * @return
     */
    public boolean isFallen() {
        return fallen;
    }


    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }
}

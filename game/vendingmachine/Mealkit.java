package game.vendingmachine;

import game.PortableItem;

/**
 * A meal for a hungry dinosaur
 */
public class Mealkit extends PortableItem {
    private final int ecoPoints;
    /**
     *
     * @param ecoPoints value of a meal kit
     * use m represent mealkit
     */
    public Mealkit(int ecoPoints) {

        super("Mealkit", 'm');
        this.ecoPoints = ecoPoints;
    }
}

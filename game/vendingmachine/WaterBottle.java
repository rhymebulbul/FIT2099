package game.vendingmachine;

import game.PortableItem;

/**
 * A waterbottle to let thirsty dinosaurs drink if there is not enough rain
 */
public class WaterBottle extends PortableItem {
    private final int ecoPoints;

    /**
     *
     * @param ecoPoints value of a WaterBottle
     * use m represent WaterBottle
     */
    public WaterBottle(int ecoPoints) {
        super("WaterBottle", 'w');
        this.ecoPoints = ecoPoints;
    }
}

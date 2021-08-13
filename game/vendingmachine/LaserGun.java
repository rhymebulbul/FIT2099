package game.vendingmachine;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A lethal weapon
 * Mainly intended to kill Stegosaurs
 * May not work on other dinosaurs
 */
public class LaserGun extends WeaponItem {
    public final int ecoPoints = 500;
    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     */
    public LaserGun(String name, char displayChar, int damage, String verb) {
        super("Laser Gun", 'g', 15, "zaps");
    }

    public int getEcoPoints() {
        return ecoPoints;
    }
}

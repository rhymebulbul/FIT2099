package game;

import game.vendingmachine.Fruit;

/**
 * A thing that swims in lakes & is of great taste to Pterodactyls
 */
public class Fish extends PortableItem{
    private int ecoPoints;
    private int amount = 5;

    private final Chance bornChance = new Chance(60);
    private final int foodPoints;
    private Fish Fish = null;
    /**
     * Class constructor specifing class attributes
     */
    public Fish() {
        super("Fish", '^');
        this.foodPoints = 5;
    }

    private void bornFish(){

        if( Fish == null &&bornChance.isChance()){
            Fish = new Fish();
        }
    }
}

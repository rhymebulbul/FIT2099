package game.vegetation;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.Chance;
import game.vendingmachine.Fruit;

/**
 * class to represent a bush grown on the ground
 */

public class Bush extends Ground {
    private int age = 0;
    private Fruit fruit;
    private final Chance ripeFruitChance = new Chance(10);
    /**
     * Constructor.
     */
    public Bush() {
        super('*');
    }

    /**
     * grows a fruit on this bush
     */

    private void growFruit(){
        if(fruit == null && ripeFruitChance.isChance()){
            this.fruit = new Fruit();
        }
    }

    /**
     * Picks any fruit on the bush & removes it
     * @return whether there was fruit & if it was picked
     */
    public boolean removeFruit(){
        this.fruit = null;
        return true;
    }

    /**
     * Accessor
     * @return Fruit on the bush
     */
    public Fruit getFruit() {
        return fruit;
    }

    /**
     * Check whether this bush has any fruit
     * @return whether this bush has fruit
     */
    public boolean hasFruit(){
        return this.fruit!=null;
    }

    /**
     * Bush can also experience the joy of time.
     * @param location The location of the Bush
     */
    @Override
    public void tick(Location location) {
        super.tick(location);

        growFruit();

        age++;
        if (age == 15)
            displayChar = 'X';
    }
}

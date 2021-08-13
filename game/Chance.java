package game;

import java.util.Random;

/**
 * Class to randomize the chances of rain, fruit growing, fishes being born & so much more!
 */
public class Chance {
    private final int probability;
    private final int upperBound;

    /**
     * Constructor
     * @param probability Chances of the given event being true, ie. happening
     * @param upperBound upper limit of the probabilty, ie, 6 for a die
     */
    public Chance(int probability, int upperBound) {
        this.probability = probability;
        this.upperBound = upperBound;
    }

    /**
     * Constructor
     * @param probability Chances of the given event being true, ie. happening
     */
    public Chance(int probability) {
        this.probability = probability;
        this.upperBound = 100;
    }

    /**
     * Selects a random integer within the range
     * @return The probability of the event happening
     */
    public int getChance(){
        Random random = new Random();
        return random.nextInt(this.upperBound) + 1;
    }

    /**
     * Checks whether the event will happen, given the random probabilty function given
     * @return Whether the event will happen
     */
    public boolean isChance(){
        Random random = new Random();
        int result = random.nextInt(this.upperBound) + 1;
        return result <= this.probability;
    }
}

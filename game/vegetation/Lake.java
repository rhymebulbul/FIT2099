package game.vegetation;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.Chance;
import game.Fish;

import java.util.ArrayList;

/**
 *  Class that represents a Lake of water dinosaurs can drink from,
 *  which it rains into & new fishes are born into
 */
public class Lake extends Ground {

    // Counts the number of turns it hasn't rained for
    private int rainTurn;
    // Capacity of the Lake to hold water
    private int capacity;
    // Maximum number fish to store in stock
    private final int limit;
    // Location of ground object
    private Location location;
    // List to store stock of fish
    private final ArrayList<Fish> fishStock;
    private final Chance newFish;
    private final Chance rainfallAmount;
    private final Chance rainChance ;
    /**
     * Class Constructor specifiying lake parameters
     */
    public Lake() {
        // TODO fix ~ display char
        super('~');
        this.capacity = 25;
        this.rainTurn = 0;
        this.rainChance = new Chance(20);
        this.rainfallAmount = (new Chance(60, 60));
        this.limit = 25;
        this.newFish = new Chance(60);
        this.fishStock = new ArrayList<>();
        for(int i=0; i<=5; i++){
            addFish();
        }
    }

    /*
    Adds a given amount of rainfall to the lake
     */
    private void makeItRain(int rainfall){
        this.capacity+= rainfall;
    }

    /**
     * Checks if fish stock is full and if not adds a new fish
     * @return Boolean whether fish stock is full
     */
    public boolean addFish(){
        if(fishStock.size()<=this.limit){
            return fishStock.add(new Fish());
        } else return false;
    }

    /**
     * Removes fish eaten by dinosaurs from stock
     */
    public Fish removeFish(){
        return this.fishStock.remove(this.fishStock.size()-1);
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.getDisplayChar() == 'P';
    }

    /**
     * Keeps track of time, called once per turn
     * @param location The location of the Lake
     */
    @Override
    public void tick(Location location) {
        super.tick(location);

        this.rainTurn++;
        if(this.rainTurn%10==0){
            if(this.rainChance.isChance()){
                makeItRain(20*this.rainfallAmount.getChance()/10);
            }
        }

        if(this.newFish.isChance()){
            addFish();
        }

    }

}

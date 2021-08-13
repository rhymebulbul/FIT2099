package game.vegetation;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.Chance;
import game.vegetation.Bush;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {
	private final Chance newBushChance = new Chance(1);
	private final Chance higherBushChance = new Chance(10);
	private final Chance nextToTreeBushChance = new Chance(0);
	private Bush bush;

	/**
	 * Constructor
	 */

	public Dirt() {

		super('.');
	}

	/**
	 * Grows a new bush on the dirt
	 * @param map
	 */
	public void growBush(GameMap map){
		if(bush!=null && newBushChance.isChance()){
			bush = new Bush();
		}

	}

	/**
	 * Dirt can also experience the joy of time.
	 * @param location The location of the Dirt
	 */
	public void tick(GameMap map, Location location) {
		super.tick(location);
		growBush(map);

	}


}

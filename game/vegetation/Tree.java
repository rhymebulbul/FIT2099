package game.vegetation;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.Chance;
import game.vendingmachine.Fruit;

/**
 * Class that represents trees, lovely & tall
 */
public class Tree extends Ground {
	private int age = 0;
	private int timeToRot = 0;
	private final Chance ripeFruitChance = new Chance(50);
	private final Chance fallenFruitChance = new Chance(5);
	private Fruit fruit = null;

	/**
	 * Constructor
	 */
	public Tree() {
		super('+');
	}

	/**
	 * Accessor
	 * @return fruit on the tree
	 */
	public Fruit getFruit() {
		return fruit;
	}

	/**
	 * Picks & removes any fruit if on this tree
	 * @return whether there was any fruit & if it has been picked
	 */
	public boolean removeFruit(){
		this.fruit = null;
		return true;
	}

	/**
	 * Grows a fruit on this tree
	 */
	private void growFruit(){
		if(fruit == null && ripeFruitChance.isChance()){
			fruit = new Fruit(1);
		}
	}

	/**
	 * Makes ripe fruit fall to the ground
	 */
	private void fallenFruit(){
		if(fruit!=null && fallenFruitChance.isChance()){
			fruit.fall();
		}
	}

	/**
	 * Makes fallen fruit rot away if not picked up soon enough
	 */
	private void rottenFruit(){
		if(fruit!=null &&timeToRot==15 && fruit.isFallen()){
			fruit.rot();
		}
	}

	/**
	 * To check if the tree is barren
	 * @return whether the tree has any fruit or not
	 */
	public boolean hasFruit(){
		return this.fruit!=null;
	}

	@Override
	public boolean canActorEnter(Actor actor) {
		return actor.getDisplayChar() == 'P';
	}
	
	/**
	 * Tree can also experience the joy of time.
	 * @param location The location of the Tree
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);

		growFruit();
		fallenFruit();
		rottenFruit();

		timeToRot++;
		age++;
		if (age == 20)
			displayChar = 'T';
	}
}

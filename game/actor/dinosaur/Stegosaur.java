package game.actor.dinosaur;


import edu.monash.fit2099.engine.*;
import game.*;
import game.actor.baby.BabyStegosaur;
import game.actor.corpse.StegosaurCorpse;
import game.vendingmachine.egg.StegosaurEgg;
import game.vegetation.Bush;
import game.vendingmachine.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * A herbivorous dinosaur prey to the Allosaur
 */
public class Stegosaur extends BabyStegosaur {
	private Behaviour behaviour;
	private final String sex;
	private final Chance sexChance = new Chance(50);
	private int pregnancyTime = 0;
	private final ArrayList<Allosaur> attackedAllosaurs = new ArrayList<>();

	/**
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 100 hit points.
	 * 
	 * @param name the name of this Stegosaur
	 */
	public Stegosaur(String name) {
		super(name, 'S');
		if(sexChance.isChance()){
			this.sex = "F";
		}else{
			this.sex = "M";
		}
		behaviour = new WanderBehaviour();
	}

	/**
	 *
	 * @param name stegosaur
	 * @param sex sex of the dinosaur
	 */
	public Stegosaur(String name, String sex) {
		super(name, 'S');
		this.sex = sex;
		behaviour = new WanderBehaviour();
	}

	/**
	 * Accessor
	 * @return sex of the dinosaur
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * Checks if partner is of the opposite sex so that they can breed
	 * @param partner propective mate
	 * @return whether they can breed
	 */
	private boolean isCompatible(Stegosaur partner){
		return this.getSex().equals("F") && partner.getSex().equals("M") && this.hitPoints>=70 && partner.hitPoints>=70;
	}

	/**
	 * Allows to Stegosaur to breed
	 * @param partner male Stegosaur to breed with
	 * @param map Gamemap of the world
	 * @return An egg the pair laid
	 */
	private StegosaurEgg breed(Stegosaur partner, GameMap map){
		pregnancyTime++;

		if(this.getSex().equals("F") && isCompatible(partner) && pregnancyTime==10){
			return new StegosaurEgg("Stegosaur Egg", map.locationOf(this));
		} else
			return null;
	}

	/**
	 * Adds this attacked Stegosaur to the list of attacked ones, so that it is not attacked by the same one until 20 turns have passed
	 * @param allosaur the Allosaur that attacked this Stegosaur
	 */
	public void addAttack(Allosaur allosaur){
		this.attackedAllosaurs.add(allosaur);
	}

	/**
	 * Removes this attacked Stegosaur to the list of attacked ones once 20 turns have passed
	 * @param allosaur the Allosaur that attacked this Stegosaur
	 */
	public void removeAttack(Allosaur allosaur){
		this.attackedAllosaurs.remove(allosaur);
	}

	/**
	 * Try eating fruit on the ground or on bushes
	 * @param map Gamemap of the world
	 */
	private void eat(GameMap map){
		try{
			eatGroundFruit(map);
		} catch (Exception ignored){

		}try {
			eatBushFruit(map);
		} catch (Exception ignored){

		}
	}

	/**
	 * Moves to any nearby bushes to look for fruit
	 * @param map Gamemap of the world
	 */
	private void eatBushFruit(GameMap map){
		Bush bush = this.moveToBush(map);
		if(bush.hasFruit()){
			this.heal(10);
			bush.removeFruit();
		}
	}

	/**
	 * Eat fruit drop to the ground from trees
	 * @param map Gamemap of the world
	 */
	private void eatGroundFruit(GameMap map){
		List items = null;
		Location actorLocation = map.locationOf(this);
		int x =  actorLocation.x()-1;
		int y = actorLocation.y()-1;
		for(int i=x; i<=x+2; i++){
			for(int j=y; j<=2; j++){
				if(i==x+1 && j==y+1){
					i++;
					j++;
				}else{
					Location fruitLocation = new Location(map, i, j);
					items.add(fruitLocation.getItems());

					Fruit fruitClass = new Fruit();
					for (Object each: items) {
						if(each.getClass()==fruitClass.getClass()){
							heal(10);
						}
					}

					fruitLocation.removeItem(fruitClass);
				}
			}
		}
	}

	/**
	 * Kills the poor thing before an Allosaur attacks it
	 * @param map Gamemap of the world
	 */
	void death(GameMap map){
		Location location = map.locationOf(this);
		map.removeActor(this);
		StegosaurCorpse corpse = new StegosaurCorpse("Stegosaur Corpse");
		map.addActor(corpse, location);
	}

	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		return new Actions(new AttackAction(this));
	}

	/**
	 * Select and return an action to perform on the current turn.
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		Action wander = behaviour.getAction(this, map);
		if (wander != null)
			return wander;

		this.hurt(1);

		if(this.hitPoints<=90) {
			display.println(this.name + " at " + map.locationOf(this).x() + "," + map.locationOf(this).y() + " on the map is getting hungry!");
			this.eat(map);
		}
		if(this.hitPoints<=0){
			this.fallUnconscious();
		}
		if(this.unconsciousness==20){
			this.death(map);
		}
		if(this.hitPoints>=10){
			this.unconsciousness=0;
			this.isUnconscious = true;
		}

		this.dehydrate(1);
		if(this.waterPoints<=40){
			display.println(this.name + " at " + map.locationOf(this).x() + "," + map.locationOf(this).y() + " on the map is getting thirsty!");
			this.moveToLake(map);
			this.hydrate(30);
		}
		return new DoNothingAction();
	}

}

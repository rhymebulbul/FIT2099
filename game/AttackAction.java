package game;

import java.util.Random;

import edu.monash.fit2099.engine.*;
import game.actor.corpse.Corpse;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected edu.monash.fit2099.engine.Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(edu.monash.fit2099.engine.Actor target) {
		this.target = target;
	}

	@Override
	public String execute(edu.monash.fit2099.engine.Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		if (rand.nextBoolean()) {
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		target.hurt(damage);
		if (!target.isConscious()) {
			Actor corpse = new Corpse("dead ", 0);
			map.locationOf(target).addActor(corpse);
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	@Override
	public String menuDescription(edu.monash.fit2099.engine.Actor actor) {
		return actor + " attacks " + target;
	}
}

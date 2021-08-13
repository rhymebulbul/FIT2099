package game;

import edu.monash.fit2099.engine.*;
import game.action.PickBushFruitAction;
import game.action.PickTreeFruitAction;
import game.actor.dinosaur.Brachiosaur;
import game.vegetation.Bush;
import game.vegetation.Dirt;
import game.vegetation.Tree;
import game.vendingmachine.Fruit;

import java.util.List;

/**
 * Class representing the Player.
 */
public class Player extends Actor {

	private final Menu menu = new Menu();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

//	/**
//	 * Moves actor to a new location
//	 * @param location where to move the actor to
//	 * @param map Gamemap of the world
//	 */
//	private void moveTo(Location location, GameMap map){
//		map.removeActor(this);
//		map.addActor(this, location);
//	}

	/**
	 * Moves to nearby squares to look for fruit
	 *
	 * @param map Gamemap of the world
	 */
	protected boolean findFruitSquare(GameMap map) {
		int x = map.getXRange().max();
		int y = map.getYRange().max();
		for (int i = 0; i <= x; i++) {
			for (int j = 0; j <= y; j++) {
				Location location = map.at(x, y);
				if(Tree.class.isAssignableFrom(map.locationOf(this).getGround().getClass())){
					Tree tree = (Tree) map.locationOf(this).getGround();
					if(tree.hasFruit()){
						new MoveActorAction(location, " towards tree ").execute(this, map);
						new PickTreeFruitAction(tree).execute(this, map);
						return true;
					}
				}else if(Bush.class.isAssignableFrom(map.locationOf(this).getGround().getClass())){
					Bush bush = (Bush) map.locationOf(this).getGround();
					if(bush.hasFruit()){
						new MoveActorAction(location, " towards bush ").execute(this, map);
						new PickBushFruitAction(bush).execute(this, map);
						return true;
					}
				}else if(Dirt.class.isAssignableFrom(map.locationOf(this).getGround().getClass())){
					List itemsOnGround = map.locationOf(this).getItems();
					for (Object item:itemsOnGround) {
						if(Fruit.class.isAssignableFrom(item.getClass())){
							new MoveActorAction(location, " towards fruit on the ground ").execute(this, map);
							this.addItemToInventory(new Fruit());
							itemsOnGround.remove(item);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null) {
			return lastAction.getNextAction();
		}

		findFruitSquare(map);
		return menu.showMenu(this, actions, display);
	}
}

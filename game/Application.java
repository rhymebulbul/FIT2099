package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;
import game.actor.dinosaur.Brachiosaur;
import game.actor.dinosaur.Stegosaur;
import game.vegetation.Bush;
import game.vegetation.Dirt;
import game.vegetation.Lake;
import game.vegetation.Tree;

/**
 * The main class for the Jurassic World game.
 */
public class Application {

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(), new Bush(), new Lake());
		
		List<String> map = Arrays.asList(
		"................................................................................",
		".......................................................~~~~~~...................",
		".....#######.............................................~~~~~..................",
		".....#_____#.............*******...........................~~~~~~~~~............",
		".....#_____#................***..................................~~~~...........",
		".....###.###....................................................................",
		"................................................................................",
		"......................................+++.......................................",
		".......................................++++..........~~~~.......................",
		"...................................+++++...............~~~......................",
		".....................................++++++.....................................",
		"......................................+++.......................................",
		".....................................+++....................~...................",
		"................................................................................",
		"............+++.................................................................",
		".............+++++..............................................................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"...........................***..........................................++.++...",
		".........................*******.........................................++++...",
		"............................*******.......................................++....",
		"................................................................................");

		List<String> secondMap = Arrays.asList(
				"................................................................................",
				"................................................................................",
				"................................................................................",
				".........................*******................................................",
				"............................***.................................................",
				"................................................................................",
				"................................................................................",
				"......................................+++.......................................",
				".......................................++++.....................................",
				"...................................+++++........................................",
				".....................................++++++.....................................",
				"......................................+++.......................................",
				".....................................+++........................................",
				"................................................................................",
				"............+++.................................................................",
				".............+++++..............................................................",
				"...............++........................................+++++..................",
				".............+++....................................++++++++....................",
				"............+++.......................................+++.......................",
				"................................................................................",
				".........................................................................++.....",
				"...........................***..........................................++.++...",
				".........................*******.........................................++++...",
				"............................*******.......................................++....",
				"................................................................................");
		//add second map
		List<String> map1 = new ArrayList<>();

//		map1=secondMap;
//		secondMap= map1;

		GameMap gameMap = new GameMap(groundFactory, map);
		GameMap secondGameMap = new GameMap(groundFactory, secondMap);


		world.addGameMap(gameMap);
		world.addGameMap(secondGameMap);
		
		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, gameMap.at(9, 4));
		
		// Place a pair of stegosaurs in the middle of the map
		gameMap.at(30, 12).addActor(new Stegosaur("Stegosaur"));
		gameMap.at(32, 12).addActor(new Stegosaur("Stegosaur"));

		gameMap.at(36, 16).addActor(new Brachiosaur("Brachiosaur", "M"));
		gameMap.at(38, 18).addActor(new Brachiosaur("Brachiosaur", "F"));
		gameMap.at(31, 20).addActor(new Brachiosaur("Brachiosaur", "M"));
		gameMap.at(37, 21).addActor(new Brachiosaur("Brachiosaur", "F"));

		world.run();
	}
}
package game.action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import game.gamemode.Challenge;

/**
 * An Action that doesn't do anything.
 *
 * Use this to implement waiting or similar actions in game clients.
 */
public class StartNewGameAction extends Action{
        private Display display;

    /**
     * Constructor
     */
    public StartNewGameAction() {
        }

        @Override
        public String execute(Actor actor, GameMap map) {
            new Challenge();
            return menuDescription(actor);
        }

        @Override
        public String menuDescription(Actor actor) {
            return actor + " does nothing";
        }

        @Override
        public String hotkey() {
            return "0";
        }

}

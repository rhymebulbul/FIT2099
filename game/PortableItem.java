package game;

import edu.monash.fit2099.engine.Item;

/**
 * Base class for any item that can be picked up and dropped.
 */
public class PortableItem extends Item {
	/**
	 *
	 * @param name name of protable item
	 * @param displayChar char used to represent name
	 */
	public PortableItem(String name, char displayChar) {
		super(name, displayChar, true);
	}
}

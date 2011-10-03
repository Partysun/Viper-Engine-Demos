package com.partysun.flight.viper;

/**
 * A miniature linked list class. Useful for optimizing time-critical or highly
 * repetitive tasks! See <code>VipQuadTree</code> for how to use it, IF YOU
 * DARE.
 */
public class VipList {

	/**
	 * Stores a reference to a <code>FlxObject</code>.
	 */
	public VipObject object;
	/**
	 * Stores a reference to the next link in the list.
	 */
	public VipList next;

	/**
	 * Creates a new link, and sets <code>object</code> and <code>next</code> to
	 * <code>null</code>.
	 */
	public VipList() {
		object = null;
		next = null;
	}

	/**
	 * Clean up memory.
	 */
	public void destroy() {
		object = null;
		if (next != null)
			next.destroy();
		next = null;
	}
}

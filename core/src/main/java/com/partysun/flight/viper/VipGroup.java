package com.partysun.flight.viper;

import java.util.ArrayList;

/**
 * This is an organizational class that can update and render a bunch of <code>VipBasic</code>s.
 * 
 * @author	Yura - (@Partysun - yura.zatsepin@gmail.com)
 */
public class VipGroup extends VipBasic {

	// ===========================================================
    // Constants
    // ===========================================================
	
	/**
	 * Use with <code>sort()</code> to sort in ascending order.
	 */
	static public final int ASCENDING = -1;
	
	/**
	 * Use with <code>sort()</code> to sort in descending order.
	 */
	static public final int DESCENDING = 1;
	 
	// ===========================================================
    // Fields
    // ===========================================================
	
	/**
	 * Array of all the <code>VipBasic</code>s that exist in this group.
	 */
	public ArrayList<VipBasic> members;
	/**
	 * The number of entries in the members array.
	 * For performance and safety you should check this variable
	 * instead of members.length unless you really know what you're doing!
	 */
	public float length;

	/**
	 * Internal helper variable for recycling objects a la <code>FlxEmitter</code>.
	 */
	protected int _marker;
	
	/**
	 * Helper for sort.
	 */
	protected String _sortIndex;
	/**
	 * Helper for sort.
	 */
	protected int _sortOrder;
			 
	// ===========================================================
    // Constructors
    // ===========================================================
	
	/**
	 * Organizational of VipObjects 
	 * @param MaxSize
	 */
	public VipGroup(int MaxSize)
	{
		super();
		members = new ArrayList<VipBasic>(MaxSize);
		length = 0;
		_marker = 0;
		_sortIndex = null;
		//log().info("init Group");
	}
	
	/**
	 * @default MaxSize = 0
	 */
	public VipGroup()
	{
		this(0);		
	}
	
	
	//============ Parameterless default constructor
	
	// ===========================================================
    // Methods
    // ===========================================================
	@Override
	public void destroy() {
		members.clear();
		_sortIndex = null;
	}

	@Override
	public void update() {
		for (VipBasic basic : members) {
			if((basic != null) && basic.exists && basic.active)
			{			
				basic.preUpdate();
				basic.update();
				basic.postUpdate();
			}
		}		
	}

	@Override
	public void postUpdate() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void draw(float alpha) {		
		super.draw(alpha);
		for (VipBasic basic : members) {
			if((basic != null) && basic.exists && basic.active)	{				
				basic.setVisible(true);
				basic.draw(alpha);				
			}	
			else 
			{
				basic.setVisible(false);
			}
		}		
	}
	
	/**
	 * Add game object into group.
	 * @param object - VipSprite/VipObject (ex. your any game object)
	 */
	public void add(VipBasic basic) {
		this.members.add(basic);
	}	
	
	/**
	 * Recycling is designed to help you reuse game objects without always re-allocating or "newing" them.	 *	
	 * @return	A reference to the object that was created.  Don't forget to cast it back to the Class you want (e.g. myObject = myGroup.recycle(myObjectClass) as myObjectClass;).
	 */
	public VipBasic recycle()
	{
		//TODO: Dummy for recycle. MUST do it like recycle!!!
		//basic = getFirstAvailable();
		for(VipBasic basic : members) {		 
			if (basic != null && !basic.exists)		
				return basic;
		}
		return null;		
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (members != null)
		for (VipBasic object: members)
			object.setVisible(visible);
	}
}
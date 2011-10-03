package com.partysun.flight.viper;

/**
* Stores a 2D floating point coordinate.
* 
* @author Yura - (@Partysun - yura.zatsepin@gmail.com)
*/	
public class VipPoint {		
		
	// ===========================================================
    // Fields
    // ===========================================================
	
	/**
	 * X - coordinate in 2d space
	 * @default 0
	 */
	public float x;
	
	/**
	 * Y - coordinate in 2d space
	 * @default 0
	 */
	public float y;
	
	// ===========================================================
    // Constructors
    // ===========================================================
	/**
	* Instantiate a new point object.
	* 
	* @param	X		The X-coordinate of the point in space.
	* @param	Y		The Y-coordinate of the point in space.
	*/		
	public VipPoint(float X, float Y)
	{
		super();
		x = X;
		y = Y;
	}
			
	//============ Parameterless default constructor
	/**
	 * @default float X = 0, float Y = 0
	 */
	public VipPoint()
	{
		this(0,0);
	}
	
	// ===========================================================
    // Methods
    // ===========================================================
	
	/**
	* Instantiate a new point object.
	* 
	* @param	X		The X-coordinate of the point in space.
	* @param	Y		The Y-coordinate of the point in space.
	*/
	public VipPoint make(float X, float Y)
	{
		x = X;
		y = Y;
		return this;
	}
			
	/**
	 * Helper function, just copies the values from the specified point.
	 * 
	 * @param	Point	Any <code>VipPoint</code>.
	 * 
	 * @return	A reference to itself.
	 */
	public VipPoint copyFrom(VipPoint Point)
	{
		make(Point.x, Point.y);
		return this;
	}
			
	/**
	 * Helper function, just copies the values from this point to the specified point.
	 * 
	 * @param	Point	Any <code>VipPoint</code>.
	 * 
	 * @return	A reference to the altered point parameter.
	 */
	public VipPoint copyTo(VipPoint Point)
	{
		Point.make(x, y);
		return Point;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "x: "+x +" y: "+y;
	}
}

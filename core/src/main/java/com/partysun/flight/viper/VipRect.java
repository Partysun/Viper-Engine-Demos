package com.partysun.flight.viper;

/**
 * Stores a rectangle.
 * 
 * @author	Yura - (@Partysun - yura.zatsepin@gmail.com)
 */
public class VipRect {
	
	// ===========================================================
    // Fields
    // ===========================================================
	
	/**
	 * @default 0
	 */
	public float x;
	/**
	 * @default 0
	 */
	public float y;
	/**
	 * @default 0
	 */
	public float width;
	/**
	 * @default 0
	 */
	public float height;
	
	// ===========================================================
    // Constructors
    // ===========================================================
	/**
	 * Instantiate a new rectangle.
	 * 
	 * @param	X		The X-coordinate of the point in space.
	 * @param	Y		The Y-coordinate of the point in space.
	 * @param	Width	Desired width of the rectangle.
	 * @param	Height	Desired height of the rectangle.
	 */
	public VipRect(float X, float Y, float Width, float Height)
	{
		x = X;
		y = Y;
		width = Width;
		height = Height;
	}
	//============ Parameterless default constructor
	public VipRect()
	{
		this(0,0,0,0);
	}	
	
	// ===========================================================
    // Methods
    // ===========================================================
	/**
	 * The X coordinate of the left side of the rectangle.  Read-only.
	 */
	public float Left()
	{
		return x;
	}
	
	/**
	 * The X coordinate of the right side of the rectangle.  Read-only.
	 */
	public float Right()
	{
		return x + width;
	}
	
	/**
	 * The Y coordinate of the top of the rectangle.  Read-only.
	 */
	public float Top()
	{
		return y;
	}
	
	/**
	 * The Y coordinate of the bottom of the rectangle.  Read-only.
	 */
	public float bottom()
	{
		return y + height;
	}
	
	/**
	 * Instantiate a new rectangle.
	 * 
	 * @param	X		The X-coordinate of the point in space.
	 * @param	Y		The Y-coordinate of the point in space.
	 * @param	Width	Desired width of the rectangle.
	 * @param	Height	Desired height of the rectangle.
	 * 
	 * @return	A reference to itself.
	 */
	public VipRect make(float X, float Y, float Width, float Height)
	{
		x = X;
		y = Y;
		width = Width;
		height = Height;
		return this;
	}
	
	/**
	 * Helper function, just copies the values from the specified rectangle.
	 * 
	 * @param	Rect	Any <code>FlxRect</code>.
	 * 
	 * @return	A reference to itself.
	 */
	public VipRect copyFrom(VipRect Rect)
	{
		x = Rect.x;
		y = Rect.y;
		width = Rect.width;
		height = Rect.height;
		return this;
	}
	
	/**
	 * Helper function, just copies the values from this rectangle to the specified rectangle.
	 * 
	 * @param	Point	Any <code>FlxRect</code>.
	 * 
	 * @return	A reference to the altered rectangle parameter.
	 */
	public VipRect copyTo(VipRect Rect)
	{
		Rect.x = x;
		Rect.y = y;
		Rect.width = width;
		Rect.height = height;
		return Rect;
	}
	
	/**
	 * Checks to see if some <code>VipRect</code> object overlaps this <code>VipRect</code> object.
	 * 
	 * @param	Rect	The rectangle being tested.
	 * 
	 * @return	Whether or not the two rectangles overlap.
	 */
	public boolean overlaps(VipRect Rect)
	{
		return (Rect.x + Rect.width > x) && (Rect.x < x+width) && (Rect.y + Rect.height > y) && (Rect.y < y+height);
	}
}

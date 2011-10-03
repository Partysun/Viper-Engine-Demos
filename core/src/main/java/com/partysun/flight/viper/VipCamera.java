package com.partysun.flight.viper;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import playn.core.GroupLayer;
import playn.core.Surface;
import playn.core.SurfaceLayer;

/**
 * @author	Yura - (@Partysun - yura.zatsepin@gmail.com)
 */
public class VipCamera extends VipBasic{
	// ===========================================================
    // Constants
    // ===========================================================
	// ===========================================================
    // Fields
    // ===========================================================	
	/**
	 * The X position of this camera's display.  Zoom does NOT affect this number.
	 * Measured in pixels from the left side of the flash window.
	 */
	public float x;
	/**
	 * The Y position of this camera's display. 
	 */
	public float y;
	/**
	 * How wide the camera display is, in game pixels.
	 */
	public int width;
	/**
	 * How tall the camera display is, in game pixels.
	 */
	public int height;
	
	/**
	 * The natural background color of the camera.
	 */
	public int bgColor;
	
	public GroupLayer screen;
	
	/**
	 * The edges of the camera's range, i.e. where to stop scrolling.
	 * Measured in game pixels and world coordinates.
	 */
	//public VipRect bounds;
	
	/**
	 * Stores the basic parallax scrolling values.
	 */
	public VipPoint scroll;
	
	/**
	 * Tells the camera to follow this <code>VipObject</code> object around.
	 */
	public VipObject target;
	// ===========================================================
    // Constructors
    // ===========================================================	
	/**
	 * Instantiates a new camera at the specified location, with the specified size and zoom level.
	 * 
	 * @param X			X location of the camera's display in pixels. Uses native, 1:1 resolution, ignores zoom.
	 * @param Y			Y location of the camera's display in pixels. Uses native, 1:1 resolution, ignores zoom.
	 * @param Width		The width of the camera display in pixels.
	 * @param Height	The height of the camera display in pixels.
	 * @param Zoom		The initial zoom level of the camera.  A zoom level of 2 will make all pixels display at 2x resolution.
	 */
	public VipCamera(float X,float Y,int Width,int Height,float Zoom)
	{
		super();
		x = X;
		y = Y;
		width = Width;
		height = Height;
		bgColor = 000;
		screen =  graphics().createGroupLayer();
		scroll = new VipPoint();
		SurfaceLayer back = graphics().createSurfaceLayer(width, height);
		
		log().info("init camera");
		Surface surf = back.surface();
		surf.clear();
    	surf.setFillColor(0xff444444);
    	surf.fillRect(X, Y, width, height);
    	
    	screen.add(back);
	}
	//============ Parameterless default constructor
	public VipCamera(float X,float Y,int Width,int Height)
	{
		this(X, Y, Width, Height, 1);
	}
	
	public VipCamera() {
		this(0, 0, graphics().screenWidth(), graphics().screenHeight(), 1);
	}
	// ===========================================================
    // Methods
    // ===========================================================
	@Override
	public void destroy() {
		screen.clear();
		screen = null;
		scroll = null;		
	}
	
	/**
	 * Updates the camera scroll as well as special effects like screen-shake or fades.
	 */
	@Override
	public void update() {
		//Either follow the object closely, 
		//or doublecheck our deadzone and update accordingly.
		if(target != null)
		{
			focusOn(target.getLocation());			
		}
			
		//Make sure we didn't go outside the camera's bounds
		if(scroll.x < 0)
			scroll.x = 0;
		if(scroll.x > VipG.WorldWidth - VipG.StageWidth)
			scroll.x = VipG.WorldWidth - VipG.StageWidth;
		if(scroll.y < 0)
			scroll.y = 0;
		if(scroll.y > VipG.WorldHeight - VipG.StageHeight)
			scroll.y = VipG.WorldHeight - VipG.StageHeight;
	}
	
	@Override
	public void postUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Move the camera focus to this location instantly.
	 * 
	 * @param	Point		Where you want the camera to focus.
	 */
	public void focusOn(VipPoint Point)
	{
		scroll.make(Point.x - width*0.5f,Point.y - height*0.5f);
	}
	public void reset() {
		screen.clear();		
		SurfaceLayer back = graphics().createSurfaceLayer(width, height);		
	
		Surface surf = back.surface();
		surf.clear();
    	surf.setFillColor(0xff444444);
    	surf.fillRect(x, y, width, height);
    	
    	screen.add(back);
		scroll = new VipPoint();
	}	
}
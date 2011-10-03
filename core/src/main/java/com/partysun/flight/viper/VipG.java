package com.partysun.flight.viper;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;

public class VipG {
	
	// ===========================================================
    // Constants
    // ===========================================================
	
	static public String LIBRARY_NAME = "Viper";
	static public int LIBRARY_MAJOR_VERSION = 0;
	static public int LIBRARY_MINOR_VERSION = 10;
	static protected boolean _pause;
//	static protected VipGame _game;
	static public boolean debug;
	
	/**
	 * Some handy color presets.  Less glaring than pure RGB full values.
	 * Primarily used in the visual debugger mode for bounding box displays.
	 * Red is used to indicate an active, movable, solid object.
	 */
	static public int RED = 0xffff0012;
	/**
	 * Green is used to indicate solid but immovable objects.
	 */
	static public int GREEN = 0xff00f225;
	/**
	 * Blue is used to indicate non-solid objects.
	 */
	static public int BLUE = 0xff0090e9;
	/**
	 * Pink is used to indicate objects that are only partially solid, like one-way platforms.
	 */
	static public int PINK = 0xfff01eff;
	/**
	 * White... for white stuff.
	 */
	static public int WHITE = 0xffffffff;
	/**
	 * And black too.
	 */
	static public int BLACK = 0xff000000;
	
	/**
	 * Represents the amount of time in seconds that passed since last frame
	 */
	static public double elapsed;
	
	/**
	 * Whether to show visual debug displays or not.
	 * Default = false.
	 */
	static public boolean visualDebug = false;
	
	/**
	 * An array of <code>VipCamera</code> objects that are used to draw stuff.
	 * By default Viper creates one camera the size of the screen.
	 */
	static public ArrayList<VipCamera> cameras;
	
	/**
	 * By default this just refers to the first entry in the cameras array
	 * declared above, but you can do what you like with it.
	 */
	static public VipCamera camera;
	
	public static int WorldWidth = 640;
	
	public static int WorldHeight = 480;
	
	public static int StageWidth = 320;
	
	public static int StageHeight = 240;
	
	public static VipGame Game;

	public static float globalSeed;
	
	public static void switchState(VipState state) {
		if (Game != null) {
			camera.destroy();
		    camera = new VipCamera();
		    graphics().rootLayer().clear();
		    graphics().rootLayer().add(camera.screen);
			Game.setState(state);			
		}		
	}
	
	/**
	 * Generates a random number.  Deterministic, meaning safe
	 * to use if you want to record replays in random environments.
	 * 
	 * @return	A <code>Number</code> between 0 and 1.
	 */
	static public float random()
	{
		globalSeed = (float) Math.random();
		return globalSeed = VipU.srand(globalSeed);
	}
}

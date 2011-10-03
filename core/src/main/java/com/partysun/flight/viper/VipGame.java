package com.partysun.flight.viper;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;
import static playn.core.PlayN.log;
import static playn.core.PlayN.pointer;

import playn.core.Game;
import playn.core.PlayN;

/**
* VipGame is the heart of all Viper games, and contains a bunch of basic game loops and things.
* It is a long and sloppy file that you shouldn't have to worry about too much!
* It is basically only used to create your game object in the first place,
* after that VipG and VipState have all the useful stuff you actually need.
* 
* @author Yura - (@Partysun - yura.zatsepin@gmail.com)	
*/
public class VipGame implements Game {

	// ===========================================================
    // Constants
    // ===========================================================
	
	// ===========================================================
    // Fields
    // ===========================================================
	
	/**
	 * How frequently the game should update (default is 60 times per second).
	 */
	private int gameFramerate = 25;
	/**
	 *	Whether the game object's basic initialization has finished yet.
	 */
	protected boolean _created;
	
	/**
	 * Total number of milliseconds elapsed since game start.
	 */
	protected long _total;
	
	/**
	 * Total number of milliseconds elapsed since last update loop.
	 * Counts down as we step through the game loop.
	 */	
	protected long _accumulator;
	
	/**
	 * Whether the Flash player lost focus.
	 */
	protected boolean _lostFocus;
	/**
	 * Milliseconds of time per step of the game loop.  FlashEvent.g. 60 fps = 16ms.
	 */
	
	private double prevTime = 0;
	
	private int _stageSizeX = 640;
	
	private int _stageSizeY = 480;
	
	private int _gameSizeX = 640;
	
	private int _gameSizeY = 640;

	private VipState currentState;
	
	// ===========================================================
    // Constructors
    // ===========================================================
	/**
	 * Instantiate a new game object.
	 * 
	 * @param	GameSizeX		The width of your game in game pixels, not necessarily final display pixels (see Zoom).
	 * @param	GameSizeY		The height of your game in game pixels, not necessarily final display pixels (see Zoom).
	 * @param	InitialState	The class name of the state you want to create and switch to first (e.g. MenuState).
	 * @param	Zoom			The default level of zoom for the game's cameras (e.g. 2 = all pixels are now drawn at 2x).  Default = 1.
	 * @param	GameFramerate	How frequently the game should update (default is 60 times per second).
	 */
	public VipGame(int StageSizeX, int StageSizeY, int GameSizeX, int GameSizeY, VipState InitialState, int GameFramerate)
	{
		super();
		
		_stageSizeX = StageSizeX;
		_stageSizeY = StageSizeY;
		_gameSizeX = GameSizeX;
		_gameSizeY = GameSizeY;
		gameFramerate = GameFramerate;
		currentState = InitialState;
		
		prevTime = PlayN.currentTime();		
	}
	
	/**
	 * The StageSize has default value : 640;480.
	 * Framerate has default value : 25;
	 * @param GameSizeX
	 * @param GameSizeY
	 * @param InitialState
	 */
	public VipGame(int GameSizeX, int GameSizeY, VipState InitialState)
	{
		this(640, 480, GameSizeX, GameSizeY, InitialState, 25);
	}

	// ===========================================================
    // Getter & Setter
    // ===========================================================
	
	public int getGameFramerate() {
		return gameFramerate;
	}

	public void setGameFramerate(int gameFramerate) {
		this.gameFramerate = (int) 1000/gameFramerate;
	}
	
	// ===========================================================
    // Methods
    // ===========================================================

	public int updateRate() {
		return gameFramerate;
	}

	@Override
	public void init() {		
		VipCamera camera = new VipCamera();
		VipG.camera = camera;	
		VipG.Game = this;
		graphics().rootLayer().add(camera.screen);
		setStageSize(_stageSizeX, _stageSizeY);
		setWorldSize(_gameSizeX, _gameSizeY);
		setState(currentState);
		log().info("init Game");
	}

	public void setStageSize(int StageWidth, int StageHeight) {
		VipG.StageWidth = StageWidth;
		VipG.StageHeight = StageHeight;
		graphics().setSize(StageWidth ,StageHeight);
	}
	
	public void setWorldSize(int WorldWidth, int WorldHeight) {
		VipG.WorldWidth = WorldWidth;
		VipG.WorldHeight = WorldHeight;		
	}

	@Override
	public void update(float delta) {
		VipG.camera.update();	
		if (currentState != null)
			currentState.update();
	}

	@Override
	public void paint(float alpha) {
		double curtime = PlayN.currentTime();
		VipG.elapsed = curtime - prevTime;
		prevTime = curtime;		
		if (currentState != null)
			currentState.draw(alpha);
	}	
	
	/**
	 * Create new state and change. 
	 * @param state - Game State (Scene)
	 */
	public void setState(VipState state) {		
		if (currentState != null) {
			pointer().setListener(null);
			keyboard().setListener(null);
			currentState.destroy();			
		}
		currentState = state;
		currentState.create();
	}
}

package com.partysun.flight.viper;

/**
 * This is the base class for most of the display objects (<code>VipSprite</code>, <code>VipText</code>, etc).
 * It includes some basic attributes about game objects, basic state information, sizes, scrolling, and basic physics and motion.
 * 
 * @author	Yura - (@Partysun - yura.zatsepin@gmail.com)
 */
public class VipObject extends VipBasic {
	
	// ===========================================================
    // Constants
    // ===========================================================
	
	/**
	 * Generic value for "left" Used by <code>facing</code>, <code>allowCollisions</code>, and <code>touching</code>.
	 */
	static final public int LEFT	= 0x0001;
	/**
	 * Generic value for "right" Used by <code>facing</code>, <code>allowCollisions</code>, and <code>touching</code>.
	 */
	static final public int RIGHT	= 0x0010;
	/**
	 * Generic value for "up" Used by <code>facing</code>, <code>allowCollisions</code>, and <code>touching</code>.
	 */
	static final public int UP		= 0x0100;
	/**
	 * Generic value for "down" Used by <code>facing</code>, <code>allowCollisions</code>, and <code>touching</code>.
	 */
	static final public int DOWN	= 0x1000;
	
	/**
	 * Special-case constant meaning no collisions, used mainly by <code>allowCollisions</code> and <code>touching</code>.
	 */
	static final public int NONE	= 0;
	/**
	 * Special-case constant meaning up, used mainly by <code>allowCollisions</code> and <code>touching</code>.
	 */
	static final public int CEILING = UP;
	/**
	 * Special-case constant meaning down, used mainly by <code>allowCollisions</code> and <code>touching</code>.
	 */
	static final public int FLOOR	= DOWN;
	/**
	 * Special-case constant meaning only the left and right sides, used mainly by <code>allowCollisions</code> and <code>touching</code>.
	 */
	static final public int WALL = LEFT | RIGHT;
	/**
	 * Special-case constant meaning any direction, used mainly by <code>allowCollisions</code> and <code>touching</code>.
	 */
	static final public int ANY	= LEFT | RIGHT | UP | DOWN;
	
	/**
	 * Handy constant used during collision resolution (see <code>separateX()</code> and <code>separateY()</code>).
	 */
	static final public float OVERLAP_BIAS = 4;
	
	// ===========================================================
    // Fields
    // ===========================================================
	/**
	 * X position of middle of this object in world space.
	 */
	public float x;
	/**
	 * Y position of middle of this object in world space.
	 */
	public float y;
	/**
	 * The width of this object.
	 */
	public float width;
	/**
	 * The height of this object.
	 */
	public float height;

	/**
	 * Whether an object will move/alter position after a collision.
	 */
	public boolean immovable;
	
	/**
	 * The basic speed of this object.
	 */
	public VipPoint velocity;
	/**
	 * The virtual mass of the object. Default value is 1.
	 * Currently only used with <code>elasticity</code> during collision resolution.
	 * Change at your own risk; effects seem crazy unpredictable so far!
	 */
	public float mass;
	/**
	 * The bounciness of this object.  Only affects collisions.  Default value is 0, or "not bouncy at all."
	 */
	public float elasticity;
	/**
	 * How fast the speed of this object is changing.
	 * Useful for smooth movement and gravity.
	 */
	public VipPoint acceleration;
	/**
	 * This isn't drag exactly, more like deceleration that is only applied
	 * when acceleration is not affecting the sprite.
	 */
	public VipPoint drag;
	/**
	 * If you are using <code>acceleration</code>, you can use <code>maxVelocity</code> with it
	 * to cap the speed automatically (very useful!).
	 */
	public VipPoint maxVelocity;
	/**
	 * Set the angle of a sprite to rotate it.
	 * WARNING: rotating sprites decreases rendering
	 * performance for this sprite by a factor of 10x!
	 */
	public float angle;
	/**
	 * This is how fast you want this sprite to spin.
	 */
	public float angularVelocity;
	/**
	 * How fast the spin speed should change.
	 */
	public float angularAcceleration;
	/**
	 * Like <code>drag</code> but for spinning.
	 */
	public float angularDrag;
	/**
	 * Use in conjunction with <code>angularAcceleration</code> for fluid spin speed control.
	 */
	public float maxAngular;
	/**
	 * Should always represent (0,0) - useful for different things, for avoiding unnecessary <code>new</code> calls.
	 */
	static protected VipPoint _pZero = new VipPoint();
	
	/**
	 * A point that can store numbers from 0 to 1 (for X and Y independently)
	 * that governs how much this object is affected by the camera subsystem.
	 * 0 means it never moves, like a HUD element or far background graphic.
	 * 1 means it scrolls along a the same speed as the foreground layer.
	 * scrollFactor is initialized as (1,1) by default.
	 */
	public VipPoint scrollFactor;
	/**
	 * Internal helper used for retro-style flickering.
	 */
	protected boolean _flicker;
	/**
	 * Internal helper used for retro-style flickering.
	 */
	protected double _flickerTimer;
	/**
	 * Handy for storing health percentage or armor points or whatever.
	 */
	public float health;
	/**
	 * This is just a pre-allocated x-y point container to be used however you like
	 */
	protected VipPoint _point;
	/**
	 * This is just a pre-allocated rectangle container to be used however you like
	 */
	//	TODO: Добавить VipRect
	//	protected var _rect:FlxRect;
	/**
	 * Set this to false if you want to skip the automatic motion/movement stuff (see <code>updateMotion()</code>).
	 * FlxObject and FlxSprite default to true.
	 * FlxText, FlxTileblock, FlxTilemap and FlxSound default to false.
	 */
	public boolean moves;
	/**
	 * Bit field of flags (use with UP, DOWN, LEFT, RIGHT, etc) indicating surface contacts.
	 * Use bitwise operators to check the values stored here, or use touching(), justStartedTouching(), etc.
	 * You can even use them broadly as boolean values if you're feeling saucy!
	 */
	public int touching;
	/**
	 * Bit field of flags (use with UP, DOWN, LEFT, RIGHT, etc) indicating surface contacts from the previous game loop step.
	 * Use bitwise operators to check the values stored here, or use touching(), justStartedTouching(), etc.
	 * You can even use them broadly as boolean values if you're feeling saucy!
	 */
	public int wasTouching;
	/**
	 * Bit field of flags (use with UP, DOWN, LEFT, RIGHT, etc) indicating collision directions.
	 * Use bitwise operators to check the values stored here.
	 * Useful for things like one-way platforms (e.g. allowCollisions = UP;)
	 * The accessor "solid" just flips this variable between NONE and ANY.
	 */
	public int allowCollisions;
	
	/**
	 * Important variable for collision processing.
	 * By default this value is set automatically during <code>preUpdate()</code>.
	 */
	public VipPoint last;	
	
	public boolean isManualPosChange = false;
	
	// ===========================================================
    // Constructors
    // ===========================================================
	/**
	 * Instantiates a <code>FlxObject</code>.
	 * 
	 * @param	X		The X-coordinate of the point in space.
	 * @param	Y		The Y-coordinate of the point in space.
	 * @param	Width	Desired width of the rectangle.
	 * @param	Height	Desired height of the rectangle.  
	 */
	public VipObject(float X,float Y,float Width,float Height)
	{
		super();
		x = X;
		y = Y;
		last = new VipPoint(x,y);
		width = Width;
		height = Height;
		mass = 1.0f;
		elasticity = 0.0f;

		immovable = false;
		moves = true;
		
		touching = NONE;
		wasTouching = NONE;
		allowCollisions = ANY;
		
		velocity = new VipPoint();
		acceleration = new VipPoint();
		drag = new VipPoint();
		maxVelocity = new VipPoint(10000,10000);
		
		angle = 0;
		angularVelocity = 0;
		angularAcceleration = 0;
		angularDrag = 0;
		maxAngular = 10000;
		
		scrollFactor = new VipPoint(1.0f,1.0f);
		_flicker = false;
		_flickerTimer = 0;
		
		_point = new VipPoint();		
		//TODO: добавить когда добавиться RECT
		//_rect = new VipRect();	
	}
	
	//============ Parameterless default constructor
	/**
	 * @default float X = 0,float Y = 0,float Width = 0,float Height = 0
	 */
	public VipObject()
	{
		this(0,0,0,0);
	}
	
	// ===========================================================
    // Methods
    // ===========================================================
	
	/**
	 * Override this function to null out variables or
	 * manually call destroy() on class members if necessary.
	 * Don't forget to call super.destroy()!
	 */
	@Override
	public void destroy() {		
		velocity = null;
		acceleration = null;
		drag = null;
		maxVelocity = null;
		scrollFactor = null;
		_point = null;
		//TODO: Добавить когда RECT
		//		_rect = null;
		last = null;
		cameras = null;		
	}	
	
	/**
	 * Pre-update is called right before <code>update()</code> on each object in the game loop.
	 * In <code>VipObject</code> it controls the flicker timer,
	 * tracking the last coordinates for collision purposes.
	 */
	@Override
	public void preUpdate()
	{
		//TODO: isManual Position Change need for separate objects when they move by -/+ x pos, but not with velocity.
		// it's very bad( i need found another version of this algorithm.
		if (isManualPosChange) {
			last.x = x;
			last.y = y;
		}
		isManualPosChange = !isManualPosChange;
		_ACTIVECOUNT++;
		
		if(_flickerTimer != 0)
		{
			if(_flickerTimer > 0)
			{
				_flickerTimer = _flickerTimer - VipG.elapsed;
				if(_flickerTimer <= 0)
				{
					_flickerTimer = 0;
					_flicker = false;
				}
			}
		}
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Post-update is called right after <code>update()</code> on each object in the game loop.
	 * In <code>VipObject</code> this function handles integrating the objects motion
	 * based on the velocity and acceleration settings, and tracking/clearing the <code>touching</code> flags.
	 */
	@Override
	public void postUpdate() {
		if(moves)
			updateMotion();
		
		wasTouching = touching;
		touching = NONE;
	}
		
	/**
	 * Internal function for updating the position and speed of this object.
	 * Useful for cases when you need to update this but are buried down in too many supers.
	 * Does a slightly fancier-than-normal integration to help with higher fidelity framerate-independenct motion.
	 */
	protected void updateMotion()
	{
		float delta;
		float velocityDelta;

		velocityDelta = (VipU.computeVelocity(angularVelocity,angularAcceleration,angularDrag,maxAngular) - angularVelocity)/2;
		angularVelocity += velocityDelta; 
		angle += angularVelocity*VipG.elapsed;
		angularVelocity += velocityDelta;
		
		velocityDelta = (VipU.computeVelocity(velocity.x,acceleration.x,drag.x,maxVelocity.x) - velocity.x)/2;
		velocity.x += velocityDelta;
		delta = (float) (velocity.x*VipG.elapsed);
		velocity.x += velocityDelta;
		x += delta;
		
		velocityDelta = (VipU.computeVelocity(velocity.y,acceleration.y,drag.y,maxVelocity.y) - velocity.y)/2;
		velocity.y += velocityDelta;
		delta = (float) (velocity.y*VipG.elapsed);
		velocity.y += velocityDelta;
		y += delta;
	}
			
	/**
	 * Rarely called, and in this case just increments the visible objects count and calls <code>drawDebug()</code> if necessary.
	 */
	@Override
	public void draw(float alpha)
	{
		super.draw(alpha);
		if (VipG.visualDebug && onScreen(VipG.camera) && !ignoreDrawDebug) {
			drawDebug();
		}
	}		
			
	/**
	 * Override this function to draw custom "debug mode" graphics to the
	 * specified camera while the debugger's visual mode is toggled on.
	 * 
	 * @param	Camera	Which camera to draw the debug visuals to.
	 */
	public void drawDebug(VipCamera Camera)
	{	
	}	
	
	/**
	 * Override this function to draw custom "debug mode" graphics to the
	 * specified camera while the debugger's visual mode is toggled on.
	 * 
	 * @param	Camera	Which camera to draw the debug visuals to.
	 */
	@Override
	public void drawDebug()
	{		
		drawDebug(VipG.camera);
	}
			
	/**
	 * Handy function for reviving game objects.
	 * Resets their existence flags and position.
	 * 
	 * @param	X	The new X position of this object.
	 * @param	Y	The new Y position of this object.
	 */
	public void reset(float X,float Y)
	{
		revive();
		touching = NONE;
		wasTouching = NONE;
		x = X;
		y = Y;
		last.x = x;
		last.y = y;
		velocity.x = 0;
		velocity.y = 0;
	}	
	
	/**
	 * Tells this object to flicker, retro-style.
	 * Pass a negative value to flicker forever.
	 * 
	 * @param	Duration	How many seconds to flicker for.
	 */
	public void flicker(float Duration)
	{
		_flickerTimer = Duration;
		if(_flickerTimer == 0)
			_flicker = false;
	}
	
	public void flicker()
	{
		flicker(1);
	}
	
	/**
	 * Check to see if the object is still flickering.
	 * 
	 * @return	Whether the object is flickering or not.
	 */
	public boolean flickering()
	{
		return _flickerTimer != 0;
	}

	/**
	 * Location - x,y (Centre of the object)
	 * @return position of this object
	 */
	public VipPoint getLocation() {
		return new VipPoint(this.x, this.y);
	}
	
	/**
	 * Retrieve the left top of this object in world coordinates.
	 * 
	 * @Point	Allows you to pass in an existing <code>VipPoint</code> object if you're so inclined.  Otherwise a new one is created.
	 * 
	 * @return	A <code>VipPoint</code> object containing the midpoint of this object in world coordinates.
	 */
	public VipPoint getLeftTopPoint()
	{		
		VipPoint Point = new VipPoint();
		Point.x = this.x - width * 0.5f;
		Point.y = this.y - height * 0.5f;
		return Point;
	}
	
	/**
	 * Call this function to figure out the on-screen position of the object.
	 * 
	 * @param	Camera		Specify which game camera you want.  If null getScreenXY() will just grab the first global camera.
	 * @param	Point		Takes a <code>FlxPoint</code> object and assigns the post-scrolled X and Y values of this object to it.
	 * 
	 * @return	The <code>Point</code> you passed in, or a new <code>Point</code> if you didn't pass one, containing the screen X and Y position of this object.
	 */
	public VipPoint getScreenXY(VipPoint Point ,VipCamera Camera)
	{
		if(Point == null)
			Point = new VipPoint();
		if(Camera == null)
			Camera = VipG.camera;
		Point.x = x - (int) Camera.scroll.x*scrollFactor.x;
		Point.y = y - (int) Camera.scroll.y*scrollFactor.y;
		Point.x += (Point.x > 0)?0.0000001:-0.0000001;
		Point.y += (Point.y > 0)?0.0000001:-0.0000001;
		return Point;
	}
	
	public VipPoint getScreenXY(VipCamera Camera)
	{
		if(Camera == null)
			Camera = VipG.camera;
		VipPoint Point = new VipPoint();
		VipPoint leftTopCorner = getLeftTopPoint();
		Point.x = leftTopCorner.x - (int) Camera.scroll.x*scrollFactor.x;
		Point.y = leftTopCorner.y - (int) Camera.scroll.y*scrollFactor.y;
		return Point;
	}
 		
	/**
	 * Check and see if this object is currently on screen.
	 * 
	 * @param	Camera		Specify which game camera you want.  If null getScreenXY() will just grab the first global camera.
	 * 
	 * @return	Whether the object is on screen or not.
	 */
	public boolean onScreen(VipCamera Camera)
	{
		if(Camera == null)
			Camera = VipG.camera;
		VipPoint screenPos = getScreenXY(Camera);	
		return (screenPos.x + width > 0) && (screenPos.x < Camera.width) && (screenPos.y + height > 0) && (screenPos.y < Camera.height);
	}
	
	/**
	 * Checks to see if some <code>VipObject</code> overlaps this <code>VipObject</code> object in world space.
	 * 
	 * @param	Object			The object being tested.
	 * @param	InScreenSpace	Whether to take scroll factors into account when checking for overlap.
	 * @param	Camera			Specify which game camera you want.  If null getScreenXY() will just grab the first global camera.
	 * 
	 * @return	Whether or not the two objects overlap.
	 */
	public boolean overlaps(VipObject Object, boolean InScreenSpace, VipCamera Camera)
	{
		if(!InScreenSpace)
			return	(Object.x + Object.width > x) && (Object.x < x + width) &&
					(Object.y + Object.height > y) && (Object.y < y + height);

		if(Camera == null)
			Camera = VipG.camera;
		VipPoint objectScreenPos = Object.getScreenXY(Camera);
		VipPoint curPos = getScreenXY(Camera);		
		return	(objectScreenPos.x + Object.width > curPos.x) && (objectScreenPos.x < curPos.x + width) &&
				(objectScreenPos.y + Object.height > curPos.y) && (objectScreenPos.y < curPos.y + height);
	}	
	
	public boolean overlaps(VipObject Object)
	{		
		return	(Object.x + Object.width > x) && (Object.x < x + width) &&
				(Object.y + Object.height > y) && (Object.y < y + height);
	}
	
	/**
	 * Reduces the "health" variable of this sprite by the amount specified in Damage.
	 * Calls kill() if health drops to or below zero.
	 * 
	 * @param	Damage	How much health to take away (use a negative number to give a health bonus).
	 */
	public void hurt(float Damage)
	{
		health = health - Damage;
		if(health <= 0)
			kill();
	}
	
	/**
	 * The main collision resolution function in flixel.
	 * 
	 * @param	Object1 	Any <code>VipObject</code>.
	 * @param	Object2		Any other <code>VipObject</code>.
	 * 
	 * @return	Whether the objects in fact touched and were separated.
	 */
	static public boolean separate(VipObject Object1, VipObject Object2)
	{
		boolean separatedX = separateX(Object1,Object2);
		boolean separatedY = separateY(Object1,Object2);
		return separatedX || separatedY;
	}
	
	
	/**
	 * The X-axis component of the object separation process.
	 * 
	 * @param	Object1 	Any <code>VipObject</code>.
	 * @param	Object2		Any other <code>VipObject</code>.
	 * 
	 * @return	Whether the objects in fact touched and were separated along the X axis.
	 */
	static public boolean separateX(VipObject Object1, VipObject Object2)
	{
		//can't separate two immovable objects
		boolean obj1immovable = Object1.immovable;
		boolean obj2immovable = Object2.immovable;
		if(obj1immovable && obj2immovable)
			return false;
		
		//First, get the two object deltas
		float overlap = 0;
		float obj1delta = Object1.x - Object1.last.x;
		float obj2delta = Object2.x - Object2.last.x;		
		if(obj1delta != obj2delta)
		{
			//Check if the X hulls actually overlap
			float obj1deltaAbs = (obj1delta > 0)?obj1delta:-obj1delta;
			float obj2deltaAbs = (obj2delta > 0)?obj2delta:-obj2delta;
			VipRect obj1rect = new VipRect(Object1.x-((obj1delta > 0)?obj1delta:0),Object1.last.y,Object1.width+((obj1delta > 0)?obj1delta:-obj1delta),Object1.height);
			VipRect obj2rect = new VipRect(Object2.x-((obj2delta > 0)?obj2delta:0),Object2.last.y,Object2.width+((obj2delta > 0)?obj2delta:-obj2delta),Object2.height);
			if((obj1rect.x + obj1rect.width > obj2rect.x) && (obj1rect.x < obj2rect.x + obj2rect.width) && (obj1rect.y + obj1rect.height > obj2rect.y) && (obj1rect.y < obj2rect.y + obj2rect.height))
			{
				float maxOverlap = obj1deltaAbs + obj2deltaAbs + OVERLAP_BIAS;
				
				//If they did overlap (and can), figure out by how much and flip the corresponding flags
				if(obj1delta > obj2delta)
				{					
					overlap = Object1.x + Object1.width - Object2.x;					
					byte stat1 = (byte) Object1.allowCollisions;
					byte stat2 = (byte) RIGHT;
					int a = stat1 & stat2;
					if((overlap > maxOverlap) || ((Object1.allowCollisions & RIGHT) != RIGHT) || ((Object2.allowCollisions & LEFT) != LEFT ))
						overlap = 0;
					else
					{
						Object1.touching |= RIGHT;
						Object2.touching |= LEFT;
					}
				}
				else if(obj1delta < obj2delta)
				{					
					overlap = Object1.x - Object2.width - Object2.x;
					if((-overlap > maxOverlap) || (Object1.allowCollisions & LEFT) != LEFT || (Object2.allowCollisions & RIGHT) != RIGHT)
						overlap = 0;
					else
					{
						Object1.touching |= LEFT;
						Object2.touching |= RIGHT;
					}
				}
			}
		}
		
		//Then adjust their positions and velocities accordingly (if there was any overlap)
		if(overlap != 0)
		{
			float obj1v = Object1.velocity.x;
			float obj2v = Object2.velocity.x;
			
			if(!obj1immovable && !obj2immovable)
			{
				overlap *= 0.5;
				Object1.x = Object1.x - overlap;
				Object2.x += overlap;

				float obj1velocity = (float) (Math.sqrt((obj2v * obj2v * Object2.mass)/Object1.mass) * ((obj2v > 0)?1:-1));
				float obj2velocity = (float) (Math.sqrt((obj1v * obj1v * Object1.mass)/Object2.mass) * ((obj1v > 0)?1:-1));
				float average = (obj1velocity + obj2velocity)*0.5f;
				obj1velocity -= average;
				obj2velocity -= average;
				Object1.velocity.x = average + obj1velocity * Object1.elasticity;
				Object2.velocity.x = average + obj2velocity * Object2.elasticity;
			}
			else if(!obj1immovable)
			{
				Object1.x = Object1.x - overlap;
				Object1.velocity.x = obj2v - obj1v*Object1.elasticity;
			}
			else if(!obj2immovable)
			{
				Object2.x += overlap;
				Object2.velocity.x = obj1v - obj2v*Object2.elasticity;
			}
			return true;
		}
		else
			return false;
	}
	
	/**
	 * The Y-axis component of the object separation process.
	 * 
	 * @param	Object1 	Any <code>VipObject</code>.
	 * @param	Object2		Any other <code>VipObject</code>.
	 * 
	 * @return	Whether the objects in fact touched and were separated along the Y axis.
	 */
	static public boolean separateY(VipObject Object1, VipObject Object2)
	{
		//can't separate two immovable objects
		boolean obj1immovable = Object1.immovable;
		boolean obj2immovable = Object2.immovable;
		if(obj1immovable && obj2immovable)
			return false;
		
		//First, get the two object deltas
		float overlap = 0;
		float obj1delta = Object1.y - Object1.last.y;
		float obj2delta = Object2.y - Object2.last.y;
		if(obj1delta != obj2delta)
		{
			//Check if the Y hulls actually overlap
			float obj1deltaAbs = (obj1delta > 0)?obj1delta:-obj1delta;
			float obj2deltaAbs = (obj2delta > 0)?obj2delta:-obj2delta;
			VipRect obj1rect = new VipRect(Object1.x,Object1.y-((obj1delta > 0)?obj1delta:0),Object1.width,Object1.height+obj1deltaAbs);
			VipRect obj2rect = new VipRect(Object2.x,Object2.y-((obj2delta > 0)?obj2delta:0),Object2.width,Object2.height+obj2deltaAbs);
			if((obj1rect.x + obj1rect.width > obj2rect.x) && (obj1rect.x < obj2rect.x + obj2rect.width) && (obj1rect.y + obj1rect.height > obj2rect.y) && (obj1rect.y < obj2rect.y + obj2rect.height))
			{
				float maxOverlap = obj1deltaAbs + obj2deltaAbs + OVERLAP_BIAS;
				
				//If they did overlap (and can), figure out by how much and flip the corresponding flags
				if(obj1delta > obj2delta)
				{
					overlap = Object1.y + Object1.height - Object2.y;
					if((overlap > maxOverlap) || (Object1.allowCollisions & DOWN) != DOWN || (Object2.allowCollisions & UP) != UP)
						overlap = 0;
					else
					{
						Object1.touching |= DOWN;
						Object2.touching |= UP;
					}
				}
				else if(obj1delta < obj2delta)
				{
					overlap = Object1.y - Object2.height - Object2.y;
					if((-overlap > maxOverlap) || (Object1.allowCollisions & UP) != UP || (Object2.allowCollisions & DOWN) != DOWN)
						overlap = 0;
					else
					{
						Object1.touching |= UP;
						Object2.touching |= DOWN;
					}
				}
			}
		}
		
		//Then adjust their positions and velocities accordingly (if there was any overlap)
		if(overlap != 0)
		{
			float obj1v = Object1.velocity.y;
			float obj2v = Object2.velocity.y;
			
			if(!obj1immovable && !obj2immovable)
			{
				overlap *= 0.5;
				Object1.y = Object1.y - overlap;
				Object2.y += overlap;

				float obj1velocity = (float) (Math.sqrt((obj2v * obj2v * Object2.mass)/Object1.mass) * ((obj2v > 0)?1:-1));
				float obj2velocity = (float) (Math.sqrt((obj1v * obj1v * Object1.mass)/Object2.mass) * ((obj1v > 0)?1:-1));
				float average = (obj1velocity + obj2velocity)*0.5f;
				obj1velocity -= average;
				obj2velocity -= average;
				Object1.velocity.y = average + obj1velocity * Object1.elasticity;
				Object2.velocity.y = average + obj2velocity * Object2.elasticity;
			}
			else if(!obj1immovable)
			{
				Object1.y = Object1.y - overlap;
				Object1.velocity.y = obj2v - obj1v*Object1.elasticity;
				//This is special case code that handles cases like horizontal moving platforms you can ride
				if(Object2.active && Object2.moves && (obj1delta > obj2delta))
					Object1.x += Object2.x - Object2.last.x;
			}
			else if(!obj2immovable)
			{
				Object2.y += overlap;
				Object2.velocity.y = obj1v - obj2v*Object2.elasticity;
				//This is special case code that handles cases like horizontal moving platforms you can ride
				if(Object1.active && Object1.moves && (obj1delta < obj2delta))
					Object2.x += Object1.x - Object1.last.x;
			}
			return true;
		}
		else
			return false;
	}

}
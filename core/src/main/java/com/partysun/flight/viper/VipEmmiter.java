package com.partysun.flight.viper;

/**
 * <code>VipEmitter</code> is a lightweight particle emitter.
 * It can be used for one-time explosions or for
 * continuous fx like rain and fire.  
 * 
 * @author Yura - (@Partysun - yura.zatsepin@gmail.com) 
 */
public class VipEmmiter extends VipGroup {
	// ===========================================================
    // Fields
    // ===========================================================
	/**
	 * The X position of the top left corner of the emitter in world space.
	 */
	public float x;
	/**
	 * The Y position of the top left corner of emitter in world space.
	 */
	public float y;
	/**
	 * The width of the emitter.  Particles can be randomly generated from anywhere within this box.
	 */
	public int width;
	/**
	 * The height of the emitter.  Particles can be randomly generated from anywhere within this box.
	 */
	public int height;
	
	/**
	 * The minimum possible velocity of a particle.
	 * The default value is (-100,-100).
	 */
	public VipPoint minParticleSpeed;
	/**
	 * The maximum possible velocity of a particle.
	 * The default value is (100,100).
	 */
	public VipPoint maxParticleSpeed;
	/**
	 * The X and Y drag component of particles launched from the emitter.
	 */
	public VipPoint particleDrag;
	/**
	 * The minimum possible angular velocity of a particle.  The default value is -360.
	 * NOTE: rotating particles are more expensive to draw than non-rotating ones!
	 */
	public float minRotation;
	/**
	 * The maximum possible angular velocity of a particle.  The default value is 360.
	 * NOTE: rotating particles are more expensive to draw than non-rotating ones!
	 */
	public float maxRotation;
	/**
	 * Sets the <code>acceleration.y</code> member of each particle to this value on launch.
	 */
	public float gravity;
	/**
	 * Determines whether the emitter is currently emitting particles.
	 * It is totally safe to directly toggle this.
	 */
	public boolean on;
	/**
	 * How often a particle is emitted (if emitter is started with Explode == false).
	 */
	public float frequency;
	/**
	 * How long each particle lives once it is emitted.
	 * Set lifespan to 'zero' for particles to live forever.
	 */
	public float lifespan;
	/**
	 * How much each particle should bounce.  1 = full bounce, 0 = no bounce.
	 */
	public float bounce;	
	/**
	 * Internal helper for deciding how many particles to launch.
	 */
	protected int _quantity;
	/**
	 * Internal helper for the style of particle emission (all at once, or one at a time).
	 */
	protected boolean _explode;
	/**
	 * Internal helper for deciding when to launch particles or kill them.
	 */
	protected float _timer;
	/**
	 * Internal counter for figuring out how many particles to launch.
	 */
	protected int _counter;
	/**
	 * Internal point object, handy for reusing for memory mgmt purposes.
	 */
	protected VipPoint _point;
	// ===========================================================
    // Constructors
    // ===========================================================
	/**
	 * Creates a new <code>VipEmitter</code> object at a specific position.
	 * Does NOT automatically generate or attach particles!
	 * 
	 * @param	X		The X position of the emitter.
	 * @param	Y		The Y position of the emitter.
	 * @param	Size	Optional, specifies a maximum capacity for this emitter.
	 */
	public VipEmmiter(float X, float Y, int Size)
	{
		super(Size);
		x = X;
		y = Y;
		width = 0;
		height = 0;
		minParticleSpeed = new VipPoint(-0.02f,-0.02f);
		maxParticleSpeed = new VipPoint(0.09f,0.09f);
		minRotation = 0.0f;
		maxRotation = 0.2f;
		gravity = 0;		
		particleDrag = new VipPoint();
		frequency = 100;
		lifespan = 3;
		bounce = 0;
		_quantity = 0;
		_counter = 0;
		_explode = true;
		on = false;
		_point = new VipPoint();
	}
	
	public VipEmmiter()
	{
		this(0, 0, 0);
	}
	
	// ===========================================================
    // Methods
    // ===========================================================
	/**
	 * Clean up memory.
	 */
	@Override
	public void destroy()
	{
		super.destroy();
		minParticleSpeed = null;
		maxParticleSpeed = null;
		particleDrag = null;	
		_point = null;	
	}
	
	/**
	 * This function generates a new array of particle sprites to attach to the emitter.
	 * 
	 * @param	Graphics		If you opted to not pre-configure an array of VipSprite objects, you can simply pass in a particle image or sprite sheet.
	 * @param	Quantity		The number of particles to generate when using the "create from image" option.
	 * @return	This VipEmitter instance (nice for chaining stuff together, if you're into that).
	 */
	public VipEmmiter makeParticles(String Graphics, int Quantity , int Width, int Height, int Color)
	{
		VipParticle particle;
		int i = 0;
		while(i < Quantity)
		{			
			if (Graphics != null)
				particle = new VipParticle(Graphics);
			else {
				particle = new VipParticle();
				particle.makeGraphic(Width, Height, Color);
			}
			particle.x = x;
			particle.y = y;
			particle.allowCollisions = VipSprite.NONE;
			particle.exists = false;
			add(particle);
			i++;
		}
		return this;
	}
	
	public VipEmmiter makeParticles(String Graphics, int Quantity)
	{
		return makeParticles(Graphics, Quantity, 0, 0, 0);
	}
	
	public VipEmmiter makeParticles(int Quantity ,int Width, int Height, int Color)
	{
		return makeParticles(null, Quantity, Width, Height, Color);
	}
	
	/**
	 * Called automatically by the game loop, decides when to launch particles and when to "die".
	 */
	@Override
	public void update()
	{		
		super.update();		
		if(on)
		{			
			if(_explode)
			{
				on = false;
				int i = 0;
				int l = _quantity;
				if((l <= 0) || (l > members.size()))
					l = members.size();
				while(i < l)
				{
					emitParticle();
					i++;
				}
				_quantity = 0;
			}
			else
			{					
				_timer += VipG.elapsed;
				while((frequency > 0) && (_timer > frequency) && on)
				{
					_timer -= frequency;					
					emitParticle();
					if((_quantity > 0) && (++_counter >= _quantity))
					{
						on = false;
						_quantity = 0;
					}
				}
			}
		}
		else 
			setVisible(false);		
	}
	
	/**
	 * This function can be used both internally and externally to emit the next particle.
	 */
	public void emitParticle()
	{		
		VipParticle particle = (VipParticle) recycle();		
		if (particle == null)
			return;
		particle.lifespan = lifespan;		
		particle.elasticity = bounce;	
		particle.reset(x + (float)Math.random()*width, y + (float)Math.random()*height);
		particle.setVisible(true);				
		
		if(minParticleSpeed.x != maxParticleSpeed.x)
			particle.velocity.x = minParticleSpeed.x + (float)Math.random()*(maxParticleSpeed.x-minParticleSpeed.x);
		else
			particle.velocity.x = minParticleSpeed.x;
		if(minParticleSpeed.y != maxParticleSpeed.y)
			particle.velocity.y = minParticleSpeed.y + (float)Math.random()*(maxParticleSpeed.y-minParticleSpeed.y);
		else
			particle.velocity.y = minParticleSpeed.y;
		particle.acceleration.y = gravity;		
		if(minRotation != maxRotation)
			particle.angularVelocity = minRotation + VipG.random()*(maxRotation-minRotation);
		else
			particle.angularVelocity = minRotation;
		if(particle.angularVelocity != 0)
			particle.angle = VipG.random()*360-180;
		
		particle.drag.x = particleDrag.x;
		particle.drag.y = particleDrag.y;
		particle.onEmit();
	}
	
	/**
	 * Call this function to turn off all the particles and the emitter.
	 */
	@Override
	public void kill()
	{
		on = false;
		super.kill();
	}
	
	/**
	 * Call this function to start emitting particles.
	 * 
	 * @param	Explode		Whether the particles should all burst out at once.
	 * @param	Lifespan	How long each particle lives once emitted. 0 = forever.
	 * @param	Frequency	Ignored if Explode is set to true. Frequency is how often to emit a particle. 0 = never emit, 0.1 = 1 particle every 0.1 seconds, 5 = 1 particle every 5 seconds.
	 * @param	Quantity	How many particles to launch. 0 = "all of the particles".
	 */
	public void start(boolean Explode, float Lifespan, float Frequency, int Quantity)
	{
		revive();
		setVisible(true);
		on = true;
		
		_explode = Explode;
		lifespan = Lifespan;
		frequency = Frequency;
		_quantity += Quantity;
		
		_counter = 0;
		_timer = 0;
	}
	
	/**
	 * A more compact way of setting the width and height of the emitter.
	 * 
	 * @param	Width	The desired width of the emitter (particles are spawned randomly within these dimensions).
	 * @param	Height	The desired height of the emitter.
	 */
	public void setSize(int Width, int Height)
	{
		width = Width;
		height = Height;
	}
	
	/**
	 * A more compact way of setting the X velocity range of the emitter.
	 * 
	 * @param	Min		The minimum value for this range.
	 * @param	Max		The maximum value for this range.
	 */
	public void setXSpeed(float Min, float Max)
	{
		minParticleSpeed.x = Min;
		maxParticleSpeed.x = Max;
	}
	
	/**
	 * A more compact way of setting the Y velocity range of the emitter.
	 * 
	 * @param	Min		The minimum value for this range.
	 * @param	Max		The maximum value for this range.
	 */
	public void setYSpeed(float Min, float Max)
	{
		minParticleSpeed.y = Min;
		maxParticleSpeed.y = Max;
	}
	
	/**
	 * A more compact way of setting the angular velocity constraints of the emitter.
	 * 
	 * @param	Min		The minimum value for this range.
	 * @param	Max		The maximum value for this range.
	 */
	public void setRotation(float Min, float Max)
	{
		minRotation = Min;
		maxRotation = Max;
	}
	
	
	/**
	 * Change the emitter's midpoint to match the midpoint of a <code>VipObject</code>.
	 * 
	 * @param	Object		The <code>FlxObject</code> that you want to sync up with.
	 */
	public void at(VipObject Object)
	{
		_point = Object.getLocation();
		x = _point.x - (width>>1);
		y = _point.y - (height>>1);
	}	 
}
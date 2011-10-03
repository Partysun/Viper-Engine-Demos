package com.partysun.flight.viper;

/**
 * This is a simple particle class that extends the default behavior
 * of <code>VipSprite</code> to have slightly more specialized behavior
 * common to many game scenarios.  You can override and extend this class
 * just like you would <code>VipSprite</code>. While <code>VipEmitter</code>
 * used to work with just any old sprite, it now requires a
 * <code>VipParticle</code> based class.
 * 
 * @author Yura - (@Partysun - yura.zatsepin@gmail.com) 
 */
public class VipParticle extends VipSprite{
	// ===========================================================
    // Constants
    // ===========================================================
	// ===========================================================
    // Fields
    // ===========================================================
	/**
	 * How long this particle lives before it disappears.
	 * NOTE: this is a maximum, not a minimum; the object
	 * could get recycled before its lifespan is up.
	 */
	public float lifespan;
	
	/**
	 * Determines how quickly the particles come to rest on the ground.
	 * Only used if the particle has gravity-like acceleration applied.
	 * @default 500
	 */
	public float friction;
	
	// ===========================================================
    // Constructors
    // ===========================================================
	/**
	 * Instantiate a new particle. 
	 */
	public VipParticle(String Graph)
	{
		super(0, 0, 1, 1, Graph);
		lifespan = 0;
		friction = 500;
	}
	
	public VipParticle()
	{
		super(0, 0, 1, 1, "");
		lifespan = 0;
		friction = 500;
	}
	// ===========================================================
    // Methods
    // ===========================================================
	
	/**
	 * The particle's main update logic.  Basically it checks to see if it should
	 * be dead yet, and then has some special bounce behavior if there is some gravity on it.
	 */
	@Override
	public void update()
	{
		super.update();
		//lifespan behavior
		if(lifespan <= 0)
			return;		
		lifespan -= VipG.elapsed;		
		if(lifespan <= 0 || !this.onScreen(null))
		{			
			setVisible(false);
			kill();
		}
		//simpler bounce/spin behavior for now
		if((touching & ANY) == ANY)
		{
			if(angularVelocity != 0)
				angularVelocity = -angularVelocity;
		}
		if(acceleration.y > 0) //special behavior for particles with gravity
		{
			if((touching & FLOOR) == FLOOR)
			{
				drag.x = friction;
				
				if((wasTouching & FLOOR) != FLOOR)
				{
					if(velocity.y < -elasticity*10)
					{
						if(angularVelocity != 0)
							angularVelocity *= -elasticity;
					}
					else
					{
						velocity.y = 0;
						angularVelocity = 0;
					}
				}
			}
			else
				drag.x = 0;
		}
	}
	
	/**
	 * Triggered whenever this object is launched by a <code>VipEmitter</code>.
	 * You can override this to add custom behavior like a sound or AI or something.
	 */
	public void onEmit()
	{
	}
}
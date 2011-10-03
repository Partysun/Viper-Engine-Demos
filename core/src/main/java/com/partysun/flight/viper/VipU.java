package com.partysun.flight.viper;

public final class VipU {
	
	// ===========================================================
    // Methods
    // ===========================================================
	
	/**
	 * A tween-like function that takes a starting velocity
	 * and some other factors and returns an altered velocity.
	 * 
	 * @param	Velocity		Any component of velocity (e.g. 20).
	 * @param	Acceleration	Rate at which the velocity is changing.
	 * @param	Drag			Really kind of a deceleration, this is how much the velocity changes if Acceleration is not set.
	 * @param	Max				An absolute value cap for the velocity.
	 * 
	 * @return	The altered Velocity value.
	 */
	static public float computeVelocity(float Velocity,float Acceleration,float Drag,float Max)
	{
		if(Acceleration != 0)
			Velocity += Acceleration*VipG.elapsed;
		else if(Drag != 0)
		{
			float drag = (float) (Drag*VipG.elapsed);
			if(Velocity - drag > 0)
				Velocity = Velocity - drag;
			else if(Velocity + drag < 0)
				Velocity += drag;
			else
				Velocity = 0;
		}
		if((Velocity != 0) && (Max != 10000))
		{
			if(Velocity > Max)
				Velocity = Max;
			else if(Velocity < -Max)
				Velocity = -Max;
		}
		return Velocity;
	}
	
	/**
	 * A tween-like function that takes a starting velocity
	 * and some other factors and returns an altered velocity.
	 * 
	 * @param	Velocity		Any component of velocity (e.g. 20). 
	 * 
	 * @return	The altered Velocity value.
	 */
	static public float computeVelocity(float Velocity)
	{
		return computeVelocity(Velocity, 0, 0, 10000);
	}
	
	/**
	 * Generates a random number based on the seed provided.
	 * 
	 * @param	Seed	A number between 0 and 1, used to generate a predictable random number (very optional).
	 * 
	 * @return	A <code>Number</code> between 0 and 1.
	 */
	static public float srand(float Seed)
	{
		return ((69621 * (int)(Seed * 0x7FFFFFFF)) % 0x7FFFFFFF) / 0x7FFFFFFF;
	}
}

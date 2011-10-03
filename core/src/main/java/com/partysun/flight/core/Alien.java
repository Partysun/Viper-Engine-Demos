package com.partysun.flight.core;

import com.partysun.flight.viper.VipSprite;

public class Alien extends VipSprite{
	
	public Alien (float x, float y)
	{
		super(x, y, "images/Alien.png");
		width = 36;
		height = 32;
		velocity.x = -0.04f;
	}

	@Override
	public void update()
	{
		super.update();
		velocity.y = (float) (Math.cos(x / 0.2) * 0.05f);
		if (!this.onScreen(null))
		{			
			kill();
		}
	}	
	
	@Override
	public void reset(float X, float Y) {	
		super.reset(X, Y);
		velocity.x = -0.04f;
	}
}
package com.partysun.flight.core;

import com.partysun.flight.viper.VipSprite;

public class Bullet extends VipSprite{

	public Bullet(float f, float g) {
		super(f, g);
		makeGraphic(16, 4, 0xFF597137);
		velocity.x = 2.0f;
	}
	
	@Override
	public void update() {
		super.update();
		if (!this.onScreen(null))
		{
			kill();
		}
	}
	
	@Override
	public void reset(float X, float Y) {	
		super.reset(X, Y);
		velocity.x = 2.0f;
	}
}

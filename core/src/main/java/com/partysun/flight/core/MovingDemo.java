package com.partysun.flight.core;

import com.partysun.flight.viper.VipG;
import com.partysun.flight.viper.VipKeyboardStandart;
import com.partysun.flight.viper.VipPoint;
import com.partysun.flight.viper.VipSprite;
import com.partysun.flight.viper.VipState;

public class MovingDemo extends VipState{

	private VipSprite img;
	private VipSprite img2;
	private VipKeyboardStandart kListener;
	
	@Override
	public void create() {
		img = new VipSprite(20, 20, "images/house.png");
		add(img);		
		img.velocity.x = 0.2f;
		
		img2 = new VipSprite(270, 200, "images/house.png");
		add(img2);		
		img2.angularVelocity = 0.002f;
		
		kListener = new VipKeyboardStandart();		
		addKeybordControl(kListener);
	}

	@Override
	public void update() {	
		super.update();
		if (kListener.ESC)	{
			VipG.switchState(new LogoDemo());
		}
		if (img.x > 350)
			img.velocity =  new VipPoint(0, 0.2f);
		if (img.y > 250)
			img.velocity =  new VipPoint(- 0.2f, 0);
		if (img.x < 20)
			img.velocity = new VipPoint();
		
		if (img2.scale.x < 2)
			img2.scale.x += 0.002f;
		if (img2.scale.x > 2)
			img2.scale.x = 1.0f;		
	}
}

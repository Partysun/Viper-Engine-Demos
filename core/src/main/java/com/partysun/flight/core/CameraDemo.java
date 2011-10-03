package com.partysun.flight.core;

import com.partysun.flight.viper.VipG;
import com.partysun.flight.viper.VipKeyboardStandart;
import com.partysun.flight.viper.VipPoint;
import com.partysun.flight.viper.VipSprite;
import com.partysun.flight.viper.VipState;
import com.partysun.flight.viper.VipTiledSprite;

public class CameraDemo extends VipState {
	private VipKeyboardStandart kListener;
	private VipTiledSprite obj;
	private VipSprite bg;
	@Override
	public void create() {
		bg = new VipSprite(0, 0, "images/menu.jpg");
		add(bg);
		obj = 	new VipTiledSprite(20, 20, "images/peasprites.png", 37, 42, 4, 1, 4);
		add(obj);
		obj.nextTile();
		VipG.camera.target = obj;
		kListener = new VipKeyboardStandart();		
		addKeybordControl(kListener);
	}
	
	@Override
	public void update() {	
		super.update();		
		if (kListener.ESC)	{
			VipG.switchState(new LogoDemo());
		}
		obj.velocity = new VipPoint();
		
		if (kListener.LEFT) {
			obj.velocity.x = -0.80f;
		} 
		if(kListener.RIGHT) {
			obj.velocity.x = 0.80f;
		} 			
		if (kListener.UP) {
			obj.velocity.y = -0.80f;
		} 
		if(kListener.DOWN) {
			obj.velocity.y = 0.80f;
		} 	
	}
}

package com.partysun.flight.core;

import com.partysun.flight.viper.VipG;
import com.partysun.flight.viper.VipKeyboardStandart;
import com.partysun.flight.viper.VipSprite;

public class Ship extends VipSprite{
	
	public VipKeyboardStandart kListener;
	private float _shootTimer = 250;
	public boolean isShoot = false;
	
	public Ship() {
		super(50, 50, "images/Ship.png");
		width = 40;
		height = 16;
		kListener = new VipKeyboardStandart();
	}
	
	@Override
	public void update() {	
		super.update();
		
		isShoot = false;
		velocity.x = 0;
		velocity.y = 0;		
		
		if (kListener.LEFT && x > 20) {
			velocity.x = -0.80f;
		} 
		if(kListener.RIGHT && x < 580) {
			velocity.x = 0.80f;
		} 			
		if (kListener.UP && y > 10) {
			velocity.y = -0.80f;
		} 
		if(kListener.DOWN && y < 450) {
			velocity.y = 0.80f;
		} 	
		_shootTimer -= VipG.elapsed;
		if(kListener.SPACE && _shootTimer < 0) {			
			_shootTimer = 250;
			isShoot = true;
		}		
	}
}
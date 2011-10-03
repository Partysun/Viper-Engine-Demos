package com.partysun.flight.core;

import com.partysun.flight.viper.VipG;
import com.partysun.flight.viper.VipKeyboardStandart;
import com.partysun.flight.viper.VipMouseControll;
import com.partysun.flight.viper.VipPoint;
import com.partysun.flight.viper.VipSprite;
import com.partysun.flight.viper.VipState;

public class SpriteDemo extends VipState {
	
	private VipSprite button;
	private VipKeyboardStandart kListener;
	
	@Override
	public void create() {
		
		final VipSprite img = new VipSprite(20, 20, "images/house.png");
		add(img);
		
		button = new VipSprite(50, 350, "images/button_Spritesheet1x1.png");
		add(button);
		kListener = new VipKeyboardStandart();		
		addKeybordControl(kListener);
		addMouseControl(new VipMouseControll() {
			
			@Override
			public void onMouseStart(VipPoint point) {				
				add(new VipSprite((float) (Math.random()*400), (float) (Math.random()*400), "images/house.png"));
			}
			
			@Override
			public void onMouseEnd(VipPoint point) {
			}
			
			@Override
			public void onMouseDrag(VipPoint point) {
			}
		});
	}
	
	@Override
	public void update() {	
		super.update();
		if (kListener.ESC)	{
			VipG.switchState(new LogoDemo());
		}
		if (button._alpha > 0)
			button._alpha -= 0.02f;
		else
		{
			button._alpha = 0.0f;
			button.setVisible(false);
		}
	}
}

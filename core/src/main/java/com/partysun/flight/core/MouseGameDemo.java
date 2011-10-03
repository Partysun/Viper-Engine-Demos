package com.partysun.flight.core;


import playn.core.Surface;
import playn.core.SurfaceLayer;

import com.partysun.flight.viper.VipAnimatedSprite;
import com.partysun.flight.viper.VipG;
import com.partysun.flight.viper.VipKeyboardStandart;
import com.partysun.flight.viper.VipMouseControll;
import com.partysun.flight.viper.VipObject;
import com.partysun.flight.viper.VipPoint;
import com.partysun.flight.viper.VipSprite;
import com.partysun.flight.viper.VipState;

public class MouseGameDemo extends VipState{
	private VipKeyboardStandart kListener;
	@Override
	public void create() 
	{
		//TODO: put background setting into engine
		SurfaceLayer back = (SurfaceLayer) VipG.camera.screen.get(0);
		Surface surf = back.surface();
		surf.clear();
	   	surf.setFillColor(0xffFFFFF);
	   	surf.fillRect(0, 0, VipG.StageWidth, VipG.StageHeight);
			
		final VipAnimatedSprite exp = new VipAnimatedSprite(0, 0, "images/BOW_Spritesheet4x3.png", 80, 80, 4, 3, 12);
		int [] a = {0,1,2,3,4,5,6,7,8,9,10,11};
		exp.addAnimation("explosion", a, 9, false);
		exp.kill();		
		add(exp);
		kListener = new VipKeyboardStandart();		
		addKeybordControl(kListener);
		addMouseControl(new VipMouseControll() {	
			@Override
			public void onMouseStart(VipPoint point) {
				for (int i = 1; i < members.size(); i++) {
					VipSprite temp = (VipSprite)members.get(i);
					if (temp.alive && check(temp,point)) {
						temp.setVisible(false);
						temp.kill();
						exp.reset(temp.x, temp.y);
						exp.play("explosion");
					}
				}
			}
			
			@Override
			public void onMouseEnd(VipPoint point) {
				
			}
			
			@Override
			public void onMouseDrag(VipPoint point) {
				// TODO Auto-generated method stub
				
			}
		});
	}	
	
	private boolean check(VipSprite sprite, VipPoint point)
	{
		return (point.x >= sprite.x && point.x <= sprite.x + sprite.width && point.y >= sprite.y && point.y <= sprite.y + sprite.height);		
	}
	
	@Override
	public void update()
	{
		super.update();
		if (kListener.ESC)	{
			VipG.switchState(new LogoDemo());
		}
		
		if (Math.random() > 0.95)
		{
			add((VipObject) new VacuumCleaner((int)(Math.random()*700), 0, "images/MINI_Spritesheet2x2.png", 35, 35, 2, 2, 4));
		}

	}

}

package com.partysun.flight.core;

import playn.core.Surface;
import playn.core.SurfaceLayer;

import com.partysun.flight.viper.VipAnimatedSprite;
import com.partysun.flight.viper.VipG;
import com.partysun.flight.viper.VipKeyboardStandart;
import com.partysun.flight.viper.VipMouseControll;
import com.partysun.flight.viper.VipPoint;
import com.partysun.flight.viper.VipState;

public class AnimationDemo extends VipState{

	private VipKeyboardStandart kListener;
	
	@Override
	public void create() {
		//TODO: put background setting into engine
		SurfaceLayer back = (SurfaceLayer) VipG.camera.screen.get(0);
		Surface surf = back.surface();
		surf.clear();
    	surf.setFillColor(0xffDFF2DF);
    	surf.fillRect(0, 0, VipG.StageWidth, VipG.StageHeight);
		
    	//Simple loop Animation
		final VipAnimatedSprite exp = new VipAnimatedSprite(0, 0, "images/BOW_Spritesheet4x3.png", 80, 80, 4, 3, 12);
		int [] a = {0,1,2,3,4,5,6,7,8,9,10,11};
		exp.addAnimation("explosion", a, 9, true);	
		exp.play("explosion");
		add(exp);
		
		//Simple loop Animation 2
		final VipAnimatedSprite exp2 = new VipAnimatedSprite(75, 75, "images/5_Spritesheet5x4.png", 170, 170, 5, 4, 20);
		int [] a2 = {0,1,2,3,4,5,6,7,8,9,10,11};
		exp2.addAnimation("explosion", a2, 20, true);	
		exp2.play("explosion");
		add(exp2);
		kListener = new VipKeyboardStandart();		
		addKeybordControl(kListener);
		addMouseControl(new VipMouseControll() {
			
			@Override
			public void onMouseStart(VipPoint point) {			
				final VipAnimatedSprite exp2 = new VipAnimatedSprite((float) (Math.random()*400), (float) (Math.random()*400), "images/5_Spritesheet5x4.png", 170, 170, 5, 4, 20);
				int [] a = {0,1,2,3,4,5,6,7,8,9,10,11};
				exp2.addAnimation("explosion", a, 12, true);	
				exp2.play("explosion");
				add(exp2);		
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
	}
}

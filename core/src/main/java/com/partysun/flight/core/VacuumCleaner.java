package com.partysun.flight.core;

import com.partysun.flight.viper.VipAnimatedSprite;

public class VacuumCleaner extends VipAnimatedSprite {

	public VacuumCleaner(float X, float Y, String imagePath, int FrameWidth,
			int FrameHeight, int columnFrames, int rowFrames, int countFrames) {
		super(X, Y, imagePath, FrameWidth, FrameHeight, columnFrames, rowFrames,
				countFrames);
		this.width = 35;
		this.height = 35;
		int[] arr = {0,1,2,3};
		this.addAnimation("play", arr , 10, true);
		this.play("play");
		this.drag.y = 0;
		this.velocity.y = 0.1f;
	}
	
	@Override
	public void update()
	{
		super.update();
		if(this.x >= 720){
			this.setVisible(false);
			this.kill();
		}
	}
}

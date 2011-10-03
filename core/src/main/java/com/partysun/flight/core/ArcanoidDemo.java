package com.partysun.flight.core;

import playn.core.Keyboard;
import playn.core.Keyboard.Event;
import playn.core.PlayN;

import com.partysun.flight.viper.VipG;
import com.partysun.flight.viper.VipGroup;
import com.partysun.flight.viper.VipKeyboardStandart;
import com.partysun.flight.viper.VipSprite;
import com.partysun.flight.viper.VipState;

public class ArcanoidDemo extends VipState {

	private VipSprite bat;
	private VipSprite ball;
	
	private VipGroup walls;
	private VipSprite leftWall;
	private VipSprite rightWall;
	private VipSprite topWall;
	private VipSprite bottomWall;
	private VipGroup bricks;
	
	private double testTime = 0;
	private boolean controlLeft;
	private boolean controlRight;
	
	private VipKeyboardStandart kListener;

	@Override
	public void create() {		
		testTime = PlayN.currentTime();

		bat = new VipSprite(180, 220).makeGraphic(40, 6, 0xffd63bc3);
		bat.immovable = true;
		ball = new VipSprite(180, 160).makeGraphic(6, 6, 0xffd63bc3);
		ball.elasticity = 1;
		ball.maxVelocity.x = 4.0f;
		ball.maxVelocity.y = 4.0f;
		ball.velocity.y = -0.2f;
		walls = new VipGroup();
		leftWall = new VipSprite(0, 0).makeGraphic(10, 240, 0xffababab);
		leftWall.immovable = true;
		walls.add(leftWall);
		rightWall = new VipSprite(310, 0).makeGraphic(10, 240, 0xffababab);
		rightWall.immovable = true;
		walls.add(rightWall);
		topWall = new VipSprite(0, 0).makeGraphic(320, 10, 0xffababab);
		topWall.immovable = true;
		walls.add(topWall);
		bottomWall = new VipSprite(0, 239).makeGraphic(320, 10, 0xff000000);
		bottomWall.immovable = true;
		walls.add(bottomWall);
			
		//walls.immovable
		//	Some bricks
		bricks = new VipGroup();
		int bx = 10;
		int by = 30;
		int[] brickColours = { 0xffd03ad1, 0xfff75352, 0xfffd8014, 0xffff9024, 0xff05b320, 0xff6d65f6 };
		for (int y = 0; y < 6; y++)
		{
			for (int x = 0; x < 20; x++)
			{
				VipSprite tempBrick = new VipSprite(bx, by);
				tempBrick.makeGraphic(15, 15, brickColours[y]);
				tempBrick.immovable = true;
				bricks.add(tempBrick);
				bx += 15;
			}
			bx = 10;
			by += 15;
		}
		add(walls);
		add(bat);
		add(ball);
		add(bricks);
	
		addKeybordControl(new Keyboard.Listener() {

			@Override
			public void onKeyDown(Event event) {
				if (event.keyCode() == Keyboard.KEY_LEFT) {
					controlLeft = true;				
				} 
				if(event.keyCode() == Keyboard.KEY_RIGHT) {
					controlRight = true;
				} 
				if(event.keyCode() == Keyboard.KEY_ESC) {
					VipG.switchState(new LogoDemo());
				} 
			}

			@Override
			public void onKeyUp(Event event) {
				if (event.keyCode() == Keyboard.KEY_LEFT) {
					controlLeft = false;				
				} 
				if(event.keyCode() == Keyboard.KEY_RIGHT) {
					controlRight = false;
				}				
			}});	
		System.out.println(PlayN.currentTime() - testTime);
	}

	@Override
	public void update() {
		super.update();
		bat.velocity.x = 0;
		
		if (controlLeft && bat.x > 10) {
			bat.velocity.x = -0.80f;
		} 
		if(controlRight && bat.x < 270) {
			bat.velocity.x = 0.80f;
		} 	
		
		collide(ball, walls);
		
		if(collide(ball, bat)) {			
			float midball = ball.x + 3;
			float midbat = bat.x + 20;
			float diff;
			if (midball < midbat )
			{
				//	Ball is on the left of the bat
				diff = midbat  - ball.x;
				ball.velocity.x = (-.02f * diff);
			}
			else if (midball > midbat)
			{
				//	Ball on the right of the bat				
				diff = midball - midbat ;			
				ball.velocity.x = (.02f * diff);
			}
			else
			{				
				//	Ball is perfectly in the middle
				//	A little random X to stop it bouncing up!
				ball.velocity.x = (float) (.02f + (int)Math.random() * 0.8f);
			}
		}
		
		VipSprite test = (VipSprite) collide(ball, bricks);
		if(test != null) {
			test.setVisible(false);
			test.kill();			
		}
		
		if (bat.x < 10)
		{
			bat.x = 10;
		}
		if (bat.x > 270)
		{
			bat.x = 270;
		}
	}	
}
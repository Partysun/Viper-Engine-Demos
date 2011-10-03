package com.partysun.flight.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.platformType;

import java.util.ArrayList;

import playn.core.CanvasLayer;
import playn.core.Keyboard;
import playn.core.Platform;
import playn.core.Keyboard.Event;

import com.partysun.flight.viper.VipG;
import com.partysun.flight.viper.VipSprite;
import com.partysun.flight.viper.VipState;
import com.partysun.flight.viper.VipText;

public class LogoDemo extends VipState {

	private ArrayList<VipState> demoList = new ArrayList<VipState>();
	{
		demoList.add(new SpriteDemo());
		demoList.add(new AnimationDemo());
		demoList.add(new MovingDemo());
		demoList.add(new ParticlesDemo());
		demoList.add(new CameraDemo());
		demoList.add(new MouseGameDemo());
		demoList.add(new ScrollShooterDemo());		
		demoList.add(new ArcanoidDemo());
		demoList.add(new TextDemo());
	}

	@Override
	public void create() {

		new VipSprite(0, 0, "images/logo.png");
		// Html5/native is support Text 
		// Flash is not support Text now.//
		if (platformType() != Platform.Type.FLASH) {
			new VipText(250, 25, 50, 30, " @Partysun present Viper Engine ");
			new VipText(220, 75, 50, 30, "for fast 2d game dev! ");
			new VipText(160, 120, 50, 30, "HTML5/Flash/Native/Android");

			new VipText(160,	170,50,	30,"Press 1/2/3... to run demo, ESC key to return to menu from demo",
					0, 16f);
			new VipText(240, 200, 50, 30, "1 - Draw Sprite!", 0, 16f);
			new VipText(238, 230, 50, 30, "2 - Work with Animation!", 0, 16f);			
			new VipText(238, 260, 50, 30, "3 - Move Objects!", 0, 16f);
			new VipText(238, 290, 50, 30, "4 - Particles!", 0, 16f);
			new VipText(238, 320, 50, 30, "5 - Camera !", 0, 16f);
			new VipText(238, 350, 50, 30, "6 - Mouse Game Demo!", 0, 16f);
			new VipText(238, 380, 50, 30, "7 -  Scroll Shooter Demo!", 0, 16f);
			new VipText(238, 410, 50, 30, "8 -  Arcanoid Demo!", 0, 16f);
			new VipText(238, 440, 50, 30, "9 -  Text Demo!", 0, 16f);
		}
		else
		{
			int width = graphics().width(), height = graphics().height();
			CanvasLayer bg = graphics().createCanvasLayer(width, height);
			float ypos = 120;
			bg.canvas().drawText("Viper Engine Demos:", 220, ypos);
			ypos += 25;
			VipG.camera.screen.add(bg);			
			bg.canvas().drawText("1" + " - " + "Draw Sprite!", 225, ypos);
			ypos += 25;
			bg.canvas().drawText("2" + " - " + "Work with Animation!", 225, ypos);
			ypos += 25;
			bg.canvas().drawText("3" + " - " + "Move Objects!", 225, ypos);
			ypos += 25;
			bg.canvas().drawText("4" + " - " + "Particles!", 225, ypos);
			ypos += 25;
			bg.canvas().drawText("5" + " - " + "Camera!", 225, ypos);
			ypos += 25;
			bg.canvas().drawText("6" + " - " + "Mouse Game Demo!", 225, ypos);
			ypos += 25;
			bg.canvas().drawText("7" + " - " + "Scroll Shooter Demo!", 225, ypos);
			ypos += 25;
			bg.canvas().drawText("8" + " - " + "Arcanoid Demo!", 225, ypos);
			ypos += 25;
			bg.canvas().drawText("9" + " - " + "Text Demo", 225, ypos);
			ypos += 25;
			ypos += 25;
			bg.canvas()
					.drawText("Press 1/2/3... to run demo, ESC key to return to menu from demo",
							225, ypos);
		}
	    
		addKeybordControl(new Keyboard.Listener()
		{

			@Override
			public void onKeyDown(Event event) {
				 int keyCode = event.keyCode();
			      int demoIndex = keyCode - '1';
			      if (demoIndex >= 0 && demoIndex < demoList.size()) {
			       VipG.switchState(demoList.get(demoIndex));
			      }
			}

			@Override
			public void onKeyUp(Event event) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
}

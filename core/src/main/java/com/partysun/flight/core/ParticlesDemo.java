package com.partysun.flight.core;

import com.partysun.flight.viper.VipEmmiter;
import com.partysun.flight.viper.VipG;
import com.partysun.flight.viper.VipKeyboardStandart;
import com.partysun.flight.viper.VipMouseControll;
import com.partysun.flight.viper.VipPoint;
import com.partysun.flight.viper.VipSprite;
import com.partysun.flight.viper.VipState;

public class ParticlesDemo extends VipState{

	private VipEmmiter em;
	private VipKeyboardStandart kListener;
	private VipSprite button;
	
	@Override
	public void create() {
	
		em = createEmitter();
		add(em);
		
		VipEmmiter em2 = createEmitter2();
		em2.x = 150;
		em2.y = 30;
		em2.width = 50;
		em2.height = 100;
		em2.start(false, 200.0f, 50, 100);
		add(em2);
		
		button = new VipSprite(50, 350, "images/button2.png");
		add(button);
		
		kListener = new VipKeyboardStandart();		
		addKeybordControl(kListener);
		addMouseControl(new VipMouseControll() {
			
			@Override
			public void onMouseStart(VipPoint point) {			
				em.x = point.x;
				em.y = point.y;
				em.width = 100;
				em.height = 100;
				em.start(true, 200.4f, 0, 0);
			}
			
			@Override
			public void onMouseEnd(VipPoint point) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMouseDrag(VipPoint point) {
				em.x = point.x;
				em.y = point.y;				
			}
		});
	}

	private VipEmmiter createEmitter2()
	{		
		VipEmmiter emitter = new VipEmmiter();
		emitter.maxRotation = 0;
		emitter.setXSpeed(-0.15f, 0.15f);
		emitter.setYSpeed(-0.15f, 0.15f);
		emitter.gravity = 0.009f;			
		emitter.bounce = 0;
		emitter.setRotation(0.05f,0.01f);
		emitter.makeParticles(120, 3, 3, 0xffFFcc23);
		return emitter;
	}	
	private VipEmmiter createEmitter()
	{		
		VipEmmiter emitter = new VipEmmiter();
		//emitter.delay = 1;		
		emitter.maxRotation = 0;
		emitter.setXSpeed(-0.85f, 0.85f);
		emitter.setYSpeed(-0.85f, 0.85f);
		emitter.gravity = 0;			
		emitter.bounce = 0;		
		emitter.makeParticles(10, 2, 2, 0xffFFcc23);
		return emitter;
	}
	
	@Override
	public void update() {
		super.update();		
		if (kListener.ESC)	{
			VipG.switchState(new LogoDemo());
		}
		
		if (button._alpha > 0)
			button._alpha -= 0.008f;
		else
		{
			button._alpha = 0.0f;
			button.setVisible(false);
		}
	}
}

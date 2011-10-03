package com.partysun.flight.core;

import com.partysun.flight.viper.VipG;
import com.partysun.flight.viper.VipKeyboardStandart;
import com.partysun.flight.viper.VipSprite;
import com.partysun.flight.viper.VipState;
import com.partysun.flight.viper.VipText;

public class TextDemo extends VipState{
	VipText a;
	private VipKeyboardStandart kListener;
	@Override
	public void create() {
		add(new VipSprite(10, 10).makeGraphic(100, 100, 0xffFFCCFF));
		add(new VipSprite(35, 35).makeGraphic(100, 100, 0xffCCFFFF));
	    a = new VipText(25, 25, 29, 30, "Viper Engine");
	    add(a);	   
		kListener = new VipKeyboardStandart();		
		addKeybordControl(kListener);
	}
	
	@Override
	public void update() {	
		super.update();
		if (kListener.ESC)	{
			VipG.switchState(new LogoDemo());
		}
		a.setText(_VISIBLECOUNT + "");
	}
}

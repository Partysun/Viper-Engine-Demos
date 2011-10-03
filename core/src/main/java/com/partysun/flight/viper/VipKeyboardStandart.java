package com.partysun.flight.viper;

import playn.core.Keyboard;
import playn.core.Keyboard.Event;

public class VipKeyboardStandart implements Keyboard.Listener {
	
	public boolean LEFT = false;
	public boolean RIGHT = false;
	public boolean UP = false;
	public boolean DOWN = false;
	public boolean SPACE = false;
	public boolean ESC = false;
	
	@Override
	public void onKeyDown(Event event) {		
		if (event.keyCode() == Keyboard.KEY_LEFT) {
			LEFT = true;				
		} 
		if(event.keyCode() == Keyboard.KEY_RIGHT) {
			RIGHT = true;
		} 
		if(event.keyCode() == Keyboard.KEY_UP) {
			UP = true;
		} 
		if(event.keyCode() == Keyboard.KEY_DOWN) {
			DOWN = true;
		} 		
		if(event.keyCode() == Keyboard.KEY_SPACE) {
			SPACE = true;
		} 	
		if(event.keyCode() == Keyboard.KEY_ESC) {
			ESC = true;
		} 
	}
	
	@Override
	public void onKeyUp(Event event) {
		if (event.keyCode() == Keyboard.KEY_LEFT) {
			LEFT = false;				
		} 
		if(event.keyCode() == Keyboard.KEY_RIGHT) {
			RIGHT = false;
		}	
		if(event.keyCode() == Keyboard.KEY_UP) {
			UP = false;
		} 
		if(event.keyCode() == Keyboard.KEY_DOWN) {
			DOWN = false;
		} 	
		if(event.keyCode() == Keyboard.KEY_SPACE) {
			SPACE = false;
		} 
		if(event.keyCode() == Keyboard.KEY_ESC) {
			ESC = false;
		} 
	}

}

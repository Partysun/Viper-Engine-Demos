package com.partysun.flight.viper;

import static playn.core.PlayN.keyboard;
import static playn.core.PlayN.pointer;
import playn.core.Keyboard;
import playn.core.Pointer;

/**
 * This is the basic game "state" object - e.g. in a simple game
 * you might have a menu state and a play state.
 * 
 * @author Yura - (@Partysun - yura.zatsepin@gmail.com)
 */
public abstract class VipState extends VipGroup {

	protected Pointer.Listener _delegate;
	 
	abstract public void create();	
		
	public void addMouseControl(Pointer.Listener delegate) {
		_delegate = (delegate == null) ? new Pointer.Adapter() : delegate;
		pointer().setListener(_delegate);
	}
	
	public void addKeybordControl(Keyboard.Listener delegate) {
		keyboard().setListener(delegate);
	}
	
	public VipObject collide(VipObject a, VipGroup agroup) {
		for (int i = 0 ; i < agroup.members.size() ; ++i) {
			VipObject b = (VipObject) agroup.members.get(i);
			if (a.alive && b.alive && a.overlaps(b)) {
				VipObject.separate(a, b);				
				return b;
			}			
		}
		return null;
	}
	
	public VipObject overlap(VipObject a, VipGroup agroup) {
		for (int i = 0 ; i < agroup.members.size() ; ++i) {
			VipObject b = (VipObject) agroup.members.get(i);
			if (a.alive && b.alive && a.overlaps(b)) {								
				return b;
			}			
		}
		return null;
	}
	
	public boolean collide(VipObject a, VipObject b) {		
		if (a.alive && b.alive && a.overlaps(b)) {			
			VipObject.separate(a, b);
			return true;
		}		
		return false;
	}
}

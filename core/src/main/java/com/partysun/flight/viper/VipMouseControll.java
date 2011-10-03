package com.partysun.flight.viper;

import playn.core.Pointer;
import playn.core.Pointer.Event;

public abstract class VipMouseControll implements Pointer.Listener {

	abstract public void onMouseStart(VipPoint point);
	
	abstract public void onMouseEnd(VipPoint point);
	
	abstract public void onMouseDrag(VipPoint point);
	
	@Override
	public void onPointerStart(Event event) {
		onMouseStart(new VipPoint(event.x(), event.y()));		
	}

	@Override
	public void onPointerEnd(Event event) {
		onMouseEnd(new VipPoint(event.x(), event.y()));		
	}

	@Override
	public void onPointerDrag(Event event) {
		onMouseDrag(new VipPoint(event.x(), event.y()));		
	}
}

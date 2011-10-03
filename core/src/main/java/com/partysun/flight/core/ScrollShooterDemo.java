package com.partysun.flight.core;

import playn.core.Surface;
import playn.core.SurfaceLayer;

import com.partysun.flight.viper.VipEmmiter;
import com.partysun.flight.viper.VipG;
import com.partysun.flight.viper.VipGroup;
import com.partysun.flight.viper.VipObject;
import com.partysun.flight.viper.VipState;

public class ScrollShooterDemo extends VipState {

	private VipGroup _aliens;
	private VipGroup _bullets;
	private float _spawnTimer;
	private float _spawnInterval = 1500;
	private Ship ship;
	@Override
	public void create() {		
		//TODO: put background setting into engine
		SurfaceLayer back = (SurfaceLayer) VipG.camera.screen.get(0);
		Surface surf = back.surface();
		surf.clear();
    	surf.setFillColor(0xffABCC7D);
    	surf.fillRect(0, 0, VipG.StageWidth, VipG.StageHeight);
    	//TODO: make better... 
    	ship = new Ship();    
    	add(ship);
    	addKeybordControl(ship.kListener);
    	
    	_aliens = new VipGroup();
    	_bullets = new VipGroup();
    	
    	for(int i = 0 ; i < 20; i ++)
    	{
    		Alien tempAlien = new Alien(0, 0);
    		tempAlien.kill();
    		_aliens.add(tempAlien);
    	}
    	add(_aliens);
    	
    	for(int i = 0 ; i < 3; i ++)
    	{
    		Bullet tempBullet = new Bullet(0, 0);
    		tempBullet.kill();
    		_bullets.add(tempBullet);
    	}
    	add(_bullets);
    }
	
	@Override
	public void update() {	
		super.update();
			
		if (ship.kListener.ESC)	{
			VipG.switchState(new LogoDemo());
		}
		
		VipObject tempAlien = overlap(ship, _aliens);
		if (tempAlien != null) 
		{
			VipEmmiter em = createEmitter();
			tempAlien.kill();
			ship.kill();
			VipEmmiter em2 = createEmitter();
			em.x = ship.x;
			em.y = ship.y;
			em2.x = tempAlien.x;
			em2.y = tempAlien.y;
			em2.width = 50;
			em2.height = 50;
			em.width = 100;
			em.height = 100;
			em.start(true, 300, 0, 0);
			em2.start(true, 300, 0,0);
		}
		
		if(ship.isShoot)
		{
			spawnBullet();
		}
		
		_spawnTimer -= VipG.elapsed;
		if(_spawnTimer < 0)
		{			
			spawnAlien();
			resetSpawnTimer();
		}
	}	
	
	private void spawnAlien()
	{
		float x = VipG.WorldWidth - 100;
		float y = (float) (Math.random() * (VipG.WorldHeight - 200) + 20);
		Alien a = (Alien) _aliens.recycle();
		if(a != null)
		{
			a.reset(x,y);
			a.setVisible(true);		
		}
	}
	
	private void spawnBullet()
	{
		Bullet tempBullet = (Bullet) _bullets.recycle();
		tempBullet.reset(ship.x+ 36, ship.y + 5);
		tempBullet.setVisible(true);
	}

	private void resetSpawnTimer()
	{
		_spawnTimer = _spawnInterval;
		_spawnInterval *= 0.95;
		if(_spawnInterval < 0.4)
		{
			_spawnInterval = 0.4f;
		}		
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
		emitter.makeParticles(10, 2, 2, 0xFF597137);
		add(emitter);
		return emitter;
	}
}

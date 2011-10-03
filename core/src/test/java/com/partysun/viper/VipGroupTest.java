package com.partysun.viper;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.partysun.flight.viper.VipBasic;
import com.partysun.flight.viper.VipGroup;
import com.partysun.flight.viper.VipObject;
import com.partysun.flight.viper.VipParticle;

public class VipGroupTest {

	private static VipGroup group;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		group = new VipGroup();
	}

	@Test
	public void initBasicObject() throws Exception {
		assertTrue("Init complete", group.exists);
		assertTrue("Init complete", group.alive);
		assertTrue("Init complete", group.active);
	}
	
	@Test
	public void clearTest() throws Exception {
		group.destroy();
		assertTrue("clear failure", group.members.size() == 0);		
	}
	
	@Test
	public void addTest() throws Exception {
		group.add(new VipObject());
		assertTrue("added failure", group.members.size() == 1);	
		group.add(new VipObject());
		group.add(new VipObject());
		assertTrue("added failure", group.members.size() == 3);
	}
	
	@Test
	public void recycleTest() throws Exception {
		group.add(new VipObject());			
		group.add(new VipObject());
		group.add(new VipObject());
		group.members.get(1).exists = false;
		assertFalse("exist failure", group.members.get(1).exists);
		VipBasic basic = group.recycle();
		assertNotNull("recycle failure", basic);
	}
	
	@Test
	public void recycleSecondTest() throws Exception {
		clearTest();		
		int particles = 10;
		for(int i = 0; i < particles; i++)
		{
			VipParticle particle = new VipParticle();
			particle.kill();
			group.add(particle);
		}		
		assertTrue("added failure", group.members.size() == particles);
		assertFalse("added failure", group.members.get(0).exists);
		assertFalse("added failure", group.members.get(particles - 1).exists);
		// All members killed.
		for(int i = 0; i < particles; i++)
		{
			VipParticle particle = (VipParticle) group.recycle();
			particle.reset(0, 0);
		}		
		assertNull("null recycle", group.recycle());
		group.members.get(0).kill();
		assertNotNull("null recycle", group.recycle());
//		System.out.println(p.exists +" ; "+ p.alive);
	}
}

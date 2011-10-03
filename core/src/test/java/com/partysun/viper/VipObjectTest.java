package com.partysun.viper;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.partysun.flight.viper.VipObject;

/**
 * Tests the Layer.Util class.
 */
public class VipObjectTest {

	private static VipObject object;
	private static VipObject object2;
	
	 @BeforeClass
	 public static void setUpBeforeClass() throws Exception {
		 object = new VipObject();
		 object2 = new VipObject();
	 }

	 @Test
	 public void initBasicObject() throws Exception{
		 assertTrue("Init complete", object.exists);
		 assertTrue("Init complete", object.alive);
		 assertTrue("Init complete", object.active);
	 }	 
	 
	 @Test
	 public void checkAllowCollision() throws Exception{
		 assertTrue(object.allowCollisions == VipObject.ANY);	
		 object.allowCollisions = VipObject.LEFT;
		 assertEquals(object.allowCollisions , VipObject.LEFT);
		 assertTrue(object.allowCollisions == VipObject.LEFT);
		 object.allowCollisions = VipObject.ANY;
		 assertTrue(object.allowCollisions == VipObject.ANY);
		 assertTrue(object.allowCollisions == (VipObject.LEFT | VipObject.RIGHT | VipObject.UP | VipObject.DOWN));
	 }	
	 
	 @Test
	 public void checkSeparate() throws Exception{
		 assertTrue(object.allowCollisions == VipObject.ANY);
//		 System.out.print(object.allowCollisions & VipObject.RIGHT);		 
		 // 0x1111 0x0010
		 assertFalse((object.allowCollisions & VipObject.RIGHT) != VipObject.RIGHT);
		 assertFalse((object.allowCollisions & VipObject.LEFT) != VipObject.LEFT);
	 }	 
	 
	 @Test
	 public void checkOverlap() throws Exception{
		object.x = 20;
		object.y = 20;
		object.width = 20;
		object.height = 20;
		
		object2.x = 39;
		object2.y = 39;
		object2.width = 20;
		object2.height = 20;
		assertTrue(object.overlaps(object2));		
	 }	 
  }

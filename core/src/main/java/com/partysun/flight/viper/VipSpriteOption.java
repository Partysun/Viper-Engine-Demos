package com.partysun.flight.viper;

/**
 * Represents the data associated with a single sprite of big spritesheets.
 * Only for Tiled and Animation Sprite use.
 */
public class VipSpriteOption {
	  
	  private final int x;
	  private final int y;
	  private final int width;
	  private final int height;

	  public VipSpriteOption(int x, int y, int width, int height) {	    
	    this.x = x;
	    this.y = y;
	    this.width = width;
	    this.height = height;
	  }

	  public int height() {
	    return height;
	  }

	  public int width() {
	    return width;
	  }

	  public int x() {
	    return x;
	  }

	  public int y() {
	    return y;
	  }
	  
	  @Override
	public String toString() {		
		return " x: " + x + " y: " + y + " width: " + width + " height: " + height;
	}
}

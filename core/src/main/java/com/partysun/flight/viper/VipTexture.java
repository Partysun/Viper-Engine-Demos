package com.partysun.flight.viper;

import static playn.core.PlayN.assetManager;

import playn.core.Image;
import playn.core.ResourceCallback;

import static playn.core.PlayN.log;

public class VipTexture {

	// ===========================================================
    // Constants
    // ===========================================================
	
	protected static String ImgDefault = "images/house.png";
	
	// ===========================================================
    // Fields
    // ===========================================================
	
	@SuppressWarnings("unused")
	private boolean isLoad;
	
	@SuppressWarnings("unused")
	private String _pathToTexture = null;
		
	public Image image; 
	
	// ===========================================================
    // Constructors
    // ===========================================================
	public VipTexture (String pathToTexture){
		super();
		_pathToTexture = pathToTexture;
		image = assetManager().getImage(pathToTexture);
	}
	
	public VipTexture (){
		this(ImgDefault);		
	}

	// ===========================================================
    // Methods
    // ===========================================================
	
	@SuppressWarnings("unused")
	private void getTexture(String pathToTexture) {
		final Image texture = assetManager().getImage(pathToTexture);
		image = texture;
		 // set callback for image
		texture.addCallback(new ResourceCallback<Image>() {
	      @Override
	      public void done(Image resource) {	       
	        doneGetTexture(texture);	        
	      }

	      @Override
	      public void error(Throwable err) {
	        this.error(err);
	      }
	    });
	}
	
	protected void doneGetTexture(Image texture) {
		setLoad(true);		
	}

	/**
	 * Should be called if an error occurs when loading the sprite image or data. Will handle calling
	 * the {@link ResourceCallback} of the {@link Sprite}.
	 */
	void error(Throwable err) {	 
	    log().error("Error loading texture", err);
	}

	public boolean isLoad() {
		System.out.println("checkkk");
		return true;
	}

	public void setLoad(boolean isLoad) {
		this.isLoad = isLoad;
	}
}
	
	

package com.partysun.flight.viper;

import static playn.core.PlayN.graphics;

import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.ImageLayer;
import pythagoras.f.Point;

public class VipSprite extends VipObject{

	// ===========================================================
    // Constants
    // ===========================================================
	
	protected String ImgDefault = "images/pea.png";

	// ===========================================================
    // Fields
    // ===========================================================
	
	 /**
	 * WARNING: The origin of the sprite will default to its center.
	 * If you change this, the visuals and the collisions will likely be
	 * pretty out-of-sync if you do any rotation.
	 */
	public VipPoint origin;
	
	/**
	 * Change the size of your sprite's graphic.
	 * NOTE: Scale doesn't currently affect collisions automatically,
	 * you will need to adjust the width, height and offset manually.
	 * WARNING: scaling sprites decreases rendering performance for this sprite by a factor of 10x!
	 */
	public VipPoint scale;
	
	/**
	 * Internal tracker for opacity.
	 */
	public float _alpha;
	
	/**
	 * Internal tracker for color tint, used with Flash getter/setter.
	 */
	protected int _color;
	
	protected Image _image;
	
	protected ImageLayer _layer;
	
	protected VipTexture _texture;

	private VipCamera camera;

	public boolean debugad = true;
	
	 // for calculating interpolation
//	private float prevX, prevY, prevA;

	// ===========================================================
    // Constructors
    // ===========================================================
	
	 /**
	 * Creates a white 8x8 square <code>VipSprite</code> at the specified position.
	 * Optionally can load a simple, one-frame graphic instead.
	 * 
	 * @param	X				The initial X position of the sprite.
	 * @param	Y				The initial Y position of the sprite.
	 * @param	Width			The initial Width of the sprite.
	 * @param	Height			The initial Height of the sprite.
	 * @param	SimpleGraphic	The graphic you want to display (OPTIONAL - for simple stuff only, do NOT use for animated images!).
	 */
	public VipSprite(float X,float Y, float Width, float Height, String imagePath)
	{
		super(X + Width*0.5f, Y + Height*0.5f,Width,Height);
		
		health = 1;

		origin = new VipPoint().make(this.width *0.5f, height*0.5F);
		
		scale = new VipPoint(1.0f,1.0f);
		_alpha = 1;
		angle = 0;
		_color = 0x00ffffff;
		
		camera = VipG.camera;	
		if (imagePath != "") {		
			createSprite(imagePath);
		}
		//log().info("init sprite");
	}
	
	public VipSprite(float X,float Y, String imagePath)
	{
		this(X, Y,1,1,imagePath);
	}	

	public VipSprite(float f, float g) {
		this(f,g, 1,1,"");
	}
	// ===========================================================
    // Getter & Setter
    // ===========================================================	

	public Image get_image() {
		return _image;
	}

	public void set_image(Image _image) {
		this._image = _image;
	}
	
	public ImageLayer get_layer() {
		return _layer;
	}

	public void set_layer(ImageLayer _layer) {
		this._layer = _layer;
	}
	
	@Override
	public void setVisible(boolean visible) {	
		super.setVisible(visible);
		if (_layer != null)
			_layer.setVisible(visible);
	}

	// ===========================================================
    // Methods
    // ===========================================================
	
	/**
	 * Создаем спрайт из внешней картинки.
	 * @param imagePath - Path to graphic data (png,jpg)
	 */
	protected void createSprite(String imagePath) {
		getTextureByPath(imagePath);		
		_layer.setOrigin(origin.x , origin.y);
	    _layer.setTranslation(x, y);	   
		VipG.camera.screen.add(_layer);		
	}	
	
	protected void getTextureByPath(String imagePath)	{
		VipTexture texture = new VipTexture(imagePath);
		set_image(texture.image);
		_layer = graphics().createImageLayer(_image);
		this._texture = texture;
	}

	@Override
	public void update() {
		super.update();
		if(camera != null){
			_point.x = x - camera.scroll.x*scrollFactor.x; //- offset.x;
			_point.y = y - camera.scroll.y*scrollFactor.y; //- offset.y;
		}
	}
	
	@Override
	public void draw(float alpha) {
		super.draw(alpha);	
		//TODO: OnScreen fix !!!!!!!
		if(alive && exists && getVisible() ){//&& onScreen(camera)) {		
			_point.x = x - camera.scroll.x*scrollFactor.x; //- offset.x;
			_point.y = y - camera.scroll.y*scrollFactor.y; //- offset.y;
			this._layer.setAlpha(this._alpha);
			this._layer.setTranslation(_point.x, _point.y);
			this._layer.setScale(this.scale.x);
			this._layer.setRotation(this.angle);			
		}
		else
		{
			setVisible(false);				
		}
		this.setVisible(getVisible());
	}
	
	@Override
	public void drawDebug() {	
		super.drawDebug();
		//TODO: Not good , ;( ...
		//get bounding box coordinates
//		VipPoint leftTopCorner = getLeftTopPoint();
//		float boundingBoxX = leftTopCorner.x - (int) camera.scroll.x*scrollFactor.x;
//		float boundingBoxY = leftTopCorner.y - (int) camera.scroll.y*scrollFactor.y;
//		float boundingBoxWidth = width;
//		float boundingBoxHeight = height;
		int _w = Math.round(width);
		int _h = Math.round(height);
	    CanvasImage bgtile = graphics().createImage(_w + 1,_h + 1);
	    int boundingBoxColor;
	    if(allowCollisions != NONE)
		{
			if(allowCollisions != ANY)
				boundingBoxColor = VipG.PINK;
			if(immovable)
				boundingBoxColor = VipG.GREEN;
			else
				boundingBoxColor = VipG.RED;
		}
		else
			boundingBoxColor = VipG.BLUE;
	    bgtile.canvas().setStrokeColor(boundingBoxColor);
	    bgtile.canvas().setStrokeWidth(1.2f);
	    bgtile.canvas().drawLine(0 , 0,_w,0);
	    bgtile.canvas().drawLine(_w , 0, width,height);
	    bgtile.canvas().drawLine(_w , _h, 0,_h);
	    bgtile.canvas().drawLine(0 , _h, 0,0);
	    
	    ImageLayer _debug = graphics().createImageLayer(bgtile);  
	    if (debugad) {
	    	debugad = false;	    	
	    	_debug.setOrigin(origin.x , origin.y);
		    _debug.setTranslation(_point.x, _point.y);	   
	    	VipG.camera.screen.add(_debug);	    	
	    }
	}
	
	@Override
	public void reset(float X, float Y) {
		super.reset(X + this.width*0.5f, Y + this.height*0.5f);
	    _point = new VipPoint();
	}
	
	/**
	 * Clean up memory.
	 */
	@Override
	public void destroy()
	{
		_layer.destroy();
		origin = null;
		scale = null;	
		_point = null;
	}
	
	/**
     * Returns true if the supplied, element-relative, coordinates are inside our bounds.
     */
    protected boolean contains (float x, float y) {
        return !(x < 0 || x > width || y < 0 || y > height);
    }

    /**
     * Used to determine whether a point falls in this element's bounds.
     * @param point the point to be tested in this element's parent's coordinate system.
     * @return the leaf-most element that contains the supplied point or null if neither this
     * element, nor its children contain the point. Also {@code point} is updated to contain the
     * hit-element-relative coordinates in the event of a hit.
     */
    public boolean hitTest (VipPoint Point) {
    	Point point = new Point(Point.x, Point.y);
    	// transform the point into our coordinate system
        point = _layer.transform().inverseTransform(point, point);
        float x = point.x + _layer.originX();
        float y = point.y + _layer.originY();
        // check whether it falls within our bounds
        if (!contains(x, y)) return false;
        // if we're the hit component, update the supplied point
        point.set(x, y);
        return true;
    }
    
    /**
	 * This function creates a flat colored square image dynamically.
	 * 
	 * @param	Width		The width of the sprite you want to generate.
	 * @param	Height		The height of the sprite you want to generate.
	 * @param	Color		Specifies the color of the generated block.	
	 * 
	 * @return	This VipSprite instance (nice for chaining stuff together, if you're into that).
	 */
	public VipSprite makeGraphic(int Width, int Height, int Color)
	{
		 CanvasImage tempGraphic = graphics().createImage(Width, Height);
		 tempGraphic.canvas().setFillColor(Color);
		 tempGraphic.canvas().fillRect(0, 0, Width, Height);
		 this.width = Width;
		 this.height = Height;
		 if (_layer == null) {
			 this._layer = graphics().createImageLayer(tempGraphic);
			 _layer.setOrigin(origin.x , origin.y);
			 _layer.setTranslation(x, y);	
			 VipG.camera.screen.add(_layer);						
		 }
		 this._layer.setImage(tempGraphic);
		 //this.set_layer(_layer);
		 return this;
	}
	
	public VipSprite makeGraphic(int Width, int Height)
	{
		return makeGraphic(Width, Height, 0xffFFFFFF);		
	}	
}	
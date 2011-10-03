package com.partysun.flight.viper;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.platformType;
import playn.core.CanvasLayer;
import playn.core.Font;
import playn.core.Font.Style;
import playn.core.Layer;
import playn.core.Platform;
import playn.core.TextFormat;
import playn.core.TextLayout;

public class VipText extends VipObject{
	// ===========================================================
    // Constants
    // ===========================================================
	// ===========================================================
    // Fields
    // ===========================================================
public VipPoint origin;
	
	/**
	 * Change the size of your sprite's graphic.
	 */
	public VipPoint scale;
	
	/**
	 * Internal tracker for opacity.
	 */
	protected float _alpha;
	
	@SuppressWarnings("unused")
	private int _color;
	
	private Layer _layer;
	
	private VipCamera camera;
	
	private TextFormat _format;
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
	 * @param	Text	Text 
	 */
	public VipText(float X,float Y, float Width, float Height, String Text, int Color, float Size , Style FontStyle)
	{
		super(X + Width*0.5f,  Y + Height*0.5f, Width,Height);
		
		health = 1;

		origin = new VipPoint().make(this.width *0.5f, height*0.5F);
		
		scale = new VipPoint(1.0f,1.0f);
		_alpha = 1;
		angle = 0;
		_color = Color;//0x00ffffff;
		
		_point = new VipPoint();
		 // Flash does not support the text rendering needed to use the TriplePlay UI framework
	    if (platformType() != Platform.Type.FLASH) {
	    	createText(Text, Size, FontStyle);
	    }
	    else
	    	createText(Text, Size);
		camera = VipG.camera;
	}

	public VipText(float X,float Y, float Width, float Height, String Text)
	{
		this(X,Y,Width,Height,Text,0,24f,Font.Style.PLAIN);
	}	
	public VipText(float X,float Y, float Width, float Height,  String Text, int m, float f) {
		this(X,Y,Width,Height,Text,0,f,Font.Style.PLAIN);
	}

	// ===========================================================
    // Getter & Setter
    // ===========================================================	
	// ===========================================================
    // Methods
    // ===========================================================
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
		if(alive && exists && getVisible() && onScreen(camera)) {		
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
	
	public void setText(String Text) {
		TextLayout layout = graphics().layoutText(Text, _format);		 
		 ((CanvasLayer) this._layer).canvas().clear();
		((CanvasLayer) this._layer).canvas().drawText(layout, 0, 0);
	}
	
	private void createText(String text, float size, Style fontStyle) {
		Font font = graphics().createFont("Times", fontStyle,size);
		TextFormat format = new TextFormat().withFont(font);
		_format = format;
		TextLayout layout = graphics().layoutText(text, format);
		Layer _layer = createTextLayer(layout);
		_layer.setTranslation(x, y);
		this._layer = _layer;
		VipG.camera.screen.add(_layer);
	}
	
	private void createText(String text, float size) {	
		 CanvasLayer layer = graphics().createCanvasLayer( (int)Math.ceil(width),  (int)Math.ceil(height));
		 layer.canvas().drawText("Viper Engine Demos:", 0, 0);
		 _layer.setTranslation(x, y);
		 _layer = layer;
		 VipG.camera.screen.add(_layer);
	}
	
	protected Layer createTextLayer(TextLayout layout) {
	    CanvasLayer layer = graphics().createCanvasLayer(
	      (int)Math.ceil(layout.width()), (int)Math.ceil(layout.height()));
	    layer.canvas().drawText(layout, 0, 0);
	    return layer;
	  }
}

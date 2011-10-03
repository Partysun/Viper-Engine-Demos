package com.partysun.flight.viper;

import static playn.core.PlayN.assetManager;
import static playn.core.PlayN.json;

import java.util.ArrayList;

import playn.core.Json;
import playn.core.ResourceCallback;


public class VipTiledSprite extends VipSprite {

	 // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
	private ArrayList<VipSpriteOption> _frames = null;
	
	private int countTiles = 0;
	
	private VipSpriteOption currentTile = null;
	
	private int currentTileId = 0;
    // ===========================================================
    // Constructors
    // ===========================================================	

	public VipTiledSprite(float X, float Y, String imagePath, String pathJSON) {
		super(X, Y, imagePath);	
		currentTileId = 0;
		if (pathJSON != "")
			getTiledTextureByPath(pathJSON);		
		else {
			//TODO: ясно что ошибка в парсинге json.сделать парсинг по ширине высоте и количеству кадров.			
			_frames = new ArrayList<VipSpriteOption>();
			_frames.add(new VipSpriteOption(30, 30, 37, 37));
		}
		setCountTiles(_frames.size());
		currentTile = _frames.get(currentTileId);
		updateTile();		
	}  
	
	public VipTiledSprite(float X, float Y, String imagePath, int FrameWidth, int FrameHeight, int columnFrames, int rowFrames,int countFrames) {
		super(X, Y, imagePath);
		
		currentTileId = 0;
		_frames = loadGraphic(FrameWidth,FrameHeight,columnFrames,rowFrames,countFrames);
		setCountTiles(_frames.size());
		currentTile = _frames.get(currentTileId);
		updateTile();		
	}  
	// ===========================================================
    // Getter & Setter
    // ===========================================================

	public int getCountTiles() {
		return countTiles;
	}

	public void setCountTiles(int countTiles) {
		this.countTiles = countTiles;
	}	

	// ===========================================================
    // Methods
    // ===========================================================
	protected void getTiledTextureByPath(String pathJSON) {
		// load and parse json, then add each image parsed from the json to the asset watcher to load
	    assetManager().getText(pathJSON, new ResourceCallback<String>() {
	      @Override
	      public void done(String json) {
	        try {
	        _frames = parseJson(json);
	        } catch (Throwable err) {
	          return;
	        }	       
	      }

	      @Override
	      public void error(Throwable err) {
	        System.out.println("Error: "+ err.toString());
	      }
	    });
	}
	/**
	 * Load an image from an embedded graphic file.
	 * 
	 * @param	Width		Optional, specify the width of your frame (helps VipSprite figure out what to do with non-square sprites or sprite sheets).
	 * @param	Height		Optional, specify the height of your frame (helps VipSprite figure out what to do with non-square sprites or sprite sheets).
	 * 
	 * @return	This FlxSprite instance (nice for chaining stuff together, if you're into that).
	 */
	public ArrayList<VipSpriteOption> loadGraphic(int FrameWidth, int FrameHeight, int columnFrames, int rowFrames,int countFrames)
	{
		ArrayList<VipSpriteOption> frames = new ArrayList<VipSpriteOption>();
		int _tempCount = 0;
		for (int i = 0; i < rowFrames ; ++i) {
			for (int j = 0; j < columnFrames ; ++j) {
				++_tempCount;
				if (_tempCount > countFrames)	
					break;
				frames.add(new VipSpriteOption(FrameWidth * j, FrameHeight * i, FrameWidth, FrameHeight));				
			}
		}
		return frames;		
	}
	
	private ArrayList<VipSpriteOption> parseJson(String pathJSON) {
		ArrayList<VipSpriteOption> _images = new ArrayList<VipSpriteOption>();		

		Json.Object document = json().parse(pathJSON);
		
		Json.Array spriteImages = document.getArray("sprites");
		for (int i = 0; i < spriteImages.length(); i++) {
			Json.Object jsonSpriteOptions = spriteImages.getObject(i);
		
			// parse the sprite images		
			int x = jsonSpriteOptions.getInt("x");
			int y = jsonSpriteOptions.getInt("y");
			int width = jsonSpriteOptions.getInt("w");
			int height = jsonSpriteOptions.getInt("h");		
			_images.add(new VipSpriteOption(x, y, width, height));
		}
		return _images;
	}
	
	/**
	 * Update the Sprite layer.
	 */
	private void updateTile() {
		currentTile = _frames.get(currentTileId);
		_layer.setWidth(currentTile.width());
		_layer.setHeight(currentTile.height());
		_layer.setSourceRect(currentTile.x(), currentTile.y(), currentTile.width(), currentTile.height());		
	}
	
	/**
	 * Render next tile of tiled sprite
	 */
	public void nextTile() {
		if(currentTileId  + 1 < countTiles)
			++currentTileId;
		else
			currentTileId = 0;
		updateTile();
	}
	
	/**
	 * Render set tile of tiled sprite
	 */
	public void setTile(int tileID) {
		if (tileID < 0 || tileID >= countTiles) {
		      throw new IndexOutOfBoundsException("Error with tileID in setTile() VipTiledSprite");
		    }
		currentTileId = tileID;
		updateTile();		
	}
	
}

package com.partysun.flight.viper;

import java.util.HashMap;

public class VipAnimatedSprite extends VipTiledSprite{

	// ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
	private HashMap<String,AnimationDesc> _animation = new HashMap<String, VipAnimatedSprite.AnimationDesc>();
	
	private AnimationDesc currentAnimation = null;	
	
	private String currentNameOfAnim = null;
	
	private boolean isPlay = false;
	
	private float _timeFrameShow = 0.033f;
	
	private float accumulator = 0;

	// ===========================================================
    // Constructors
    // ===========================================================	
	public VipAnimatedSprite(float X, float Y, String imagePath,
			int FrameWidth, int FrameHeight, int columnFrames, int rowFrames,
			int countFrames) {
		super(X, Y, imagePath, FrameWidth, FrameHeight, columnFrames, rowFrames,
				countFrames);
	}
	// ===========================================================
    // Getter & Setter
    // ===========================================================
	
	// ===========================================================
    // Methods
    // ===========================================================
	
	/**
	 * Add animation in animation set of Sprite
	 * @param nameAnim - Key value - Name of animation. ex. "Run" or "Kick"
	 * @param frames - sequence of frames which describes animation.
	 */
	public void addAnimation(String nameAnim, int[] frames, int framerate, boolean looped) {
		AnimationDesc desription = new AnimationDesc(framerate, frames, looped);
		_animation.put(nameAnim, desription);
		_timeFrameShow = 1000 / framerate;
	}
	
	@Override
	public void draw(float alpha) {	
		super.draw(alpha);
		if (isPlay) {
			accumulator += VipG.elapsed;		
			if(accumulator > _timeFrameShow) {
				playNextFrame();				
				accumulator = 0;
			}
		}
		else
		{
			this.setVisible(false);
		}
	}

	public void play(String nameofAnimation) {
		currentAnimation = _animation.get(nameofAnimation);
		if (currentAnimation != null) {
			currentNameOfAnim = nameofAnimation;
			currentAnimation.reset();
			isPlay = true;
		}
	}
	
	private void playNextFrame() {
		if (currentAnimation.finished && !currentAnimation.looped)			
			isPlay = false;		
		else
			currentAnimation.nextFrame();
		
		animationCallback(currentNameOfAnim,currentAnimation.array[currentAnimation.currentTileID], currentAnimation.currentTileID);
	}
	
	/**
	 * Callback method launch after all playNextFrame()
	 * @param NameOfAnimation 
	 * @param CurrentFrameNumber - Position of frame in all frames of sprite
	 * @param CurrentFrameIndex - Position of frame in current animation index
	 */
	public void animationCallback(String NameOfAnimation, int CurrentFrameNumber,
			int CurrentFrameIndex) {
		//To do all you want.		
	}

	// ===========================================================
    // Inner Class
    // ===========================================================
	private class AnimationDesc {
		// start stepping through the array from the beginning
		private int framerate = 7;

		private int[] array = { 0 };
		
		private int currentTileID = 0;
		
		public boolean finished = false;
		
		public boolean looped = true;

		public AnimationDesc(int framerate, int[] array, boolean looped) {
			this.setFramerate(framerate);
			this.setFrameSeq(array);
			this.looped = looped;
		}

		@SuppressWarnings("unused")
		public int getFramerate() {
			return framerate;
		}

		public void setFramerate(int framerate) {
			this.framerate = framerate;
		}

		@SuppressWarnings("unused")
		public int[] getFrameSeq() {
			return array;
		}

		public void setFrameSeq(int[] array) {
			this.array = array;
		}
		
		public void reset()	{
			finished = false;
			currentTileID = 0;
			setTile(array[currentTileID]);	
		}
				
		public void nextFrame() {
			// If play the last frame - animation finished
			if (currentTileID == array.length - 1)
				finished = true;
			
			if(currentTileID  + 1 < array.length)
				++currentTileID;
			else
				currentTileID = 0;
					
			setTile(array[currentTileID]);		
		}		
	}
}

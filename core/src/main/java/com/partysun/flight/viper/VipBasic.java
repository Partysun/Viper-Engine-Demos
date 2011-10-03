package com.partysun.flight.viper;

	import java.util.ArrayList;

	
	/**
	 * This is a useful basic Viper engine object.
	 * Both <code>VipObject</code> and <code>VipGroup</code> extend this class.
	 * Has no size, position or graphical data. 
	 * @author Yura - (@Partysun - yura.zatsepin@gmail.com) 
	 */
	public abstract class VipBasic
	{		
		// ===========================================================
        // Constants
        // ===========================================================

//		@SuppressWarnings("unused")
//		private static final String TAG = VipBasic.class.getSimpleName();
		
		// ===========================================================
        // Fields
        // ===========================================================

		/**
		 * Controls whether <code>update()</code> and <code>draw()</code> are automatically called by VipState/VipGroup.
		 */
		public boolean exists;
		/**
		 * Controls whether <code>update()</code> is automatically called by VipState/VipGroup.
		 */
		public boolean active;
		/**
		 * Controls whether <code>draw()</code> is automatically called by VipState/VipGroup.
		 */
		private boolean visible;
		/**
		 * Useful state for many game objects - "dead" (!alive) vs alive.
		 * <code>kill()</code> and <code>revive()</code> both flip this switch (along with exists, but you can override that).
		 */
		public boolean alive;
		/**
		 * An array of camera objects that this object will use during <code>draw()</code>.
		 * This value will initialize itself during the first draw to automatically
		 * point at the main camera list out in <code>ViG</code> unless you already set it.
		 * You can also change it afterward too, very flexible!
		 */
		public ArrayList<VipCamera> cameras;
		/**
		 * Setting this to true will prevent the object from appearing
		 * when the visual debug mode in the debugger overlay is toggled on.
		 */
		public boolean ignoreDrawDebug;
		
		public int _ACTIVECOUNT = 0;
		public int _VISIBLECOUNT = 0;
		
		// ===========================================================
        // Constructors
        // ===========================================================

		/**
		 * Instantiate the basic Viper object.
		 */
		public VipBasic()
		{
			exists = true;
			active = true;
			setVisible(true);
			alive = true;
			ignoreDrawDebug = false;
		}
		// ===========================================================
        // Getter/Setter
        // ===========================================================
		public boolean getVisible() {
			return visible;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}		
		// ===========================================================
        // Methods
        // ===========================================================
		
		/**
		 * Override this function to null out variables or manually call
		 * <code>destroy()</code> on class members if necessary.
		 * Don't forget to call <code>super.destroy()</code>!
		 */
		public abstract void destroy();
				
		/**
		 * Pre-update is called right before <code>update()</code> on each object in the game loop.
		 */
		public void preUpdate()
		{
			_ACTIVECOUNT++;
		}
		
		/**
		 * Override this function to update your class's position and appearance.
		 * This is where most of your game rules and behavioral code will go.
		 */
		public abstract void update();
		
		/**
		 * Post-update is called right after <code>update()</code> on each object in the game loop.
		 */
		public abstract void postUpdate();
		
		/**
		 * Override this function to control how the object is drawn.
		 * Overriding <code>draw()</code> is rarely necessary, but can be very useful.
		 * @param alpha 
		 */
		public void draw(float alpha)
		{
				_VISIBLECOUNT++;
		}
		
		/**
		 * Override this function to draw custom "debug mode" graphics to the
		 * specified camera while the debugger's visual mode is toggled on.
		 * 
		 * @param	Camera	Which camera to draw the debug visuals to.
		 */
		public void drawDebug(VipCamera Camera) {}
		
		public void drawDebug() {}		
		
		/**
		 * Handy function for "killing" game objects.
		 * Default behavior is to flag them as nonexistent AND dead.
		 * However, if you want the "corpse" to remain in the game,
		 * like to animate an effect or whatever, you should override this,
		 * setting only alive to false, and leaving exists true.
		 */
		public void kill()
		{			
			alive = false;
			exists = false;
		}
		
		/**
		 * Handy function for bringing game objects "back to life". Just sets alive and exists back to true.
		 * In practice, this function is most often called by <code>VipObject.reset()</code>.
		 */
		public void revive()
		{
			alive = true;
			exists = true;
		}
	}


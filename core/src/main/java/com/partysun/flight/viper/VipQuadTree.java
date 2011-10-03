package com.partysun.flight.viper;

/**
 * A fairly generic quad tree structure for rapid overlap checks.
 * VipQuadTree is also configured for single or dual list operation.
 * You can add items either to its A list or its B list.
 * When you do an overlap check, you can compare the A list to itself,
 * or the A list against the B list.  Handy for different things!
 */
public class VipQuadTree extends VipRect {
	// ===========================================================
    // Constants
    // ===========================================================
	/**
	 * Flag for specifying that you want to add an object to the A list.
	 */
	static public int A_LIST = 0;
	/**
	 * Flag for specifying that you want to add an object to the B list.
	 */
	static public int B_LIST = 1;
	// ===========================================================
    // Fields
    // ===========================================================
	/**
	 * Controls the granularity of the quad tree.  Default is 6 (decent performance on large and small worlds).
	 */
	static public int divisions = 6;
	
	/**
	 * Whether this branch of the tree can be subdivided or not.
	 */
	protected boolean _canSubdivide;
	
	/**
	 * Refers to the internal A and B linked lists,
	 * which are used to store objects in the leaves.
	 */
	protected VipList _headA;
	/**
	 * Refers to the internal A and B linked lists,
	 * which are used to store objects in the leaves.
	 */
	protected VipList _tailA;
	/**
	 * Refers to the internal A and B linked lists,
	 * which are used to store objects in the leaves.
	 */
	

	protected VipList _headB;
	/**
	 * Refers to the internal A and B linked lists,
	 * which are used to store objects in the leaves.
	 */
	protected VipList _tailB;

	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	static protected int _min;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected VipQuadTree _northWestTree;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected VipQuadTree _northEastTree;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected VipQuadTree _southEastTree;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected VipQuadTree _southWestTree;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected float _leftEdge;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected float _rightEdge;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected float _topEdge;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected float _bottomEdge;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected float _halfWidth;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected float _halfHeight;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected float _midpointX;
	/**
	 * Internal, governs and assists with the formation of the tree.
	 */
	protected float _midpointY;
	
	// ===========================================================
    // Constructors
    // ===========================================================
	// ===========================================================
    // Methods
    // ===========================================================
}
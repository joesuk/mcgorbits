package a_000workshop2016;
import java.util.*;
/**
 * SimpleLoops serves as implementation of simple loop arrangement (their automorphic conjugacy classes) with their relative positions (with
 * respect to the nature of being concentric vs non-concentric).
 * @author Joe
 */

public class SimpleLoops {
	/**
	 * Number of self-intersections.
	 */
	public int selfIntersections;
	/**
	 * Number of loops, or number of self-intersections + 1.
	 */
	public int loopCount;
	/**
	 * List of loop words in free group.
	 */
	public ArrayList<String> loopWords = new ArrayList<String>();
	/**
	 * Take a sufficiently small simple loop homotopic to the basepoint. ordering describes the order of traversal of small loop with our
	 * object's simple loops in a fixed orientation of traversal (clockwise vs. anticlockwise).
	 */
	public int[] ordering;
	/**
	 * Cyclic permutations of ordering
	 */
	public int[][] orderingPermutations;
	
	/**
	 * Constructor
	 * @param newSI New selfIntersections
	 */
	public SimpleLoops(int newSI) {
		selfIntersections = newSI;
		loopCount = newSI+1;
	}
	
	/**
	 * Produces cyclic permutations of ordering and stores them in 2D array.
	 */
	public void cyclicPermute() {
		orderingPermutations = new int[2*loopCount][2*loopCount];
		for (int j = 0; j < 2*loopCount; j++) {
			for (int k = 0; k < 2*loopCount; k++) {
				orderingPermutations[j][k] = ordering[(k-j+10) % 10];
			}
		}
	}
	
	/**
	 * Accessor for selfIntersections
	 * @return selfIntersections
	 */
	public int getSI() {return selfIntersections;}
	
	/**
	 * Accessor for loopCount
	 * @return loopCount
	 */
	public int getLoopCount() {return loopCount;}
	
	/**
	 * Accessor for loopWords
	 * @return loopWords
	 */
	public ArrayList<String> getLoopWords() {return loopWords;}
	
	/**
	 * Accessor for ordering
	 * @return ordering
	 */
	public int[] getOrdering() {return ordering;}
	
	/**
	 * Accessor for orderingPermutations
	 * @return orderingPermutations
	 */
	public int[][] getOrderingPermutations() {return orderingPermutations;}
	
	/**
	 * Add a new loop by its word.
	 * @param word Word representation of loop to be added.
	 */
	public void addLoop(String word) {
		loopWords.add(word);
	}
	
	/**
	 * Mutator for ordering.
	 * @param newOrdering New ordering.
	 */
	public void setOrdering(int[] newOrdering) {
		ordering = newOrdering;
	}
	
	
	
	

}

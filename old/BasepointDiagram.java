package a_000workshop2016;
import java.util.*;
/**
 * BasepointDiagram serves as the implementation of a basepoint diagram or basepoint graph at a small neighborhood of the basepoint. 
 * It can be treated as a graph on some number of vertices with certain restrictions, but for our purposes, we only
 * care about the arrangement of edges. 
 * @author Joe
 *
 */
public class BasepointDiagram {
	/**
	 * Number of edges, overcounted by a factor of 2
	 */
	public int size;
	/**
	 * Describes graph at a small neighborhood by indicating which edge the traversal of the curve passes through next
	 * after passing through an indexed edge. This is without respect to orientation. So, precondition is that this 
	 * is symmetric so travelInd[i]=j iff travelInd[j]=i
	 */
	public int[] travelInd = new int[size]; 

	
	/**
	 * Constructor
	 * @param newSize New size
	 */
	public BasepointDiagram(int newSize) {
		size = newSize;
	}
	
	/**
	 * Constructor with travel indices for edges.
	 * @param newSize New size
	 * @param ind New travelInd
	 */
	public BasepointDiagram(int newSize, int[] ind) {
		if (newSize != ind.length) {
			return;
		}
		
		travelInd = new int[newSize];
		for (int i = 0; i < ind.length; i++) {
			travelInd[i]=ind[i];
		}
	}
	
	/**
	 * Traverse to next edge in fixed orientation.
	 * @param index The edge input
	 * @return The next edge in the traversal
	 */
	int getNext(int index) {
		return travelInd[index];
	}
	
	/**
	 * Traverse to previous edge in fixed orientation.
	 * @param index The edge input
	 * @return The previous edge in the traversal
	 */
	int getPrev(int index) {
		for (int i = 0; i < travelInd.length; i++) {
			if (travelInd[i] == index) {
				return i;
			}
		}
		return size;
	}
	
	/**
	 * Mutator for travelInd
	 * @param newTravelInd new travelInd
	 */
	public void setTravelInd(int[] newTravelInd) {
		travelInd = newTravelInd;
		size = newTravelInd.length;
	}
	
	/**
	 * Mutator for size
	 * @param newSize new size
	 */
	public void setSize(int newSize) {
		size = newSize;
	}
	
	/**
	 * Traverse basepoint graph's closed curve with given ordering
	 * @param selfIntersections Number of self-intersections
	 * @param ordering Describes edge configuration outside of neighborhood
	 * @return order of traversal for loops with inverses
	 */
	public ArrayList<Integer> traverse(int selfIntersections, int[] ordering) {
		ArrayList<Integer> travelPath = new ArrayList<Integer>();
		ArrayList<Integer> positionList = new ArrayList<Integer>();
		for (int k = 0; k < ordering.length; k++) {
			positionList.add(ordering[k]);
		}
		travelPath.add(1);
		int current = 1;
		int count = selfIntersections;
		int indexStart = 0; int indexEnd = 0;
		while (count>0) {
			indexStart = positionList.indexOf(current*-1);
			indexEnd = getNext(indexStart);
			current = ordering[indexEnd];
			travelPath.add(current);
			count--;
		}
		
		return travelPath;
	}
	
	/**
	 * size accessor
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * travelInd accessor
	 * @return
	 */
	public int[] getTravelInd() {
		return travelInd;
	}
	
	

}

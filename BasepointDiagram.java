package a_000workshop2016;
import java.util.*;

public class BasepointDiagram {
	public int size;
	public int[] travelInd = new int[size];
	
	public BasepointDiagram(int newSize) {
		size = newSize;
	}
	
	
	public BasepointDiagram(int newSize, int[] ind) {
		if (newSize != ind.length) {
			return;
		}
		
		travelInd = new int[newSize];
		for (int i = 0; i < ind.length; i++) {
			travelInd[i]=ind[i];
		}
	}
	
	int getNext(int index) {
		return travelInd[index];
	}
	
	int getPrev(int index) {
		for (int i = 0; i < travelInd.length; i++) {
			if (travelInd[i] == index) {
				return i;
			}
		}
		return size;
	}
	
	public void setTravelInd(int[] newTravelInd) {
		travelInd = newTravelInd;
		size = newTravelInd.length;
	}
	
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
	
	

}

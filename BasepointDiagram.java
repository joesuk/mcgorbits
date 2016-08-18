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
	
	

}

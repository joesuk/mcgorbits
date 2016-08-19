package a_000workshop2016;
import java.util.*;


public class SimpleLoops {
	public int selfIntersections;
	public int loopCount;
	public ArrayList<String> loopWords = new ArrayList<String>();
	public int[] ordering;
	public int[][] orderingPermutations;
	
	public SimpleLoops(int newSI) {
		selfIntersections = newSI;
		loopCount = newSI+1;
	}
	
	public void cyclicPermute() {
		orderingPermutations = new int[2*loopCount][2*loopCount];
		for (int j = 0; j < 2*loopCount; j++) {
			for (int k = 0; k < 2*loopCount; k++) {
				orderingPermutations[j][k] = ordering[(k-j+10) % 10];
			}
		}
	}
	
	public int getSI() {return selfIntersections;}
	
	public int getLoopCount() {return loopCount;}
	
	public ArrayList<String> getLoopWords() {return loopWords;}
	
	public int[] getOrdering() {return ordering;}
	
	public int[][] getOrderingPermutations() {return orderingPermutations;}
	
	public void addLoop(String word) {
		loopWords.add(word);
	}
	
	public void setOrdering(int[] newOrdering) {
		ordering = newOrdering;
	}
	
	
	
	

}

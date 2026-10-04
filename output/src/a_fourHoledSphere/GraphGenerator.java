package a_fourHoledSphere;
import java.util.Iterator;
import java.util.*;

import a_000workshop2016.*;

public class GraphGenerator {
	
	
	public static int[][][] generate(int size) {
		
		
		
		int genCount = factorial(loopCount);
		
		for(Iterator<int[]> it = new PermIterator(loopCount); it.hasNext(); ) {
			int[] reOrdering = it.next();
			
			
			
			
	         print(loopCount, it.next());
	     }
		
		
	}
	
	public static int[] getGraph(BasepointDiagram bp) {
		int size = bp.getSize();
		
		
		
		
		
		
		
		
		
		
		
		int[] travelInd = bp.getTravelInd();
		int maxInd = 0; int max = 0;
		for (int i = 0; i < travelInd.length; i++) {
			if (Math.abs(i-travelInd[i]) > max) {
				max = Math.abs(i-travelInd[i]);
				maxInd = i;
			}
		}
		int lower = Math.min(travelInd[maxInd],travelInd[travelInd[maxInd]]);
		int upper = Math.max(travelInd[maxInd],travelInd[travelInd[maxInd]]);
		int verticalCount = 0;
		ArrayList<Integer> horizontals = new ArrayList<Integer>();
		for (int i = 0; i < travelInd.length; i++) {
			int posLow = Math.min(travelInd[i],i);
			int posHigh = Math.max(travelInd[i],i);
			if (posLow > lower && posHigh < upper) {
				horizontals.add(i);
			} else if (posLow < lower && posHigh > lower && posHigh < upper) {
				verticalCount++;
			} else if (posLow > lower && posLow < upper && posHigh > upper) {
				verticalCount++;
			}
		}
		
		
		
		
	}
	
	public static int factorial(int n) {
		if (n>1) return factorial(n-1);
		else return 1;
	}

}

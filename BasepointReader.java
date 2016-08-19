package a_000workshop2016;
import a_000workshop2016.*;
import a_orbits.Mcg;
import a_torusPantsHL.Parametrizations;
import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * BasepointReader stores the list of possible basepoint diagrams and simple loop configurations for a given self-intersection number by
 * reading them from files. The information in the files should be determined beforehand based on the lemmas given in the paper.
 * @author Joe
 *
 */
public class BasepointReader {
	/**
	 * Number of self intersections.
	 */
	public int selfIntersections;
	/**
	 * List of basepoint diagrams.
	 */
	public ArrayList<BasepointDiagram> diagrams = new ArrayList<BasepointDiagram>();
	/**
	 * List of simple loop arrangements.
	 */
	public ArrayList<SimpleLoops> simples = new ArrayList<SimpleLoops>();
	
	/**
	 * Basepoint diagram accessor
	 * @return diagrams
	 */
	public ArrayList<BasepointDiagram> getDiagrams() {
		return diagrams;
	}
	
	/**
	 * Simple loop arrangement accessor
	 * @return simples
	 */
	public ArrayList<SimpleLoops> getSimples() {
		return simples;
	}
	
	/**
	 * Self intersection number mutator
	 * @param newSI New selfIntersections
	 */
	public void setSI(int newSI) {
		selfIntersections = newSI;
	}
	
	/**
	 * Read basepoint diagrams using Java I/O
	 * @param basepointFile Name of file storing basepoint diagram information.
	 * @throws IOException
	 */
	public void readDiagrams(String basepointFile) throws IOException {
		BufferedReader basepointReader = new BufferedReader(new FileReader(basepointFile));
		try {
			int[] travelInd = new int[(selfIntersections+1)*2];
			String num = ""; int i = 0;
			while ((num = basepointReader.readLine()) != null) {
				travelInd[i] = Integer.parseInt(num);
				i++;
				if (i == (selfIntersections+1)*2) {
					BasepointDiagram diagram = new BasepointDiagram((selfIntersections+1)*2);
					diagram.setTravelInd(travelInd);
					diagrams.add(diagram);
					i = 0;
				}
			}
		}
		finally {
			basepointReader.close();
		}
	}
		
	/**
	 * Read simple loop arrangements using Java I/O
	 * @param loopFile Name of file storing simple loop arranagements.
	 * @throws IOException
	 */
	public void readLoops(String loopFile) throws IOException {
		BufferedReader loopReader = new BufferedReader(new FileReader(loopFile));
		try {
			String line = loopReader.readLine();
			int loopCount = selfIntersections+1;
			SimpleLoops simpleLoops = new SimpleLoops(selfIntersections);

		    while (line != null && loopCount > 0) {
		    	StringBuilder sb = new StringBuilder(line);
		    	if (line.length() < 5)  {
		    		simpleLoops.addLoop(sb.toString().replaceAll("\\s+",""));
		    	}
		    	loopCount--;
		        line = loopReader.readLine();
		    }
		    
		    String[] orderingString = line.split(",\\s+");
		    simpleLoops.setOrdering(new int[orderingString.length]);
		    int[] ordering = simpleLoops.getOrdering();
		    for (int i = 0; i < ordering.length; i++) {
		    	ordering[i] = Integer.parseInt(orderingString[i]);
		    }
		    			
		    simpleLoops.cyclicPermute();
		    simples.add(simpleLoops);
		    
		    
		}
		finally {
			loopReader.close();
		}
	}
	

}

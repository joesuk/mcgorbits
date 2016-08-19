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

public class BasepointReader {
	public int selfIntersections;
	public ArrayList<BasepointDiagram> diagrams = new ArrayList<BasepointDiagram>();
	public ArrayList<SimpleLoops> simples = new ArrayList<SimpleLoops>();
	
	
	public ArrayList<BasepointDiagram> getDiagrams() {
		return diagrams;
	}
	
	public ArrayList<SimpleLoops> getSimples() {
		return simples;
	}
	
	public void setSI(int newSI) {
		selfIntersections = newSI;
	}
	
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

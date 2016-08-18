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
	final static int selfIntersections = 4;
	final static ArrayList<BasepointDiagram> diagrams = new ArrayList<BasepointDiagram>();
	final static ArrayList<String> loopWords = new ArrayList<String>();
	
	
	public static void main(String[] args) {
		try {
			readDiagrams("basepoints.txt");
			readLoops("si3.txt");
		} catch (Exception e) {}
	}
	
	public static void readDiagrams(String basepointArrangement) throws IOException {
		BufferedReader basepointReader = new BufferedReader(new FileReader(basepointArrangement));
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
		
	
	public static void readLoops(String loopArrangement) throws IOException {
		BufferedReader loopReader = new BufferedReader(new FileReader(loopArrangement));
		try {
			String line = loopReader.readLine();
			int loopCount = selfIntersections+1;

		    while (line != null && loopCount > 0) {
		    	StringBuilder sb = new StringBuilder(line);
		    	if (line.length() < 5)  {
		    		loopWords.add(sb.toString().replaceAll("\\s+",""));
		    	}
		    	loopCount--;
		        line = loopReader.readLine();
		    }
		    
		    String[] orderingString = line.split(",\\s+");
		    int[] ordering = new int[orderingString.length];
		    for (int i = 0; i < ordering.length; i++) {
		    	ordering[i] = Integer.parseInt(orderingString[i]);
		    }
		    
		    int[][] positions = new int[2*loopCount][2*loopCount];
			for (int j = 0; j < 2*loopCount; j++) {
				for (int k = 0; k < 2*loopCount; k++) {
					positions[j][k] = ordering[(k-j+10) % 10];
				}
				
				makeWords(positions[j], loopWords, selfIntersections);
			}
		    
		    
		}
		finally {
			loopReader.close();
		}
	}
	
	public static void makeWords(int[] positions, ArrayList<String> loopWords, int si) {
		
		for (BasepointDiagram diagram : diagrams) {
			
		}
		
		
		ArrayList<Integer> travelPath = new ArrayList<Integer>();
		ArrayList<Integer> positionList = new ArrayList<Integer>();
		for (int k = 0; k < positions.length; k++) {
			positionList.add(positions[k]);
		}
		StringBuilder word = new StringBuilder();
		travelPath.add(1);
		int current = 1;
		int count = si;
		int indexStart = 0; int indexEnd = 0;
		while (count>0) {
			indexStart = positionList.indexOf(current*-1);
			indexEnd = getNext(indexStart);
			current = position[indexEnd];
			travelPath.add(current);
			count--;
		}
		
		
		
		
		Set<Integer> set = new HashSet<Integer>(travelPath);
		if (set.size() < travelPath.size() || travelPath.size() != 5) {
			// System.out.println("\n" + reduceWord(word) + " NOT A WORD");
			return;
		} else {
			ArrayList<String> thisCase = cases.get(i);
			for(int j = 0; j < travelPath.size(); j++) {
				if (travelPath.get(j) > 0) {
					word.append(thisCase.get(travelPath.get(j)-1));
				} else if (travelPath.get(j) < 0) {
					word.append(inverse(thisCase.get(-1*travelPath.get(j)-1)));
				}
			}
			
			
			double[][][] m=Parametrizations.param4();
			double[][] m1={m[1][1],m[1][2]};
			SurfaceWord swTorus=Parametrizations.torus(m1);
			
			Word i1 = new Word(Word.fromString(reduceWord(word), 4));
			
			Word i2 = new Word(Word.fromString(reduceWord(inverse(word.toString())),4));
			
			Curve x11 = new Curve(i1, swTorus);
			
			Curve x12 = new Curve(i2,swTorus);

	}

}

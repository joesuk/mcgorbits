package a_000workshop2016;
import a_000workshop2016.*;
import a_torusPantsHL.TestsTorus;
import a_torusPantsHL.Parametrizations;
import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;
import a_orbits.DriverOrbits2016;
import a_orbits.Mcg;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class BasepointAnalysis {
	public int selfIntersections;
	public BasepointReader bp;
	public Set<Curve> curves = new HashSet<Curve>();
	public Set<String> words = new HashSet<String>();
	public Set<Curve> extraCurves = new HashSet<Curve>();
	
	public BasepointAnalysis(BasepointReader newBP, int newSI) {
		bp = newBP;
		selfIntersections = newSI;
	}
	
	public void analyze() {
		for (BasepointDiagram diagram : bp.getDiagrams()) {
			for (SimpleLoops simples : bp.getSimples()) {
				analyzeDiagram(diagram,simples);
			}
		}
	}
	
	public void analyzeDiagram(BasepointDiagram diagram, SimpleLoops simples) {
		for (int i = 0; i < simples.getOrdering().length; i++) {
			analyzeOrdering(simples.getOrderingPermutations()[i], diagram, simples);
		}		
	}
	
	public void analyzeOrdering(int[] ordering, BasepointDiagram diagram, SimpleLoops simples) {

		ArrayList<Integer> travelPath = diagram.traverse(selfIntersections, ordering);
		StringBuilder word = new StringBuilder();
		
		
		Set<Integer> set = new HashSet<Integer>(travelPath);
		if (set.size() < travelPath.size() || travelPath.size() != simples.getLoopCount()) {
			System.out.println("\n" + reduceWord(word) + " NOT A WORD");
			return;
		} else {
			ArrayList<String> thisCase = simples.getLoopWords();
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


			if (x11.selfIntersection() == selfIntersections) {
				curves.add(x11);
				words.add(x11.toString());
				System.out.println("\n"+x11.toString() + " self-int number  " + x11.selfIntersection() + " word length " + x11.toString().length());
			}
		}
	}
	
	
	static String inverse(String word) {
		
		// word = reduceWord(new StringBuilder(word));
		StringBuilder inverse = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == 'a') {
				inverse.insert(0,'A');
			} else if (word.charAt(i) == 'A') {
				inverse.insert(0,'a');
			} else if (word.charAt(i) == 'b') {
				inverse.insert(0,'B');
			} else if (word.charAt(i) == 'B') {
				inverse.insert(0,'b');
			}
		}
			
		return inverse.toString();
	}
	
	static String reduceWord(StringBuilder word) {
		int max = word.length();
		while (max > 0) {
			String newWord = word.toString().replaceAll("aA","").replaceAll("Aa","").replaceAll("bB","").replaceAll("Bb","");
			word = new StringBuilder(newWord);
			max--;
		}
		max = word.length();
		while (max > 0) {
			if (word.charAt(0) == 'a' && word.charAt(word.length()-1) == 'A') {
				word.deleteCharAt(word.length()-1);
				word.deleteCharAt(0);
			} else if (word.charAt(0) == 'A' && word.charAt(word.length()-1) == 'a') {
				word.deleteCharAt(word.length()-1);
				word.deleteCharAt(0);
			} else if (word.charAt(0) == 'b' && word.charAt(word.length()-1) == 'B') {
				word.deleteCharAt(word.length()-1);
				word.deleteCharAt(0);
			} else if (word.charAt(0) == 'B' && word.charAt(word.length()-1) == 'b') {
				word.deleteCharAt(word.length()-1);
				word.deleteCharAt(0);
			}
			max--;
		}
		return word.toString();
	}
	
	static String reduceWord(String wordString) {
		StringBuilder word = new StringBuilder(wordString);
		int max = word.length();
		while (max > 0) {
			String newWord = word.toString().replaceAll("aA","").replaceAll("Aa","").replaceAll("bB","").replaceAll("Bb","");
			word = new StringBuilder(newWord);
			max--;
		}
		max = word.length();
		while (max > 0) {
			if (word.charAt(0) == 'a' && word.charAt(word.length()-1) == 'A') {
				word.deleteCharAt(word.length()-1);
				word.deleteCharAt(0);
			} else if (word.charAt(0) == 'A' && word.charAt(word.length()-1) == 'a') {
				word.deleteCharAt(word.length()-1);
				word.deleteCharAt(0);
			} else if (word.charAt(0) == 'b' && word.charAt(word.length()-1) == 'B') {
				word.deleteCharAt(word.length()-1);
				word.deleteCharAt(0);
			} else if (word.charAt(0) == 'B' && word.charAt(word.length()-1) == 'b') {
				word.deleteCharAt(word.length()-1);
				word.deleteCharAt(0);
			}
			max--;
		}
		return word.toString();
	}
	
	public boolean contains(Curve x11) {
		Iterator it = curves.iterator();
		while (it.hasNext()) {
			Curve curve = (Curve)it.next();
			if (curve.toString().equals(x11.toString())) {
				return true;
			} else if (isCycled(curve.toString(),x11.toString())) {
				return true;
			}
		}
		return false;
	}
	
	static boolean isCycled(String a, String b) {
		a = reduceWord(a);
		b = reduceWord(b);
		a = a.replace('A','c');
		a = a.replace('B','d');
		b = b.replace('A','c');
		b = b.replace('B','d');
		if (a.length() != b.length()) {
			return false;
		}
		
		int max = Math.max(a.length(),b.length());
		String temp = a;
		while (max>0) {
			StringBuilder newTemp = new StringBuilder(temp);
			char j = temp.charAt(0);
			newTemp.deleteCharAt(0);
			newTemp.append(j);
			if (newTemp.toString().equals(b)) {
				return true;
			}
			max--;
			temp = newTemp.toString();
		}
		return false;
		
	}

	void printCases() {
		String fileName = "Basepoint Analysis for " + selfIntersections + " Self Intersections";
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			for (int i = 1; i < 21; i++) {
				Iterator it = words.iterator();
				while (it.hasNext()) {
					String word = (String)it.next();
					if (word.length() == i) {
						writer.println("\n"+word + " : " + word.length());
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	static ArrayList<Integer> invertTravel(ArrayList<Integer> travelPath) {
		ArrayList<Integer> newPath = new ArrayList<Integer>();
		for (int i = travelPath.size()-1; i >-1; i--) {
			
		}
		return newPath;
	}
	
	
	static void clearOrbit(ArrayList<String> toRemove, ArrayList<String> ourCases, String word) {

		String curve = word;
		//The set of curves
		HashSet<String> hs = new HashSet<String>();
		
		
		hs.add(word);
		// hs.add(curve2);
		// hs.add(curve3);
		
		//Maximal word  length of the (classes of) curves to find
		int maxWL=20;
		//
		int boundWLOrbit=maxWL+20;
		// how many times we apply the generators.
		int times = 15; 
		
		
		//directory to store results
				String dir=curve+times+"_orbitERASE/";	
				statics.CreateDirectory.create(dir);	
				
		// the set of curves
	    hs = Mcg.findOrbits(times, hs, dir, maxWL);

	    //count different classes
	  
	    int[][] total1 = new int[boundWLOrbit+100][2];

	    //Store the results
	    Iterator it = hs.iterator();
	    
	    while(it.hasNext()){
	    	String s1 = (String) it.next();
	    	
	    	if (ourCases.contains(s1) && !s1.equals(word)) {
	    		toRemove.add(s1);
	    	}
	    	
	    	
	    }
	}
	

}

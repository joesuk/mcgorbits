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
/**
 * Performs basepoint analysis to find exhaustive list of words for curves of a certain self-intersetion number, up to cyclic reduction.
 * @author Joe
 *
 */
public class BasepointAnalysis {
	/**
	 * Self intersection number.
	 */
	public int selfIntersections;
	/**
	 * BasepointReader storing information about our basepoint diagrams and simple loop arrangements.
	 */
	public BasepointReader bp;
	/**
	 * Set of closed curves.
	 */
	public Set<Curve> curves = new HashSet<Curve>();
	/**
	 * Set of reduced words representing closed curves.
	 */
	public Set<String> words = new HashSet<String>();
	/**
	 * Auxiliary set of curves not essential to our analysis.
	 */
	public Set<Curve> extraCurves = new HashSet<Curve>();
	
	/**
	 * Constructor
	 * @param newBP New bp
	 * @param newSI New selfIntersections
	 */
	public BasepointAnalysis(BasepointReader newBP, int newSI) {
		bp = newBP;
		selfIntersections = newSI;
	}
	
	/**
	 * Peforms basepoint analysis.
	 */
	public void analyze() {
		for (BasepointDiagram diagram : bp.getDiagrams()) {
			for (SimpleLoops simples : bp.getSimples()) {
				analyzeDiagram(diagram,simples);
			}
		}
	}
	
	/**
	 * Performs basepoint analysis for a given loop arrangement and basepoint diagram.
	 * @param diagram Basepoint diagram to use
	 * @param simples Simple loop arrangement to use
	 */
	public void analyzeDiagram(BasepointDiagram diagram, SimpleLoops simples) {
		for (int i = 0; i < simples.getOrdering().length; i++) {
			analyzeOrdering(simples.getOrderingPermutations()[i], diagram, simples);
		}		
	}
	
	/**
	 * Performs basepoint analysis for one specific cyclic permutation of simple loop
	 * arrangement's ordering (see SimpleLoops.cyclicPermute()).
	 * @param ordering Specific cyclic permutation of ordering.
	 * @param diagram Basepoint diagram to use
	 * @param simples Simple loop arrangement to use
	 */
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


			if (x11.selfIntersection() == selfIntersections) { // We use Lusztig's algorithm for selfIntersections() method.
				curves.add(x11);
				words.add(x11.toString());
				System.out.println("\n"+x11.toString() + " self-int number  " + x11.selfIntersection() + " word length " + x11.toString().length());
			}
		}
	}
	
	/**
	 * Finds free group inverse of given word.
	 * @param word Word to find inverse of.
	 * @return Inverse word.
	 */
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
	
	/**
	 * Cyclically reduces word (conjugacy class).
	 * @param word Word to be reduced.
	 * @return Reduced word.
	 */
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
	
	/**
	 * Cyclically reduces word (conjugacy class).
	 * @param word Word to be reduced.
	 * @return Reduced word.
	 */
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
	
	

	/**
	 * Prints exhaustive list of words after performing basepoint analysis in a text file.
	 */
	public void printCases() {
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
	
	/*
	static ArrayList<Integer> invertTravel(ArrayList<Integer> travelPath) {
		ArrayList<Integer> newPath = new ArrayList<Integer>();
		for (int i = travelPath.size()-1; i >-1; i--) {
			
		}
		return newPath;
	}
	*/
	
	
	

}

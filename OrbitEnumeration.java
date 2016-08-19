package a_000workshop2016;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import a_orbits.Mcg;

/**
 * Class for generating new elements of an orbit for a given word using the elementary Nielsen transformations. For an exhaustive list
 * of words produced by basepoint analysis, this can be used to eliminate non-distinct orbits by showing their representative words on the
 * list are some number of elementary transformations away from each other within the same orbit.
 * @author Joe
 *
 */
public class OrbitEnumeration {
	/**
	 * List of words to be used.
	 */
	public ArrayList<String> words = new ArrayList<String>();
	/**
	 * Words generated within orbits of our list that we want to remove.
	 */
	public ArrayList<String> toRemove = new ArrayList<String>();
	/**
	 * Maximum distance between words (in terms of iterations of elementary transformations) we want to check.
	 */
	public int distance;
	/**
	 * Maximum word length.
	 */
	public int maxWL;
	/**
	 * Maximum bound on the word length of the orbit.
	 */
	public int boundWLOrbit;
	
	
	/**
	 * Generates orbit elements which are duplicated in our list of words.
	 * @param fileName Name of file containing list of words.
	 */
	public void generate(String fileName) {
		
		try {
			readFile(fileName);
		} catch (Exception e) {}
		ArrayList<String> toRemove = new ArrayList<String>();
		for (String str : words) {
		    enumerate(str);
		}
	}
	
	
	/**
	 * Reduce our list of words by removing those within the same orbit up to our distance and max word length.
	 */
	public void remove() {
		words.removeAll(toRemove);
	}
	
	/**
	 * Generate words in the orbit for a given word
	 * @param word Word to be used as the initial point in the orbit.
	 */
	public void enumerate(String word) {

		String curve = word;
		//The set of curves
		HashSet<String> hs = new HashSet<String>();
		
		
		hs.add(word);
		// hs.add(curve2);
		// hs.add(curve3);
		
		//Maximal word  length of the (classes of) curves to find
		// int boundWLOrbit=maxWL+20;
		// how many times we apply the generators.
		int times = distance; 
		
		
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
	    	
	    	if (words.contains(s1) && !s1.equals(word)) {
	    		toRemove.add(s1);
	    	}
	    	
	    	
	    }
	}
	
	/**
	 * Check if two words are cyclic permutations of each other
	 * @param a First word to compare
	 * @param b Second word to compare
	 * @return True if a and b are cyclic permutations, false otherwise.
	 */
	public boolean isCycled(String a, String b) {
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
	 * Reads file containing list of words.
	 * @param fileName Name of file to be read
	 * @throws IOException
	 */
	public void readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			String line = br.readLine();

		    while (line != null) {
		    	StringBuilder sb = new StringBuilder(line);
		    	if (line.length() >5)  {
		    		words.add(sb.delete(sb.length()-4,sb.length()).toString().replaceAll("\\s+",""));
		    	}
		        line = br.readLine();
		    }
		    
		}
		finally {
			br.close();
		}
	}
	
}

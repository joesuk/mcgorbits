package a_000workshop2016;
import a_000workshop2016.*;
import a_orbits.Mcg;
import boundary.Word;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
/**
 * MinimalWord contains methods to test whether a word is minimal in its orbit (with respect to word length) and, if not,
 * finds a representative minimal element in its orbit. We do this by using the elementary generators of the automorphism 
 * subgroup which can reduce word length, given by Whitehead in the early 20th Century.
 * @author Joe
 *
 */
public class MinimalWord {
	/**
	 * List of words to check.
	 */
	public Set<String> words = new HashSet<String>();
	/**
	 * List of 
	 */
	public Set<String> torusWords = new HashSet<String>();
	/**
	 * Self Intersection number.
	 */
	public int selfIntersections = -1;
	
	public MinimalWord(int newSI) {
		selfIntersections = newSI;
	}
	
	/**
	 * Checks if the words in a given file are minimal and provides a list of reduced words.
	 * @param fileName Name of file containing list of words.
	 */
	public void checkMinimal(String fileName) {
		
		try {
			readFile(fileName);
		} catch (Exception e) {}
		
		    
		for (String word: words) {
			torusWords.add(Abelianization.abelianize(word));
			// System.out.println(Abelianization.abelianize(word));
		}
		
		String newFile = "ba" + selfIntersections + ".minimal.txt";
		try{
			PrintWriter writer = new PrintWriter(newFile, "UTF-8");
			Iterator it = torusWords.iterator();
			while (it.hasNext()) {
				String torusWord = (String)it.next();
				writer.println(torusWord + " : ");
				Iterator it2 = words.iterator();
				while (it2.hasNext()) {
					String word = (String)it2.next();
					if (Abelianization.abelianize(word).equals(torusWord)) {
						if (isMinimal(word)) {
							writer.println(word + " : " + true);
							} else {
								writer.println(word + " : " + findMinimal(word));
							}
					}
				}
				writer.println();
			}
		
			writer.println(torusWords.size());
			writer.println();
			
			Iterator it3 = words.iterator();
		
			while (it3.hasNext()) {
				String word = (String)it3.next();
				if (isMinimal(word)) {
					writer.println(word + " : " + true);
				} else {
					writer.println(word + " : " + findMinimal(word));
				}
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		
	}
	
	/**
	 * Checks if a single word is minimal
	 * @param word Word to be checked.
	 * @return boolean True if minimal, false otherwise.
	 */
	public static boolean isMinimal(String word) {
		if (bab(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (bAb(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (BaB(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (BAB(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (bba(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (bbA(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (BBa(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (BBA(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (aab(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (aaB(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (AAb(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (AAB(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (aba(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (aBa(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (ABA(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		if (AbA(word).replaceAll("\\s+","").length() < word.length()) {return false;}
		return true;
	}
	
	/**
	 * Finds a word of minimal length in the orbit of a given word.
	 * @param word Word to be used.
	 * @return New word of minimal length.
	 */
	public static String findMinimal(String word) {
		String reducedWord = word;
		int count = 0;
		while (!isMinimal(reducedWord)) {
		String newWord = reducedWord;
		if (bab(newWord).length() < newWord.length()) {reducedWord = bab(newWord);} else {count++;}
		if (bAb(newWord).length() < newWord.length()) {reducedWord = bAb(newWord);} else {count++;}
		if (BaB(newWord).length() < newWord.length()) {reducedWord = BaB(newWord);} else {count++;}
		if (BAB(newWord).length() < newWord.length()) {reducedWord = BAB(newWord);} else {count++;}
		if (bba(newWord).length() < newWord.length()) {reducedWord = bba(newWord);} else {count++;}
		if (bbA(newWord).length() < newWord.length()) {reducedWord = bbA(newWord);} else {count++;}
		if (BBa(newWord).length() < newWord.length()) {reducedWord = BBa(newWord);} else {count++;}
		if (BBA(newWord).length() < newWord.length()) {reducedWord = BBA(newWord);} else {count++;}
		if (aab(newWord).length() < newWord.length()) {reducedWord = aab(newWord);} else {count++;}
		if (aaB(newWord).length() < newWord.length()) {reducedWord = aaB(newWord);} else {count++;}
		if (AAb(newWord).length() < newWord.length()) {reducedWord = AAb(newWord);} else {count++;}
		if (AAB(newWord).length() < newWord.length()) {reducedWord = AAB(newWord);} else {count++;}
		if (aba(newWord).length() < newWord.length()) {reducedWord = aba(newWord);} else {count++;}
		if (aBa(newWord).length() < newWord.length()) {reducedWord = aBa(newWord);} else {count++;}
		if (ABA(newWord).length() < newWord.length()) {reducedWord = ABA(newWord);} else {count++;}
		if (AbA(newWord).length() < newWord.length()) {reducedWord = AbA(newWord);} else {count++;}
		if (count > 100000) {
			break;
		}
		count = 0;
		}
		
		return reducedWord;
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
		    	words.add(line.replaceAll("\\s+",""));
		        line = br.readLine();
		    }
		}
		finally {
			br.close();
		}
	}
	
	
	

	/**
	 * Takes a word s
	 *  replaces b->ab, B->BA
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String bab(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a' || s.charAt(h)=='A') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='b') answer = answer+ "ab";
			else answer= answer +"BA";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces b->Ab, B->Ba
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String bAb(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a' || s.charAt(h)=='A') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='b') answer = answer+ "Ab";
			else answer= answer +"Ba";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces B->aB, b->bA
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String BaB(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a' || s.charAt(h)=='A') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='B') answer = answer+ "aB";
			else answer= answer +"bA";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces B->AB, b->ba
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String BAB(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a' || s.charAt(h)=='A') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='B') answer = answer+ "AB";
			else answer= answer +"ba";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces b->ba, B->AB
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String bba(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a' || s.charAt(h)=='A') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='b') answer = answer+ "ba";
			else answer= answer +"AB";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces b->bA, B->aB
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String bbA(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a' || s.charAt(h)=='A') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='b') answer = answer+ "bA";
			else answer= answer +"aB";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces B->Ba, b->Ab
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String BBa(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a' || s.charAt(h)=='A') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='B') answer = answer+ "Ba";
			else answer= answer +"Ab";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces B->BA, b->ab
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String BBA(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a' || s.charAt(h)=='A') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='B') answer = answer+ "BA";
			else answer= answer +"ab";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces a->ab, A->BA
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String aab(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='b' || s.charAt(h)=='B') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='a') answer = answer+ "ab";
			else answer= answer +"BA";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces a->aB, A->bA
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String aaB(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='b' || s.charAt(h)=='B') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='a') answer = answer+ "aB";
			else answer= answer +"bA";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces A->Ab, a->Ba
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String AAb(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='b' || s.charAt(h)=='B') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='A') answer = answer+ "Ab";
			else answer= answer +"Ba";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces A->AB, a->ba
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String AAB(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='b' || s.charAt(h)=='B') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='A') answer = answer+ "AB";
			else answer= answer +"ba";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces a->ba, A->AB
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String aba(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='b' || s.charAt(h)=='B') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='a') answer = answer+ "ba";
			else answer= answer +"AB";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces a->Ba, A->Ab
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String aBa(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='b' || s.charAt(h)=='B') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='a') answer = answer+ "Ba";
			else answer= answer +"Ab";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces A->bA, a->aB
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String AbA(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='b' || s.charAt(h)=='B') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='A') answer = answer+ "bA";
			else answer= answer +"aB";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	/**
	 * Takes a word s
	 *  replaces A->BA, a->ab
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String ABA(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='b' || s.charAt(h)=='B') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='A') answer = answer+ "BA";
			else answer= answer +"ab";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}
	
	public void showWords() {
		Iterator it = words.iterator();
		while (it.hasNext()) {
			String word = (String)it.next();
			System.out.println(word);
		}
	}
		
		
}


package a_000workshop2016;
import a_000workshop2016.*;
import a_orbits.Mcg;
import boundary.Word;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MinimalWord {
	final static ArrayList<String> words = new ArrayList<String>();
	final static ArrayList<String> torusWords = new ArrayList<String>();
	
	public static void checkMinimal(String fileName) {
		
		try {
			readFile(fileName);
		} catch (Exception e) {System.out.println("woah");}
		    
		for (String word: words) {
			if (!torusWords.contains(reduceTorus(word))) {
				torusWords.add(reduceTorus(word));
			}
		}
		
		for (String torusWord : torusWords) {
			System.out.println(torusWord + " : ");
			for (String word : words) {
				if (reduceTorus(word).equals(torusWord)) {
					if (isMinimal(word)) {
						System.out.println(word + " : " + true);
						} else {
							System.out.println(word + " : " + findMinimal(word));
						}
				}
			}
			System.out.println();
		}
		
		System.out.println(torusWords.size());
		System.out.println();
		
		for (String word: words) {
			if (isMinimal(word)) {
			System.out.println(word + " : " + true);
			} else {
				System.out.println(word + " : " + findMinimal(word));
			}
		}
		    
		
	}
	
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
		count = 0;
		}
		
		return reducedWord;
	}
	
	public static void readFile(String fileName) throws IOException {
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
	
	
	public static String reduceTorus(String originalWord) {
		String word = originalWord;
		int aCount = 0; int bCount = 0; int ACount = 0; int BCount = 0;
		for( int i=0; i<word.length(); i++ ) {
		    if (word.charAt(i) == 'a') {
		    	aCount++;
		    } else if (word.charAt(i) == 'b') {
		    	bCount++;
		    } else if (word.charAt(i) == 'A') {
		    	ACount++;
		    } else if (word.charAt(i) == 'B') {
		    	BCount++;
		    }
		}
		// System.out.println(aCount + " " + bCount + " " + ACount + " " + BCount);
		
		if (aCount == ACount) {
			word = word.replaceAll("a","").replaceAll("A","");
		} else if (aCount > ACount && ACount != 0) {
			word = word.replaceAll("A","");
			int newCount = 0; int i = 0;
			while (newCount < ACount && i < word.length()) {
				if (word.charAt(i) == 'a') {
					StringBuilder reduced = new StringBuilder(word);
					reduced.deleteCharAt(i);
					word = reduced.toString();
					newCount++;
				}
				i++;
			}
		} else if (ACount > aCount && aCount != 0) {
			word = word.replaceAll("a","");
			int newCount = 0; int i = 0;
			while (newCount < aCount && i < word.length()) {
				if (word.charAt(i) == 'A') {
					StringBuilder reduced = new StringBuilder(word);
					reduced.deleteCharAt(i);
					word = reduced.toString();
					newCount++;
				}
				i++;
			}
		}
		
		if (bCount == BCount) {
			word = word.replaceAll("b","").replaceAll("B","");
		} else if (bCount > BCount && BCount != 0) {
			word = word.replaceAll("B","");
			int newCount = 0; int i = 0;
			while (newCount < ACount && i < word.length()) {
				if (word.charAt(i) == 'b') {
					StringBuilder reduced = new StringBuilder(word);
					reduced.deleteCharAt(i);
					word = reduced.toString();
					newCount++;
				}
				i++;
			}
		} else if (BCount > bCount && bCount != 0) {
			word = word.replaceAll("b","");
			int newCount = 0; int i = 0;
			while (newCount < bCount && i < word.length()) {
				if (word.charAt(i) == 'B') {
					StringBuilder reduced = new StringBuilder(word);
					reduced.deleteCharAt(i);
					word = reduced.toString();
					newCount++;
				}
				i++;
			}
		}
		
		return word.replaceAll("\\s+","");
		
		
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
		
		
}


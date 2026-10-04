package simpleIntersectionDat;

import java.util.*;
import java.io.*;

public class intersection {
	/* The method simpleIntersectionEquiv receives 2 words, each consisting of a, b, and c,
	and indicates whether they are simple intersection equivalent
	Here a, b, c are counter-clockwise loops from a point p around the three holes of the pants
	You can (but don't have to) use numbers to indicate repeated letters.
	For example, you can enter a2B3c or a2BB2c instead of aaBBBc.
	Big numbers like a10b30 are fine. However, negative numbers are NOT allowed.
	Use A, B, and C for a^{-1}, b^{-1}, and c^{-1}, respectively. */
	
	// Here is the main method for test purposes.
	public static void main (String [] args) throws ClassNotFoundException, IOException {
		
		
		/* This method only work for curves of the form
		 * a^x1 b^y1 a^x2 b^y2 ... a^xn b^yn
		 * where n is at most 4, and all xj and yj are between [-4,4]
		 * 
		 * The input does not have to be in that form, but the reduced word does. 
		 */
		
		/* Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter the a word.");
		String word = keyboard.nextLine();
		keyboard.close();
		
		ArrayList<CurvePants> equivclass = PantsSimInterEqWith(word);
		*/
		
		PantsSimInterEqClass(4);
	}
				
	public static curveStandard standardize (String word, int conj){
		/* This stores a word as an object in curveStandard.java
		 * The second argument is 1 if we want to consider conjugacies, 0 otherwise.
		 */
		curveStandard standardWord = new curveStandard(); 
		int i = 0;
		while (i < word.length()){
			if (word.charAt(i) == 'a'){
				if (i == word.length()-1 || !Character.isDigit(word.charAt(i+1))){
					standardWord.addA(1);
					i = i+1;}
				else {
					int j = i+1;
					while (j < word.length()){
						if (!Character.isDigit(word.charAt(j))) { break;}
						j = j +1;}
					if (j == word.length()) {
						standardWord.addA(Integer.parseInt(word.substring(i+1)));
						break;}
					else {
						standardWord.addA(Integer.parseInt(word.substring(i+1,j)));
						i = j;}
					}
			} else if (word.charAt(i) == 'A'){
				if (i == word.length()-1 || !Character.isDigit(word.charAt(i+1))){
					standardWord.addA(-1);
					i = i+1;}
				else {
					int j = i+1;
					while (j < word.length()){
						if (!Character.isDigit(word.charAt(j))) { break;}
						j = j +1;}
					if (j == word.length()) {
						standardWord.addA(-Integer.parseInt(word.substring(i+1)));
						break;}
					else {
						standardWord.addA(-Integer.parseInt(word.substring(i+1,j)));
						i = j;}
					}
			} else if (word.charAt(i) == 'b'){
				if (i == word.length()-1 || !Character.isDigit(word.charAt(i+1))){
					standardWord.addB(1);
					i = i+1;}
				else {
					int j = i+1;
					while (j < word.length()){
						if (!Character.isDigit(word.charAt(j))) { break;}
						j = j +1;}
					if (j == word.length()) {
						standardWord.addB(Integer.parseInt(word.substring(i+1)));
						break;}
					else {
						standardWord.addB(Integer.parseInt(word.substring(i+1,j)));
						i = j;}
					}
			} else if (word.charAt(i) == 'B'){
				if (i == word.length()-1 || !Character.isDigit(word.charAt(i+1))){
					standardWord.addB(-1);
					i = i+1;}
				else {
					int j = i+1;
					while (j < word.length()){
						if (!Character.isDigit(word.charAt(j))) { break;}
						j = j +1;}
					if (j == word.length()) {
						standardWord.addB(-Integer.parseInt(word.substring(i+1)));
						break;}
					else {
						standardWord.addB(-Integer.parseInt(word.substring(i+1,j)));
						i = j;}
					}
			} else if (word.charAt(i) == 'c'){
				if (i == word.length()-1 || !Character.isDigit(word.charAt(i+1))){
					standardWord.addC(1);
					i = i+1;}
				else {
					int j = i+1;
					while (j < word.length()){
						if (!Character.isDigit(word.charAt(j))) { break;}
						j = j +1;}
					if (j == word.length()) {
						standardWord.addC(Integer.parseInt(word.substring(i+1)));
						break;}
					else {
						standardWord.addC(Integer.parseInt(word.substring(i+1,j)));
						i = j;}
					}
			} else if (word.charAt(i) == 'C'){
				if (i == word.length()-1 || !Character.isDigit(word.charAt(i+1))){
					standardWord.addC(-1);
					i = i+1;}
				else {
					int j = i+1;
					while (j < word.length()){
						if (!Character.isDigit(word.charAt(j))) { break;}
						j = j +1;}
					if (j == word.length()) {
						standardWord.addC(-Integer.parseInt(word.substring(i+1)));
						break;}
					else {
						standardWord.addC(-Integer.parseInt(word.substring(i+1,j)));
						i = j;}
					}
			}}
		if (conj == 1) {standardWord.reduce();}
		return standardWord; 
		}
	
	public static String inverse (String word){
		/* This method give the inverse of a word */
		String ans = "";
		String expanded = standardize(word,0).longword();
		for (int i = 0; i < expanded.length();i++){
			if (Character.isUpperCase(expanded.charAt(i))){ ans = Character.toLowerCase(expanded.charAt(i)) + ans;}
			else { ans = Character.toUpperCase(expanded.charAt(i)) + ans;}
		}
		return ans;
	}
	
	public static String changeBasis (String word, String a, String b, String c){
		/* This method receives the original word, and the representations of
		 * the old basis a, b, and c with respect to the new basis  a', b', c'.
		 * It returns the representation of word with respect to the new basis a', b', c'.
		 * However, the output ignores all the prime signs.
		 * 
		 * For example, if the word is a2b3c5, and in the new basis,
		 * a = a'2, b = b', c= a'b', then output should be
		 * a'4b'3(a'b')(a'b')(a'b')(a'b')(a'b').
		 * But the method ignores all the prime signs, so the actual output is
		 * a4b3(ab)(ab)(ab)(ab)(ab)
		 * 		  
		 * Usually, we only need simple basis changes, like (a,c) --> (a,b),
		 * where c = ab, so this should not be a problem.
		 */
		String ans = "";
		String expanded = standardize(word,0).longword();
		for (int i = 0; i < expanded.length(); i++){
			if (expanded.charAt(i) == 'a') { ans = ans + a;}
			else if (expanded.charAt(i) == 'b') { ans = ans + b;}
			else if (expanded.charAt(i) == 'c') { ans = ans + c;}
			else if (expanded.charAt(i) == 'A') { ans = ans + inverse(a);}
			else if (expanded.charAt(i) == 'B') { ans = ans + inverse(b);}
			else if (expanded.charAt(i) == 'C') { ans = ans + inverse(c);}
		}
		return ans;
	}

	private static ArrayList<Integer> intersectl1l2w1 (String word){
		ArrayList<Integer> ans = new ArrayList<Integer>();
		int l1 = 0;
		int l2 = 0;
		int w1 = 0;
		
		String inBasis = changeBasis(word, "a", "b", "ab");
		ArrayList<Integer> a = standardize(inBasis,1).getA();
		ArrayList<Integer> b = standardize(inBasis,1).getB();
		
		for (int i: a){l1 = l1 + Math.abs(i);}
		for (int i: b){l2 = l2 + Math.abs(i);}
		if (a.size() == 1){
			if (a.indexOf(0) == -1 && b.indexOf(0) == -1) {w1 = 2;}
		} else {w1 = 2*a.size();}
		ans.add(l1);
		ans.add(l2);
		ans.add(w1);
		return ans;
	}
	
	private static ArrayList<Integer> intersectl3w2 (String word){
		ArrayList<Integer> ans = new ArrayList<Integer>();
		int l3 = 0;
		int w2 = 0;
		
		String inBasis = changeBasis(word, "a", "Ac", "c");
		ArrayList<Integer> a = standardize(inBasis,1).getA();
		ArrayList<Integer> c = standardize(inBasis,1).getC();
		
		for (int i: a){l3 = l3 + Math.abs(i);}
		if (a.size() == 1){
			if (a.indexOf(0) == -1 && c.indexOf(0) == -2) {w2 = 2;}
		} else {w2 = 2*a.size();}
		ans.add(l3);
		ans.add(w2);
		
		return ans;
	}
	
	private static int intersectw3 (String word){
		int w3 = 0;
		String inBasis = changeBasis(word, "cB", "b", "c");
		ArrayList<Integer> b = standardize(inBasis,1).getB();
		ArrayList<Integer> c = standardize(inBasis,1).getC();
		if (b.size() == 1){
			if (b.indexOf(0) == -1 && c.indexOf(0) == -2) {w3 = 2;}
		} else {w3 = 2*b.size();}
		return w3;
	}
	
	public static boolean PantsSimInterEq (String word1, String word2){
		int ans = 0;
		if (intersectw3(word1) == intersectw3(word2)) {
			ArrayList<Integer> x1 = intersectl1l2w1(word1);
			ArrayList<Integer> x2 = intersectl1l2w1(word2);
			int l1word1 = x1.get(0);
			int l2word1 = x1.get(1);
			int w1word1 = x1.get(2);
			int l1word2 = x2.get(0);
			int l2word2 = x2.get(1);
			int w1word2 = x2.get(2);
			if (l1word1 == l1word2 && l2word1 == l2word2 && w1word1 == w1word2) {
				ArrayList<Integer> y1 = intersectl3w2(word1);
				ArrayList<Integer> y2 = intersectl3w2(word2);
				int l3word1 = y1.get(0);
				int w2word1 = y1.get(1);
				int l3word2 = y2.get(0);
				int w2word2 = y2.get(1);
				if (l3word1 == l3word2 && w2word1 == w2word2){
					ans = 1;
				}
			}
		}
		return (ans == 1);
	}
	
	public static ArrayList<CurvePants> PantsSimInterEqWith (String word) throws IOException, ClassNotFoundException{
		ArrayList<CurvePants> ans = new ArrayList<CurvePants>();
		
		//Creat an object in CurvePants 
		String newbasis = changeBasis(word, "a", "b", "ab");
		curveStandard standard = standardize(newbasis,1);
		CurvePants fromword = new CurvePants(standard.getA(),standard.getB());
		fromword.setlongword();
		fromword.setIntersectNum();
		
		//Retrieve the right list
		String filename = "n" + Integer.toString(fromword.size()) + "CurvePants.tmp";
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<CurvePants> list = (ArrayList<CurvePants>) ois.readObject();
		ois.close();
		
		for (CurvePants x: list){
			if (fromword.compareTo(x) == 0) {ans.add(x);}
			if (fromword.compareTo(x) < 0) {break;}
		}
		return ans;
	}
	
	public static void PantsSimInterEqClass (int n) throws IOException, ClassNotFoundException{
		FileWriter classes = new FileWriter("n"+n+"classes.txt");
        PrintWriter printclass = new PrintWriter(classes);
        
        printclass.println("This is the list of simple intersection equivalence classes, consisting of curves of the form");
        printclass.println("a^x1 b^y1 a^x2 b^y2 ... a^xn b^yn");
        printclass.println("where n = " + n + " and all xj and yj are between [-4,4]");
        printclass.println("Inverses and backwards of curves can appear together. However, ");
        printclass.println("no two words in this file represent the same curve up to free homotopy.");
        printclass.println();
		
		//Retrieve the right list
		String filename = "n" + n + "CurvePants.tmp";
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<CurvePants> list = (ArrayList<CurvePants>) ois.readObject();
		ois.close();
		
		int s = list.size();
		for (int j = 0; j<s; j++){
			CurvePants current = list.get(j);
			printclass.println(current.getlongword());
			if (j+1 < s){CurvePants next = list.get(j+1);
				if (current.compareTo(next) != 0) {
					printclass.println("(end of class)");
					printclass.println();
				}
			}
		}
		printclass.close();
	}
}



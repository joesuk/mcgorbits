package a_000workshop2016;
import java.util.*;
import a_torusPantsHL.TestsTorus;
import a_torusPantsHL.Parametrizations;
import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;
import a_orbits.DriverOrbits2016;
import a_orbits.Mcg;

public class WordFinder {
	
	static int fourcount = 0;
	static ArrayList<Curve> curves = new ArrayList<Curve>();
	static ArrayList<String> words = new ArrayList<String>();
	static ArrayList<Curve> extraCurves = new ArrayList<Curve>();
	
	public static void main(String[] args) {

		ArrayList<String> case1 = new ArrayList<String>();
		case1.add("abAB");
		case1.add("abAB");
		case1.add("abAB");
		case1.add("abAB");
		case1.add("abAB");
		
		ArrayList<String> case2 = new ArrayList<String>();
		case2.add("a");
		case2.add("abAB");
		case2.add("abAB");
		case2.add("abAB");
		case2.add("abAB");
		
		ArrayList<String> case3 = new ArrayList<String>();
		case3.add("a");
		case3.add("a");
		case3.add("abAB");
		case3.add("abAB");
		case3.add("abAB");
		
		ArrayList<String> case4 = new ArrayList<String>();
		case4.add("a");
		case4.add("abAB");
		case4.add("abAB");
		case4.add("abAB");
		case4.add("b");
		
		ArrayList<String> case5 = new ArrayList<String>();
		case5.add("a");
		case5.add("abAB");
		case5.add("abAB");
		case5.add("abAB");
		case5.add("baB");
		
		ArrayList<String> case6 = new ArrayList<String>();
		case6.add("a");
		case6.add("a");
		case6.add("a");
		case6.add("abAB");
		case6.add("abAB");
		
		ArrayList<String> case7 = new ArrayList<String>();
		case7.add("a");
		case7.add("a");
		case7.add("abAB");
		case7.add("abAB");
		case7.add("b");
		
		ArrayList<String> case8 = new ArrayList<String>();
		case8.add("a");
		case8.add("a");
		case8.add("abAB");
		case8.add("abAB");
		case8.add("baB");
		
		ArrayList<String> case9 = new ArrayList<String>();
		case9.add("a");
		case9.add("abAB");
		case9.add("abAB");
		case9.add("baB");
		case9.add("b");
		
		ArrayList<String> case10 = new ArrayList<String>();
		case10.add("a");
		case10.add("abAB");
		case10.add("abAB");
		case10.add("ab");
		case10.add("b");
		
		ArrayList<String> case11 = new ArrayList<String>();
		case11.add("a");
		case11.add("a");
		case11.add("a");
		case11.add("a");
		case11.add("abAB");
		
		ArrayList<String> case12 = new ArrayList<String>();
		case12.add("a");
		case12.add("a");
		case12.add("a");
		case12.add("abAB");
		case12.add("b");
		
		ArrayList<String> case13 = new ArrayList<String>();
		case13.add("a");
		case13.add("a");
		case13.add("a");
		case13.add("abAB");
		case13.add("baB");
		
		ArrayList<String> case14 = new ArrayList<String>();
		case14.add("a");
		case14.add("a");
		case14.add("abAB");
		case14.add("b");
		case14.add("b");
		
		ArrayList<String> case15 = new ArrayList<String>();
		case15.add("a");
		case15.add("a");
		case15.add("abAB");
		case15.add("baB");
		case15.add("baB");
		
		ArrayList<String> case16 = new ArrayList<String>();
		case16.add("a");
		case16.add("a");
		case16.add("abAB");
		case16.add("baB");
		case16.add("b");
		
		ArrayList<String> case17 = new ArrayList<String>();
		case17.add("a");
		case17.add("a");
		case17.add("b");
		case17.add("abAB");
		case17.add("b");
		
		
		ArrayList<String> case18 = new ArrayList<String>();
		case18.add("a");
		case18.add("a");
		case18.add("abAB");
		case18.add("ab");
		case18.add("b");
		
		ArrayList<String> case19 = new ArrayList<String>();
		case19.add("a");
		case19.add("ab");
		case19.add("abA");
		case19.add("abAB");
		case19.add("b");
		
		ArrayList<String> case20 = new ArrayList<String>();
		case20.add("a");
		case20.add("a");
		case20.add("a");
		case20.add("a");
		case20.add("a");
		
		ArrayList<String> case21 = new ArrayList<String>();
		case21.add("a");
		case21.add("a");
		case21.add("a");
		case21.add("a");
		case21.add("b");
		
		ArrayList<String> case22 = new ArrayList<String>();
		case22.add("a");
		case22.add("a");
		case22.add("a");
		case22.add("a");
		case22.add("baB");
		
		ArrayList<String> case23 = new ArrayList<String>();
		case23.add("a");
		case23.add("a");
		case23.add("a");
		case23.add("b");
		case23.add("b");
		
		ArrayList<String> case24 = new ArrayList<String>();
		case24.add("a");
		case24.add("a");
		case24.add("a");
		case24.add("baB");
		case24.add("baB");
		
		ArrayList<String> case25 = new ArrayList<String>();
		case25.add("a");
		case25.add("a");
		case25.add("a");
		case25.add("baB");
		case25.add("b");
		
		ArrayList<String> case26 = new ArrayList<String>();
		case26.add("a");
		case26.add("a");
		case26.add("a");
		case26.add("abA");
		case26.add("b");
		
		ArrayList<String> case27 = new ArrayList<String>();
		case27.add("a");
		case27.add("a");
		case27.add("a");
		case27.add("ab");
		case27.add("b");
		
		ArrayList<String> case28 = new ArrayList<String>();
		case28.add("a");
		case28.add("a");
		case28.add("baB");
		case28.add("abA");
		case28.add("b");
		
		ArrayList<String> case29 = new ArrayList<String>();
		case29.add("a");
		case29.add("a");
		case29.add("baB");
		case29.add("b");
		case29.add("b");
		
		
		ArrayList<String> case30 = new ArrayList<String>();
		case30.add("a");
		case30.add("a");
		case30.add("baB");
		case30.add("baB");
		case30.add("b");
		
		
		ArrayList<String> case31 = new ArrayList<String>();
		case31.add("a");
		case31.add("a");
		case31.add("abA");
		case31.add("b");
		case31.add("b");
		
		
		ArrayList<String> case32 = new ArrayList<String>();
		case32.add("a");
		case32.add("a");
		case32.add("ab");
		case32.add("abA");
		case32.add("b");
		
		ArrayList<String> case33 = new ArrayList<String>();
		case33.add("a");
		case33.add("a");
		case33.add("ab");
		case33.add("b");
		case33.add("b");
		
		ArrayList<String> case34 = new ArrayList<String>();
		case34.add("a");
		case34.add("a");
		case34.add("ab");
		case34.add("abA");
		case34.add("b");
		
		ArrayList<String> case35 = new ArrayList<String>();
		case35.add("a");
		case35.add("a");
		case35.add("abA");
		case35.add("abA");
		case35.add("b");
		
		ArrayList<String> case36 = new ArrayList<String>();
		case36.add("a");
		case36.add("a");
		case36.add("ab");
		case36.add("abA");
		case36.add("b");
		
		ArrayList<ArrayList<String>> cases = new ArrayList<ArrayList<String>>();
		cases.addAll(Arrays.asList(case1,case2,case3,case4,case5,case6,case7,case8,case9,
				case10,case11,case12,case13,case14,case15,case16,case17,case18,case19,
				case20,case21,case22,case23,case24,case25,case26,case27,case28,case29,
				case30,case31,case32,case33,case34,case35,case36));
		
		
		int[] order1 = {1,2,3,4,5,-5,-4,-3,-2,-1};
		int[] order2 = order1;
		int[] order3 = order1;
		int[] order4 = {1,2,3,4,-4,-3,-2,5,-1,-5};
		int[] order5 = {1,2,3,4,-4,-3,-2,5,-5,-1};
		int[] order6 = order1;
		int[] order7 = {1,2,3,4,-4,-3,5,-2,-1,-5};
		int[] order8 = {1,2,3,4,-4,-3,5,-5,-2,-1};
		int[] order9 = {1,2,3,-3,-2,4,5,-4,-1,-5};
		int[] order10 = {1,2,3,-3,-2,4,5,-1,-4,-5};
		int[] order11 = order1;
		int[] order12 = {1,2,3,4,-4,5,-3,-2,-1,-5};
		int[] order13 = {1,2,3,4,-4,5,-5,-3,-2,-1};
		int[] order14 = {1,2,3,-3,4,5,-2,-1,-5,-4};
		int[] order15 = {1,2,3,-3,4,5,-5,-4,-2,-1};
		int[] order16 = {1,2,3,-3,4,5,-4,-2,-1,-5};
		int[] order17 = {1,2,3,4,-4,5,-2,-1,-5,-3};
		int[] order18 = {1,2,3,-3,4,5,-2,-1,-5,-4};
		int[] order19 = {1,2,3,4,-4,5,-1,-2,-5,-3};
		int[] order20 = order1;
		int[] order21 = {1,2,3,4,5,-4,-3,-2,-1,-5};
		int[] order22 = order1;
		int[] order23 = {1,2,3,4,5,-3,-2,-1,-5,-4};
		int[] order24 = order1;
		int[] order25 = {1,2,3,4,5,-4,-3,-2,-1,-5};
		int[] order26 = {1,2,3,4,5,-3,-2,-1,-5,-4};
		int[] order27 = {1,2,3,4,5,-3,-2,-1,-5,-4};
		int[] order28 = order27;
		int[] order29 = order27;
		int[] order30 = order25;
		int[] order31 = {1,2,3,4,5,-2,-1,-5,-4,-3};
		int[] order32 = order31;
		int[] order33 = {1,2,3,4,5,-2,-1,-3,-5,-4};
		int[] order34 = {1,2,3,4,5,-2,-1,-3,-5,-4};
		int[] order35 = {1,2,3,4,5,-2,-1,-4,-3,-5};
		int[] order36 = {1,2,3,4,5,-2,-1,-3,-5,-4};
		
		int[][] orders = {order1,order2,order3,order4,order5,order6
							,order7,order8,order9,order10,order11,order12
							,order13,order14,order15,order16,order17,order18
							,order19,order20,order21,order22,order23,order24
							,order25,order26,order27,order28,order29,order30
							,order31,order32,order33,order34,order35,order36
		};

		
		classify(cases, orders);
		
		printCases();
		System.out.println(words.size());
		
		ArrayList<String> toRemove = new ArrayList<String>();
		for (String str : words) {
		    clearOrbit(toRemove, words,str);
		}
		
		words.removeAll(toRemove);
		

		
		printCases();
		
		System.out.println(words.size());
		
		// System.out.println(fourcount);
		
		// printCases();
		/*for (Curve x11 : curves) {
		System.out.println("\n"+x11.toString() + " self-int number  " + x11.selfIntersection() + " word length " + x11.toString().length());
		}*/
		
		/* double[][][] m=Parametrizations.param4();
		double[][] m1={m[1][1],m[1][2]};
		SurfaceWord swTorus=Parametrizations.torus(m1);
		
		Word i1 = new Word(Word.fromString(reduceWord(new StringBuilder("aabABabABabABbAB")), 4));
		
		Curve x11 = new Curve(i1, swTorus);
		
		System.out.println(inverse("baB"));*/

		
		
		
	}
	
	static void classify(ArrayList<ArrayList<String>> cases, int[][] orders) {
		for (int i = 0; i < cases.size(); i++) {
			System.out.println("Case " + (i+1));
			int[][] positions = new int[10][10];
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					positions[j][k] = orders[i][(k-j+10) % 10];
				}
				
				makeWords(positions[j],cases, i);
			}
		}
	}
	
	static void makeWords(int[] position, ArrayList<ArrayList<String>> cases, int i ) {
		ArrayList<Integer> travelPath = new ArrayList<Integer>();
		ArrayList<Integer> positionList = new ArrayList<Integer>();
		for (int k = 0; k < position.length; k++) {
			positionList.add(position[k]);
		}
		StringBuilder word = new StringBuilder();
		travelPath.add(1);
		int current = 1;
		int count = 4;
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


			if (x11.selfIntersection() ==4) {
				if (!contains(x11)) {
					curves.add(x11);
					words.add(x11.toString());
					/*for (int k = 0; k < travelPath.size(); k++) {
						System.out.print(travelPath.get(k) + " ");
					}*/
					fourcount++;
					// System.out.println(word);
					System.out.println("\n"+x11.toString() + " self-int number  " + x11.selfIntersection() + " word length " + x11.toString().length());
				}
			}
			
			/*if (x12.selfIntersection() ==4) {
				if (!contains(x12)) {
					System.out.println("\n" + "INVERSE");
					curves.add(x12);
					words.add(x12.toString());

					fourcount++;
					// System.out.println(word);
					System.out.println("\n"+x12.toString() + " self-int number  " + x12.selfIntersection() + " word length " + x12.toString().length());
				}
			}
			*/
			
		}
		
		
		
	}
	
	static int getNext(int index) {
		switch (index) {
		case 0 : return 2;
		case 1 : return 8;
		case 2 : return 0;
		case 3 : return 7;
		case 4 : return 6;
		case 5 : return 9;
		case 6 : return 4;
		case 7 : return 3;
		case 8 : return 1;
		case 9 : return 5;
		}
		return 0;
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
		
		/* if (word.equals("baB") || word.equals("bAB") || word.equals("abA") || word.equals("aBA")) {
			return inverse.toString();
		}*/
		
		
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
	
	static boolean contains(Curve x11) {
		for (Curve thisCurve : curves) {
			if (thisCurve.toString().equals(x11.toString())) {
				return true;
			} else if (isCycled(thisCurve.toString(),x11.toString())) {
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

	static void printCases() {
		for (int i = 1; i < 21; i++) {
			for (String x11 : words) {
				if (x11.length() == i) {
					System.out.println("\n"+x11 + " : " + x11.length());
				}
			}
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


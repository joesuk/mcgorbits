package a_000workshop2016;
/**
 * This class contains a method to perform abelianization of a word in the fundamental group of the punctured torus to get its respective
 * word in the fundamental group of the normal torus. This is necessary to impose a partition on our exhaustive list of words produced
 * from the basepoint analysis by comparing the automorphic conjugacy classes of the abelianizations of two words (if distinct, they
 * cannot be in the same class on the punctured torus, reducing our work to prove distinctness).
 * @author Joe
 *
 */
public class Abelianization {
	/**
	 * Abelianizes given word of free homotopy class on punctured torus.
	 * @param originalWord Given word.
	 * @return Abelianized word for normal torus.
	 */
	public static String abelianize(String originalWord) {
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

}

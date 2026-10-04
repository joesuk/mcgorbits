package simpleIntersectionDat;

import java.util.Scanner;
import java.util.*;
/* This class represents a word by 3 lists a, b, c, such that the word is equal to
 * a^a[0] b^b[0] c^c[0] a^a[1] b^b[1] c^c[1] ...
 * 
 *  The three lists always have equal size.
 *  If we do not allow words to be conjugated, then the three lists
 *  are always as short as possible, even without applying the "reduce" method.
 *  
 *  The "reduce" method makes the three lists as short as possible,
 *  in the case conjugations are taken into consideration.
 *   
 */

public class curveStandard {
	private ArrayList<Integer> a;
	private ArrayList<Integer> b;
	private ArrayList<Integer> c;
	char last;
	char first;
	/* If we write out the word corresponding to these three lists,
	 * then "first" and "last" are exactly the first and last letter of the word.
	 * 
	 * Of course these data is not really needed for our main problem.
	 * They are just here for technical reasons.
	 */
		
	public curveStandard() {
	/* Create the lists that correspond to the blank word */
		
		a = new ArrayList<Integer>(); a.add(0);
		b = new ArrayList<Integer>(); b.add(0);
		c = new ArrayList<Integer>(); c.add(0);
		first = 'a';
		last = 'a';
	}
	
	void reset(){
		/* This method reset the data to their initial values. */
		 
		a.clear(); a.add(0);
		b.clear(); b.add(0);
		c.clear(); c.add(0);
		first = 'a';
		last = 'a';
	}
	
	void updateLast(){
		/* This method, as its name, updates the value of "last."
		 * It also removes redundant zeroes at the tails of the three lists.
		 */
		int lastA = a.get(a.size()-1);
		int lastB = b.get(b.size()-1);
		int lastC = c.get(c.size()-1);
		while (a.size()>1 && lastA == 0 && lastB == 0 && lastC == 0){
			c.remove(c.size()-1);
			b.remove(b.size()-1);
			a.remove(a.size()-1);
			lastA = a.get(a.size()-1);
			lastB = b.get(b.size()-1);
			lastC = c.get(c.size()-1);
		}
		if (lastB==0 && lastC == 0) {last = 'a';}
		else if (lastC == 0) {last = 'b';}
		else {last = 'c';}
	}
	
	/* The following 3 methods update the lists in the events of
	 * new letters being added to the right of the word.
	 */
	
	void addA (int i){
		/* Update the lists when i (consecutive) letters 'a' are added */
		if (last == 'a'){
			int lastA = a.get(a.size()-1);
			a.set(a.size()-1, lastA + i);
			updateLast();
		} else {
			a.add(i); b.add(0); c.add(0);
			updateLast();
		}
	}
	
	void addB (int i){
		/* Update the lists when i (consecutive) letters 'b' are added */
		if (last == 'a'){
			b.set(b.size()-1, i);
			updateLast();
		} else if (last == 'b'){
			int lastB = b.get(b.size()-1);
			b.set(b.size()-1, lastB + i);
			updateLast();
		} else {
			a.add(0); b.add(i); c.add(0);
			updateLast();
		}
	}
	
	void addC (int i){
		/* Update the lists when i (consecutive) letters 'c' are added */
		if (last == 'c'){
			int lastC = c.get(c.size()-1);
			c.set(c.size()-1, lastC + i);
			updateLast();
		} else {
			c.set(c.size()-1,i);
			updateLast();
		}
	}
	
	void updateFirst(){
		/* This method, as its name, updates the value of "first." */
		int firstA = a.get(0);
		int firstB = b.get(0);
		int firstC = c.get(0);
		if (firstA == 0 && firstB == 0 && firstC == 0) {first = 'a';}
		else if (firstA == 0 && firstB == 0) {first = 'c';}
		else if (firstA == 0) {first = 'b';}
		else {first = 'a';}
	}

	void reduce() {
		/* This method takes conjugations into consideration,
		 * and make the final reduction. (Note that the lists are already
		 * reduced if we do not consider conjugacies).
		 */
		
		updateLast();
		updateFirst();
		while (a.size() > 1 && (first == last || last == 'a')){
			if (last == 'a'){
				int lastA = a.get(a.size()-1);
				int firstA = a.get(0);
				a.set(0, lastA + firstA);
				a.set(a.size()-1, 0);
			} else if (last == 'b'){
				int lastB = b.get(b.size()-1);
				int firstB = b.get(0);
				b.set(0, lastB + firstB);
				b.set(b.size()-1, 0);
			} else {
				int lastC = c.get(c.size()-1);
				int firstC = c.get(0);
				c.set(0, lastC + firstC);
				c.set(c.size()-1, 0);
			}
			updateLast();
			updateFirst();
		}
		/* This method guarantees that when we write out the word,
		 * it will start with 'a', unless it does not have any 'a',
		 * then it will start with 'b', unless it only has 'c'
		 */
		if (first != 'a' && a.size()>1){
			int firstnonzeroA = 0;
			while (firstnonzeroA < a.size()) {
				int currentA = a.get(firstnonzeroA);
				if (currentA != 0) {break;}
				firstnonzeroA = firstnonzeroA + 1;
			}
			if (firstnonzeroA < a.size()){
				for (int j = 0; j < firstnonzeroA; j++){
					int firstB = b.get(0);
					int firstC = c.get(0);
					addB(firstB);
					addC(firstC);
					a.remove(0);
					b.remove(0);
					c.remove(0);
				}
			} else if (first != 'b'){
				int firstC = c.get(0);
				addC(firstC);
				a.remove(0);
				b.remove(0);
				c.remove(0);
			}	
		}
		updateLast();
		updateFirst();
	}
		
	/* getters */
	public ArrayList<Integer> getA (){return a;}
	public ArrayList<Integer> getB (){return b;}
	public ArrayList<Integer> getC (){return c;}
	
	/* setters
	 * This method can easily mess up the data structure.
	 * It should only be used as a set up for "longword" or "shortword"
	 */
	public void set (ArrayList<Integer> listA, ArrayList<Integer> listB, ArrayList<Integer> listC){
		a = listA; b = listB; c=listC;
	}
	
	String longword (){
	/* This method write out the word in long form (i.e. not using numbers) */
		String ans = "";
		for (int i = 0; i < a.size(); i++){
			int currentA = a.get(i);
			int currentB = b.get(i);
			int currentC = c.get(i);
			if (currentA > 0) {for (int j = 0; j < currentA; j++){ans = ans + "a";}}
			if (currentA < 0) {for (int j = 0; j < -currentA; j++){ans = ans + "A";}}
			if (currentB > 0) {for (int j = 0; j < currentB; j++){ans = ans + "b";}}
			if (currentB < 0) {for (int j = 0; j < -currentB; j++){ans = ans + "B";}}
			if (currentC > 0) {for (int j = 0; j < currentC; j++){ans = ans + "c";}}
			if (currentC < 0) {for (int j = 0; j < -currentC; j++){ans = ans + "C";}}
		}
		return ans;
	}
	
	String shortword(){
		/* This method write out the word in short form (i.e. using numbers) */
		String ans = "";
		for (int i = 0; i < a.size(); i++){
			int currentA = a.get(i);
			int currentB = b.get(i);
			int currentC = c.get(i);
			
			if (currentA > 1) {ans = ans + "a" + Integer.toString(currentA);}
			if (currentA == 1) {ans = ans + "a";}
			if (currentA == -1) {ans = ans + "A";}
			if (currentA < -1) {ans = ans + "A" + Integer.toString(-currentA);}
			
			if (currentB > 1) {ans = ans + "b" + Integer.toString(currentB);}
			if (currentB == 1) {ans = ans + "b";}
			if (currentB == -1) {ans = ans + "B";}
			if (currentB < -1) {ans = ans + "B" + Integer.toString(-currentB);}
			
			if (currentC > 1) {ans = ans + "c" + Integer.toString(currentC);}
			if (currentC == 1) {ans = ans + "c";}
			if (currentC == -1) {ans = ans + "C";}
			if (currentC < -1) {ans = ans + "C" + Integer.toString(-currentC);}
		}
		return ans;
	}	
}
	


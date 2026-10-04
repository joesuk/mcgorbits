package simpleIntersectionDat;

import boundary.Curve;
import boundary.CurveList;
import boundary.PrimitiveDoublyPalCyclicWord;
import boundary.SurfaceWord;

public class FindSimpleIntersectionClasses {
	public static void main (String [] args) {
		
		//The surface is the pair of pants
		SurfaceWord swPants = new SurfaceWord("aAbB");
		//The word length of the classes to study.
		int wordLength = 8;
		
		/*Create the an empty list of curves.
		One knows that if w is a curve-words, then there are at most three curves that will
		be simple intersection equivalent to w. These curve-word are w^{-1}, w "read backwards, and 
		w^{-1} read backwards. The list below will be made choosing only one of these four 
		equivalent curves. Also, there is exacly one representative of each conjugacy class.
		In other words, 
		1. For each curve-word w, there is one curve-word v in the list, such that w is conjugate to
		v or  to v^{-1} or to v read backwards or to v^{-1} read backwards.
		2. If v and w are two words in the ListOfCurves, then v is not conjugate to w, nor to w^{-1}, 
		nor to w read backwards nor to w^{-1} read backwards.
		 
		 (I made this code a bit fast, so I am only 90% sure that it works fine. There might be some
		 issue when a word is conjugate to the same word read backwards. )
		 */
		CurveList listOfCurves = new CurveList(swPants);
		PrimitiveDoublyPalCyclicWord dcw = new PrimitiveDoublyPalCyclicWord(new int[wordLength] , swPants.length());
		Curve c = new Curve(dcw, swPants);
		c.initialize();
		//We add to the curve-list cl ALL curve-words of  word length equal to wordLength 
		while(c.length()==wordLength){
			listOfCurves.add(c);	
			c = c.next();
				}
		// Now we need a method that will take the listOfCurves as input, and
		//give an ArrayList<CurveList> , where each CurveList is an (simple intersection) 
		//equivalence class
	}

}

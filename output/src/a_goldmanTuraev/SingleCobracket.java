package a_goldmanTuraev;

import java.util.ArrayList;

import boundary.Curve;
import boundary.CyclicWord;
import boundary.SurfaceWord;
import boundary.Word;
import boundaryBialgebra.*;


public class SingleCobracket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String z = "aAbB";
		String curw ="aaB";
		
		
		//String curw ="BacAbcBA";
		SurfaceWord sw = new SurfaceWord(z);
		
		Word ww1 = new Word( Word.fromString(curw, sw.length()));
	//	CyclicWord cw = new CyclicWord(s1, sw.length());
		Curve c = new Curve(ww1, sw);
	
		
		
		
		System.out.println("The curve is " +c.toString()+". Self-intersection is: "+c.selfIntersection());
		System.out.println(c.cobracket().getTerms());
		
		
		
	
	/*	
	    	int max =100;	
	//	CyclicWord cw = new CyclicWord(new int[1], sw.length());
		CyclicWord cw1 = new CyclicWord(new int[1], sw.length());
		//This create a Curve c from the CyclicWord cw and SurfaceWord sw.
		//This now represents a [0], or 'a' on the surface abABcdCD or [0,2,1,3,4,6,5,7]
		
		Curve c1 = new Curve(cw1, sw);
		
		c.initialize();
		c1.initialize();
		System.out.println("Brackets with cancellation "+sw);
		for(int i=0;i<0;i++){
			for(int j = 0 ; j < max ; j++){
				BTermsNumber bt =cl.bracket(cl.toNumber(c.toString()),cl.toNumber(c1.toString()));	
				if(checkCurve(bt)){
				System.out.println("AAAAAAAAAAAAAA"+" "+c.toString()+" "+c1.toString());
				}
				int intersection = c.intersection(c1);
				if(bt.addAbsVCoef() <  intersection){
					System.out.println(bt.addAbsVCoef()+" "+intersection+" "+c.toString()+" "+c1.toString());
					//System.out.println(bt.toString(swo));
				}
				ArrayList<BTermNumber> btt= 	bt.getTerms();
			
			c = c.next();
		}
		c1=c1.next();
		
	}
	*/

	}

	
	public static boolean checkCurve(BTermsNumber bt){
		int[] v1 ={0,1,2,3};
		BTermNumber bd = new BTermNumber(1,v1);
		boolean answer = false;
		ArrayList<BTermNumber> btn= bt.getTerms();
		for(int h = 0 ; h < btn.size() ; h++){
			answer = answer || btn.get(h).vectorsEqual(bd);
		}
		return answer;
	}
}

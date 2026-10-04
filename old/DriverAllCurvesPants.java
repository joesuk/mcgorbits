package a_000workshop2016;

import a_torusPantsHL.Parametrizations;
import boundary.Curve;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;

public class DriverAllCurvesPants {

	/**
	 *Prints a list of all free homotpy classes of closed curves on the pair of pants with a given matric
	 *up to certain hyperbolic length.
	 * @param args
	 */
	public static void main(String[] args) {
		//the length of the boundary components of the pants
		
		double la = 1;
		double lb = 1;
		double lc = 4;
		
		//the maximal word length to test
		int maxWL = 12;

		//create vector to save data
	
		
		//create the surface word
		SurfaceWord sw1 = Parametrizations.pants(la, lb, lc);
		
		//find the constant.
		double constant1 = Math.min(Math.min(la, lb), lc * 0.5);

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
				new int[1], sw1.length());
		Curve c = new Curve(dcw, sw1);
		c.initialize();

		System.out.println("word"+"\t"+"SI"+"\t"+"WL"+"\t"+"Hyperbolic length");
		
		while (c.length() <= maxWL) {
			 
			 

			 //this considers all the word up to hyperbolic length constant1 * maxWL
			if (c.hyperbolicLength() <= constant1 * maxWL) {
				System.out.println(c.toString()+"\t"+c.selfIntersection()+"\t"+c.length()+"\t"+c.hyperbolicLength());
				
			}
			c = c.next();
		}

	}

}

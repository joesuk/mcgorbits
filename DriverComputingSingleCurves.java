package a_000workshop2016;

import a_torusPantsHL.TestsTorus;
import a_torusPantsHL.Parametrizations;
import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;

public class DriverComputingSingleCurves {

	public static void main(String[] args) {
	


    //Pants


		//the length of the boundary components of the pants
		double la = 1;
		double lb = 1;
		double lc = 4;

		//create the surface word if surface is PANTS	 

		SurfaceWord swPant = Parametrizations.pants(la, lb, lc);
		
		//cyclic reduced words
		String s1 = "aab";
		String s2 = "aaababAABaBB";

		//We define words from string, the "4" is because the surfaces we are considering (punctured torus and pants)
		//have alphabets with 4 letters.

		Word i1 = new Word(Word.fromString(s1, 4));
		Word i2 = new Word(Word.fromString(s2, 4));




		Curve w11 = new Curve(i1, swPant);
		Curve w12 = new Curve(i2, swPant);


		System.out.println(w11.toString() + " self-int number  " + w11.selfIntersection());
		System.out.println("hyperbolic length of " +w11.toString() + "  in pants metric ("+la+", "+lb+", "+lc+") is "
				+ w11.hyperbolicLength() );
		System.out.println(w12.toString() + " self-int number " + w12.selfIntersection());
		System.out.println(w11.toString() + " and "+w12.toString() +" intersection "+ w11.intersection(w12));

/*********************************
 * Torus
 */
		
		//torus	
		double[][][] m=Parametrizations.param4();
		double[][] m1={m[1][1],m[1][2]};
		SurfaceWord swTorus=Parametrizations.torus(m1);
		
		Curve x11 = new Curve(i1, swTorus);
		Curve x12 = new Curve(i2, swTorus);


		System.out.println("\n"+x11.toString() + " self-int number  " + x11.selfIntersection());
		System.out.println("hyperbolic length of " +x11.toString() + "  in torus metric is "
				+ x11.hyperbolicLength() );
		System.out.println(x12.toString() + " self-int number " + x12.selfIntersection());
		System.out.println(x11.toString() + " and "+x12.toString() +" intersection "+ x11.intersection(w12));



	}

}

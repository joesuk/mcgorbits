package examples;
import java.util.ArrayList;

import boundary.*;

public class SingleTest {
	public static void main(String[] args){
		/****************************************/
		/*  Curves with same hyperbolic length  */
		/****************************************/
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		//Need  a < 1 < b < c and the surface has to be Pants
		
		double aa = 0.2;
		double bb= 1.2;
		double cc= 3;
		
	//	double aa = 0.00000001	;
	//	double bb= 1.1;
	//	double cc= 1.1000000001;
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
				{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
	
		
		Word w1 = new Word(Word.fromString("aB",4));
			//	"BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB",4));
		Word w2 = new Word(Word.fromString("BabA",4));
		Word w3 = new Word(Word.fromString("aaaBAAb",4));
		
		CyclicWord cw1 = new CyclicWord(w1);
		CyclicWord cw2 = new CyclicWord(w2);
		CyclicWord cw3 = new CyclicWord(w3);
		
		Curve c1 = new Curve(cw1,sw);
		Curve c2 = new Curve(cw2,sw);
		Curve c3 = new Curve(cw3,sw);
	
		
		sw.setHyperbolicMatrix(mat);
		
		System.out.println(c1.selfIntersection());
		System.out.println(c2.selfIntersection());
		
	//	System.out.println(c1.hyperbolicLength()+"\n"+ c1.hyperbolicLength()/c1.length());
	//	System.out.println(c1.hyperbolicLength()+"\n"+ c2.hyperbolicLength()+"\n"+c3.hyperbolicLength());
		
		
		
	}
}

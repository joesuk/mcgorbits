package a_goldmanTuraev;

import hypLengthComp.LengthSamples;
import boundary.SurfaceWord;
import boundaryBialgebra.BTermsNumber;
import boundaryBialgebra.CL;

public class JonahConj {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Surface word
		String sw = "abAB";
		SurfaceWord ssw = new SurfaceWord(sw);
		CL cl = new CL(sw);
		
		//strings for curves: Should be cyclically reduced
		String h1 = "Ba";
		String h2 = "bA";
		String c="bbABaa"; 
		
	//	String w1 = LengthSamples.randomWordString(10,ssw); 
			
	//	String w2 = LengthSamples.randomWordString(8,ssw); 
		
		
		
		int[] hh1 = cl.toNumber(h1);
		int[] hh2 = cl.toNumber(h2);
		int[] cc = cl.toNumber(c);
		
		BTermsNumber bt1 =cl.bracket(hh1, cc);
		bt1.simplify();
		BTermsNumber bt2 =cl.bracket(hh2, cc);
		bt2.simplify();
	//	CobracketTerms ct = cl.cobracket(s1);
	    String answer =bt1.toString(cl);
		System.out.println(answer);
		
		answer =bt2.toString(cl);
		System.out.println(answer);
		
	//	System.out.println(ct.toString(cl));
		
//		System.out.println(bt.toString(cl))
		System.out.println("(*"+h1+", "+h2+" "+c);
		System.out.println("\n\nThe number of terms: "+bt1.addAbsVCoef());
		System.out.println("\n\n"+cl.si(h1,c));
		System.out.println("*)");
		System.out.println("theList="+bt1.toMathematica(cl)+";");
		System.out.println("theList="+bt2.toMathematica(cl)+";");
		System.out.println("Compare[theList]");
	//	System.out.println(cl.si(w2));
		

	}

}

package a_goldmanTuraev;

import hypLengthComp.*;
import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;
import boundaryBialgebra.BTermsNumber;
import boundaryBialgebra.CL;

public class CompareSI_Fricke {

	/**
	 * @param args
	 */
	public static void main(String[] args) {  
		//Surface word
		int wordLength =10;
		SurfaceWord swPants = new SurfaceWord("aAbB");
		
		String sentence="list={";
		//strings for curves: Should be cyclically reduced
		
		for(int h=10 ; h< 61 ; h=h+1){
			
			String w1 = LengthSamples.randomWordString(h,swPants); 
		//	String w1="aabababababababab";
			Word ww1 = new Word(Word.fromString(w1,swPants.length()));	
			Curve c = new Curve(ww1,swPants);
		//	sentence="1.*Length[MonomialList[FrickePoly[\""+c.toString()+"\"]]]/"+c.selfIntersection()+"\n";
			sentence=sentence+"{Length[MonomialList[FrickePoly[\""+c.toString()+"\"]]],"+c.length()+","+c.selfIntersection()+"},\n";
			
			
		}
			
		System.out.println(sentence);
		
		
	//	System.out.println(sentence);
	//	System.out.println(cl.si(w2));
		

	}

}

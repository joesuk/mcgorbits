package a_goldmanTuraev;

import hypLengthComp.LengthSamples;
import boundary.Curve;

import boundary.PrimitiveDoublyPalCyclicWord;
import boundary.SurfaceWord;


import boundaryBialgebra.BTermsNumber;
import boundaryBialgebra.CL;

public class BracketStudyingLengthEquiv {

	/**
	 * @param args
	 */
	public static void main(String[] args) {  
		//Surface word
		String sw = "abAB";
		CL cl = new CL(sw);
		SurfaceWord swPants = new SurfaceWord(sw);
		int testLength = 6;
		//String s1 = "abbaBB";
		String s1 = LengthSamples.randomWordString(8,swPants); 
		
		printList(5, s1,cl, swPants, "test1");
		System.out.println(s1);
		for(int i=3; i <8; i++){
			System.out.println(i);
			
		}
	///
//		Word w1 = new Word(Word.fromString(s1,swPants.length()));	
		
		
	
	//	CobracketTerms ct = cl.cobracket(s1)
	

		
	//	System.out.println(cl.si(w2));
		

	}
	
	public static void printList(int testLength, String s1,CL cl, SurfaceWord swPants, String filename){
		int[] n1 = cl.toNumber(s1);
		
		PrimitiveDoublyPalCyclicWord dcw = new PrimitiveDoublyPalCyclicWord(new int[testLength] , swPants.length());
		Curve c = new Curve(dcw, swPants);
		c.initialize();
		String dir ="LengthEquiv/";
	    statics.CreateDirectory.create(dir);	  
        statics.FileStuff.fileSentence(dir+filename+testLength+"_"+s1, "lislis={");
        BTermsNumber bt =cl.bracket(n1, cl.toNumber(c.toString()));
        statics.FileStuff.fileSentence(dir+filename+testLength+"_"+s1, bt.toMathematica(cl));
		while(c.length()==testLength ){
			//int[] s2 = cl.toNumber(c.baseword.toString());
		
			c=c.next();
	//		System.out.println(c.toString());
		    bt =cl.bracket(n1, cl.toNumber(c.toString()));
		    statics.FileStuff.fileSentence(dir+filename+testLength+"_"+s1,","+ bt.toMathematica(cl));
		
		}
		   statics.FileStuff.fileSentence(dir+filename+testLength+"_"+s1, "}");
		
	}
	
	 public static void printResults(BTermsNumber bt, CL cl, String w1, String w2, int testLength){
		 bt.simplify();
		 String sentence = "(*"+w1+", "+w2;
			  sentence = sentence+"\n The number of terms: "+bt.addAbsVCoef();
			  sentence = sentence+"\n Intersection number is "+cl.si(w1,w2)+"\n*)\n\n";
			  sentence = sentence+"theList={"+bt.toMathematica(cl)+"};\n";
			  sentence = sentence+"CountPairs[theList]\n\n";
	String dir ="LengthEquiv/";
			    statics.CreateDirectory.create(dir);	  
		statics.FileStuff.fileSentence(dir+"testLengtg_"+testLength+"_"+w1, sentence);		
	 }

}

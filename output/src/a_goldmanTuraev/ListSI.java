package a_goldmanTuraev;
import java.util.ArrayList;

import boundary.*;

public class ListSI {
	public static void main(String[] args){
	
		int testLength = 8;
		String z = "aAbB";
		SurfaceWord swPants = new SurfaceWord(z);
		SurfaceWord swTorus = new SurfaceWord("abAB");
		//We consider the normal closure of aaaabb
		String filename = "testst";
		
		
   	 

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[testLength] , swPants.length());
       Curve c = new Curve(dcw, swPants);
		c.initialize();
		String dir ="LengthEquiv/";
	    statics.CreateDirectory.create(dir);
      String sentence="Max[";
	    while(c.length()==testLength ){
			//int[] s2 = cl.toNumber(c.baseword.toString());
	    	Word w1 = new Word(Word.fromString(c.toString(),swPants.length()));	
	    	Curve ct = new Curve(w1,swTorus);
	  //  	sentence = "{Length[MonomialList[FrickePoly[\""+c.toString()+"\"]]],"+c.selfIntersection()+","+ct.selfIntersection()+"}";
	    	sentence= sentence+"Length[MonomialList[FrickePoly[\""+c.toString()+"\"]]]/"+c.selfIntersection()+",";
	    	c=c.next();
	//		
		   // statics.FileStuff.fileSentence(dir+filename+testLength+"_",sentence);

			  
		}

	   
     
	    System.out.println(sentence);
       
   	
    

		
	}
}

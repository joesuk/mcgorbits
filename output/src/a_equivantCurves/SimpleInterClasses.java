package a_equivantCurves;
import java.util.ArrayList;

import examples.Algorithms;
import boundary.*;

public class SimpleInterClasses {

	public static void main(String[] args){
		//int lenTestedC=10; //length of elements in the classes
		int lenSC= 17; //length of simple closed curves used to test.
		String filename = "siClasses_";
		CurveList cl = createListSC(lenSC);
		for(int lent = 3 ;lent <21;lent++){
			System.out.println(lent);
			printClass(filename+lent+"_"+lenSC, lenSC, lent,cl);
			
		}


	}

	public static CurveList createListSC(int len){
		SurfaceWord sw = new SurfaceWord("abAB");
	//	CurveList cl = new CurveList(sw);
		//build the list of curves of length k
		PrimitiveDoublyPalCyclicWord cw = new PrimitiveDoublyPalCyclicWord(new int[1], sw.length());
		SimpleCurve sc = new SimpleCurve(cw, sw);
		sc.initialize();
		CurveList scl = new CurveList(sw);
		while(sc.length()< len + 1){
			
			scl.add(sc);
			sc = sc.next();
		}
		
		return scl;
	}
	public static void printClass(String filename, int lenSC, int lenTestedC, CurveList listSC){
		 String dir ="simpleIntersectionEquivalent1/";
	    statics.CreateDirectory.create(dir);
	    filename = dir + filename;   
		SurfaceWord sw = new SurfaceWord("abAB");
		PrimitiveDoublyCyclicWord cw = new PrimitiveDoublyCyclicWord(new int[lenTestedC], sw.length());
		Curve c = new Curve(cw,sw);
		c.initialize();
		
		CurveList scl = new CurveList(sw);
		while(c.length()< lenTestedC + 1){
			scl.add(c);
			c = c.next();
		}
		
		ArrayList<CurveList> a = Algorithms.equivalentClass(scl, listSC);
		for(int i=0;i<a.size();i++){
			System.out.println( a.get(i).toString());
		//	System.out.println( "\n");
			if(a.get(i).size()>1){
				//One can actually print a CurveList.
				//Please refer to the output of this program
				String sentence = "{";
				System.out.println(a.get(i));
				for(int h = 0 ; h < a.get(i).size()-1 ; h++){
					sentence = sentence+"\""+a.get(i).get(h).toString()+"\""+ ",";
				}
				 statics.FileStuff.fileSentence(filename+"sizeClass_"+a.get(i).size(), sentence +"\""+  a.get(i).get(a.get(i).size()-1)+ "\"}" );
				}
		}
	}
}



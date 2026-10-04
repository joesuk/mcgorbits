package a_equivantCurves;

import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;

public class DriverNewBuser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String w="aaBBaBAbAbb";
		constructTwo(w);
		 w="aabAbbABBaB";
		constructTwo(w);
		w="aaBaBBAbbAb";
		constructTwo(w);
		 w="aabbAbABaBB";
		constructTwo(w);
	}
	
	


	
	public static String[] constructTwo(String w){
	
		String wInv = BuserAlgorithm.invert(w);
		String v1 = wInv+"b"+"b"+w+"b"+wInv+"b"+w;
		String v2= w+"b"+"b"+wInv+"b"+w+"b"+wInv;
		SurfaceWord sw= new SurfaceWord("aAbB");
		SurfaceWord sw1= new SurfaceWord("abAB");
		Word ww1pants = new Word( Word.fromString(v1, sw.length()));
		Word w1 = ww1pants.cyclicallyReduce();
		Word ww2pants = new Word( Word.fromString(v2, sw.length()));
		Word w2 = ww2pants.cyclicallyReduce();
		
		Curve c1pants = new Curve(w1, sw);
		Curve c1torus = new Curve(w1,sw1);
		Curve c2pants = new Curve(w2, sw);
		Curve c2torus = new Curve(w2,sw1);
		
		String[] list ={c1pants.toString(), c2pants.toString()};
		System.out.println("\""+  list[0]+"\","+"\t" +"\""+list[1]+"\",");
		return  list;
		
	
		
		
	}

}

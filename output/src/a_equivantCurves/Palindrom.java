package a_equivantCurves;

import boundary.Curve;
import boundary.CurveList;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;
import boundary.Word;

public class Palindrom {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String[] theList ={"abABabbb", "abABBBAB"};
		
		String s1 = theList[0];
		String s2= theList[1];
		System.out.println(statics.MoreHelpers.areInverseOrPalindromePairs(s1, s2));
	//	System.out.println(statics.MoreHelpers.areInverseOrPalindromePairs("ab", "AB"));
		
		for(int cl =2 ;cl<20 ;cl++){
			String z = "abAB";
		  // System.out.println(cl+" "+testCl(cl,z)+" "+z);
		//   System.out.println(cl+" "+testCl(cl,"aAbB")+" abAB");
		}

	}
	
	
	public static boolean testCl(int combLength, String z){
	//	SurfaceWord sw1 = new SurfaceWord("abAB");
		SurfaceWord sw = new SurfaceWord(z);
		
		CurveList cl = new CurveList(sw);
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[combLength] , sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();

		//We add to the curve-list cl ALL curve-words of combinatorial length CombLength 

		while(c.length()==combLength){
			cl.add(c);
			c = c.next();
			
			Word w =new  Word(Word.fromString(pal(c.baseword.toString()), sw.length()));
			Curve cp = new Curve(w, sw);
	//		Curve ct = new Curve(w, sw1);
			if(cp.selfIntersection() != c.selfIntersection()){
				String sent = cp.toString()+" false "+cp.toString()+" "+c.toString();
				System.out.println(sent);
				statics.FileStuff.fileSentence("testPalidrome",sent);
				return false;
				
			}
			
		//	if(cp.selfIntersection() != ct.selfIntersection()) System.out.println(cp.toString()+" "+ct.toString()+" false");
			;
			
			
		}
		statics.FileStuff.fileSentence("testPalidrome",combLength+" tested "+z);
		return true;
	}
	
	public static String pal(String s){
		String s1="";
		for(int h = 0 ;h < s.length() ; h++){
			s1=s.charAt(h)+s1;
		}
		return s1;
	}

}

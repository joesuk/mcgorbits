package a_equivantCurves;

import boundary.Curve;
import boundary.CurveList;
import boundary.SurfaceWord;
import boundary.Word;

public class SingleTests {

	/**
	 *Input 4 free homotipy clases in F2, and parameter of metric in pair of pants.
	 *Output: Length in pants of each class, Self-intersection in pair of pants and torus.
	 * @param args
	 */
	public static void main(String[] args) {
		String s1 ="aBaBBBB";
		String s2 ="aaaBB";
		String s3 ="aaabb";
		String s4 ="abaBabAB";
		
		System.out.println(s1.length());
		check1(s1,s2,s3,s4);
		
		Word w1 = new Word(Word.fromString("baa",4));
		Word w2 = new Word(Word.fromString("aab",4));
		System.out.println("\n"+compareTwo(w1,w2));
	}
	
	public static boolean compareClass(CurveList clist){
		for(int h =0 ; h < clist.size() ; h++){
			
		}
		return true;
	}
	
	
	public static boolean compareTwo(Word w1, Word w2){
		if(w1.length() != w2.length()) return false;
		
		for(int j  = 0 ;  j < w1.length() ; j++){
			
			if(w1.compareTo(w2.permute(j)) == 0 ) return true;
		}
		
		return false;
	}
	
	public static void check1(String s1, String s2, String s3, String s4){
		String z = "baBA";
		SurfaceWord sw = new SurfaceWord(z);
		
		
		Word w1 = new Word( Word.fromString(s1, sw.length()));
		Word w2 = new Word(Word.fromString(s2,sw.length()));
		Word w3 = new Word( Word.fromString(s3, sw.length()));
		Word w4 = new Word( Word.fromString(s4, sw.length()));
		

		Curve c1 = new Curve(w1, sw);
		Curve c2 = new Curve(w2, sw);
		Curve c3 = new Curve(w3, sw);
		Curve c4 = new Curve(w4, sw);
		
		
		
		
		int s11= c1.selfIntersection();
		int s22=c2.selfIntersection();
		int s33=c3.selfIntersection();
		int s44=c4.selfIntersection();
	
		
		String sentence0 = "Curve \t\t SI punct Torus "+"\t\t"+"SI pants \t\t GeomLength \n";
		String sentence1 =s1+"\t\t  "+s11+"\t\t";
		String sentence2=s2+"\t\t"+s22+"\t\t";
		String sentence3=s3+"\t\t"+s33+"\t\t";
		String sentence4=s4+"\t\t"+s44+"\t\t";
		
		sw = new SurfaceWord("abBA");
		double la = 10;
		double lb = 1;
		double lc = 10;


		double[][] mat = hypLengthComp.LengthSamples.computeMatrix(la, lb, lc);	
		sw.setHyperbolicMatrix(mat);
		
		c1 = new Curve(w1, sw);
		c2 = new Curve(w2, sw);
		c3 = new Curve(w3, sw);
		c4 = new Curve(w4, sw);
		
		s11= c1.selfIntersection();
		s22=c2.selfIntersection();
		s33=c3.selfIntersection();
		s44=c4.selfIntersection();
		
		 sentence1 = sentence1+s11+"\t\t"+c1.hyperbolicLength()+"\n";
		 sentence2=  sentence2+s22+"\t\t"+c2.hyperbolicLength()+"\n";
		 sentence3=	 sentence3+s33+"\t\t"+c3.hyperbolicLength()+"\n";
	     sentence4=	 sentence4+s44+"\t"+c4.hyperbolicLength()+"\n";
	//			 "The combinatorial length of the curves is "+c1.length();
		String sen =sentence0+sentence1+sentence2+sentence3+sentence4;
		System.out.println(sen);
	//	statics.FileStuff.fileSentence("EquivalentCurves2013/FinalTests",sen);
	}

}

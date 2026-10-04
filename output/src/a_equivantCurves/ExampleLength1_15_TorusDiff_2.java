package a_equivantCurves;

import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;

public class ExampleLength1_15_TorusDiff_2 {

	/**
	 *Input 4 free homotipy clases in F2, and parameter of metric in pair of pants.
	 *Output: Length in pants of each class, Self-intersection in pair of pants and torus.
	 * @param args
	 */
	public static void main(String[] args) {
		String s1 ="aabABAbbaBBabaB";
		String s2 ="aabbABAbaBabaBB";
		String s3 ="aaBabaBBabbABAb";
		String s4 ="aaBBabaBabABAbb";
		
	
		check1(s1,s2,s3,s4);
	}
	
	public static void check1(String s1, String s2, String s3, String s4){
		String z = "abAB";
		SurfaceWord swTorus= new SurfaceWord(z);
		
		
		Word w1 = new Word( Word.fromString(s1, swTorus.length()));
		Word w2 = new Word(Word.fromString(s2,swTorus.length()));
		Word w3 = new Word( Word.fromString(s3, swTorus.length()));
		Word w4 = new Word( Word.fromString(s4, swTorus.length()));
		

		Curve c1 = new Curve(w1, swTorus);
		Curve c2 = new Curve(w2, swTorus);
		Curve c3 = new Curve(w3, swTorus);
		Curve c4 = new Curve(w4, swTorus);
		
		
		
		
		int s11= c1.selfIntersection();
		int s22=c2.selfIntersection();
		int s33=c3.selfIntersection();
		int s44=c4.selfIntersection();
	
		
		
		String sentence0 = "Curve \t\t SI punct Torus "+"\t\t"+"SI pants \n";
		String sentence1 =s1+"\t\t  "+s11+"\t\t";
		String sentence2=s2+"\t\t"+s22+"\t\t";
		String sentence3=s3+"\t\t"+s33+"\t\t";
		String sentence4=s4+"\t\t"+s44+"\t\t";
		
		SurfaceWord swPants = new SurfaceWord("aAbB");
		
		
		c1 = new Curve(w1, swPants);
		c2 = new Curve(w2, swPants);
		c3 = new Curve(w3, swPants);
		c4 = new Curve(w4, swPants);
		
		s11= c1.selfIntersection();
		s22=c2.selfIntersection();
		s33=c3.selfIntersection();
		s44=c4.selfIntersection();
		 sentence1 = sentence1+s11+"\t\t"+"\n";
		 sentence2=  sentence2+s22+"\t\t"+"\n";
		 sentence3=	 sentence3+s33+"\t\t"+"\n";
	     sentence4=	 sentence4+s44+"\t\t"+"\n"+
				 "The combinatorial length of the curves is "+c1.length();
		String sen =sentence0+sentence1+sentence2+sentence3+sentence4;
		System.out.println(sen);
		statics.FileStuff.fileSentence("FinalTests",sen);
	}

}

package a_equivantCurves;

import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;

public class ExamplLength_11ClassWith3Members {

	/**
	 *Input 4 free homotipy clases in F2, and parameter of metric in pair of pants.
	 *Output: Length in pants of each class, Self-intersection in pair of pants and torus.
	 * @param args
	 */
	public static void main(String[] args) {
		String s1 ="aabaaBAAbAB";
		String s2 ="aabaBAAbAAB";
		String s3 ="aabAABAbaaB";
		check1(s1);
		check1(s2);
		check1(s3 );
		
	}
	
	public static void check1(String s1){
		

		SurfaceWord sw = new SurfaceWord("abAB");
		
		
		Word w1 = new Word( Word.fromString(s1, sw.length()));
			

		Curve c1 = new Curve(w1, sw);
		
		
		
		
		int s11= c1.selfIntersection();
		
		String sentence1 =s1+"\t\t  "+s11+"\t\t";
		
		sw = new SurfaceWord("aAbB");
		double la = 0.1;
		double lb =0.01;
		double lc = 0.1;


		double[][] mat = hypLengthComp.LengthSamples.computeMatrix(la, lb, lc);	
		sw.setHyperbolicMatrix(mat);
		
		c1 = new Curve(w1, sw);
	
	
		s11= c1.selfIntersection();
		
		
		 sentence1 = sentence1+s11+"\t"+c1.hyperbolicLength();
	
		 	 
		
		System.out.println(sentence1);
		String dir ="Examples/";
	    statics.CreateDirectory.create(dir);
		statics.FileStuff.fileSentence(dir+"FinalTests",sentence1);
	}

}

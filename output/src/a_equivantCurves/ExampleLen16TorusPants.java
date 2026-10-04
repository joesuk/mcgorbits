package a_equivantCurves;

import boundary.Curve;
import boundary.SurfaceWord;
import boundary.Word;

public class ExampleLen16TorusPants {

	/**
	 *Input 4 free homotipy clases in F2, and parameter of metric in pair of pants.
	 *Output: Length in pants of each class, Self-intersection in pair of pants and torus.
	 * @param args
	 */
	public static void main(String[] args) {
		String s1 ="aaabaaBabaBAbAAB";
		String s2 ="aaabaaBAbAABabaB";
		String s3 ="aaabaBaabaBAAbAB";
		String s4 ="aaabaBabAABAbaaB";
		String s5 ="aaabAABAbaBabaaB";
	 	String s6 ="aaabABAAbaBaabaB";
		
	 
	   System.out.println(s1.length());
		
		String sentence0 = 
				"The combinatorial length of the curves is "+s1.length()+
				"Curve \t\t SI punct Torus "+"\t\t"+"SI pants  \n";
		String dir ="Examples/";
	    statics.CreateDirectory.create(dir);
		statics.FileStuff.fileSentence(dir+"FinalTests",sentence0);
		check1(s1);
		check1(s2);
		check1(s3);
		check1(s4);
		check1(s5);
		check1(s6);
		
	}
	
	public static void check1(String s1){
		String z = "abAB";
		SurfaceWord sw = new SurfaceWord(z);
		
		
		Word w1 = new Word( Word.fromString(s1, sw.length()));
			

		Curve c1 = new Curve(w1, sw);
		
		
		
		
		int s11= c1.selfIntersection();
		
		String sentence1 =s1+"\t\t  "+s11+"\t\t";
		
		sw = new SurfaceWord("aAbB");
		
		
		c1 = new Curve(w1, sw);
	
	
		s11= c1.selfIntersection();
		
		
		 sentence1 = sentence1+s11;
	
		 
				 
		
		System.out.println(sentence1);
		String dir ="Examples/";
	    statics.CreateDirectory.create(dir);
		statics.FileStuff.fileSentence(dir+"FinalTests",sentence1);
	}

	
	public static void check1(String s1, String s2, String s3, String s4){
		
		SurfaceWord swTorus = new SurfaceWord( "abAB");
		
		
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
	
		
		String sentence0 = "Curve \t\t SI punct Torus "+"\t\t"+"SI pants \t\t LengthPants \n";
		String sentence1 =s1+"\t\t  "+s11+"\t\t";
		String sentence2=s2+"\t\t"+s22+"\t\t";
		String sentence3=s3+"\t\t"+s33+"\t\t";
		String sentence4=s4+"\t\t"+s44+"\t\t";
		
		swTorus = new SurfaceWord("aAbB");
		double la = 10;
		
		
		c1 = new Curve(w1, swTorus);
		c2 = new Curve(w2, swTorus);
		c3 = new Curve(w3, swTorus);
		c4 = new Curve(w4, swTorus);
		
		s11= c1.selfIntersection();
		s22=c2.selfIntersection();
		s33=c3.selfIntersection();
		s44=c4.selfIntersection();
		
		 sentence1 = sentence1+s11+"\t\t"+"\n";
		 sentence2=  sentence2+s22+"\t\t"+"\n";
		 sentence3=	 sentence3+s33+"\t\t"+"\n";
	     sentence4=	 sentence4+s44+"\t"+"\n"+
				 "The combinatorial length of the curves is "+c1.length();
		String sen =sentence0+sentence1+sentence2+sentence3+sentence4;
		System.out.println(sen);
		String dir ="Examples/";
	    statics.CreateDirectory.create(dir);
		statics.FileStuff.fileSentence(dir+"FinalTests",sen);
	}

	public static String invertB(String s){
		String answer="";
		for(int h = 0 ; h < s.length() ; h++)
			if(s.charAt(h)=='a' || s.charAt(h)== 'A'){
				answer=answer+s.charAt(h);
			}else if(s.charAt(h)=='b'){
				answer = answer+'B';
			}else answer = answer + 'b';
		return answer;
	}
	
	public static String invertA(String s){
		String answer="";
		for(int h = 0 ; h < s.length() ; h++)
			if(s.charAt(h)=='a' || s.charAt(h)== 'A'){
				answer=answer+s.charAt(h);
			}else if(s.charAt(h)=='b'){
				answer = answer+'B';
			}else answer = answer + 'b';
		return answer;
	}
	

}

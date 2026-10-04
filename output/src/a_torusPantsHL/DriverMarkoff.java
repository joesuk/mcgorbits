package a_torusPantsHL;

import hypLengthComp.LengthSamples;

import java.util.ArrayList;

import boundary.*;

public class DriverMarkoff {
	public static void main(String[] args) {

	
		String dir = "MarkoffDataNew/";
		statics.CreateDirectory.create(dir);


		String[] s16 =  {
				"aabb", "aaBB", "abaB", "abAb"		};
				
		
				
				
				
				for(int h = 0 ; h < s16.length ; h++){
			print_all(s16[h],dir);
		}
		
		
		
		

	}
	
	public static void print_all(String s1, String dir){
		String z ="abAB";
		SurfaceWord sw = new SurfaceWord(z);
		double[][] m={{1,1,1,2},{1,-1,-1,2}};
		sw.setHyperbolicMatrix(m);
		Word i1 = new Word(Word.fromString(s1, z.length()));
		
		Curve w11 = new Curve(i1, sw);
		double[] m1=w11.hyperbolicMatrix();
	//	printMatrix(m1);
		int a=(int) m1[0];
		int b =(int) m1[1];
		int c=(int) m1[2];
		int d =(int) m1[3];
		
		String mat = "{"+a+","+b+","+c+","+d+"},";
		statics.FileStuff.fileSentenceNoNewLine(dir+"MATdataMarkoff_WL"+ s1.length()+"_SI_"+w11.selfIntersection(), mat);
		
		double delta = (d-a)*(d-a)+4*c*b;
		double sq_delta = Math.sqrt(delta);
		System.out.println(s1 );
		
		String sent =  s1+"\n"+printMatrix(m1)+
				"\nDet is "+(a*d-b*c)+
				"\nDelta is "+delta+ "\nTrace is "+ (a+d) +
				"\nSquare root of delta is "+sq_delta+"\n"+
				"Radius is "+Math.abs((sq_delta*0.5)/c) +
				"\nEndpoint 1: "+(-d+a-sq_delta)/(2*c)+"\n"+
				"Endpoint 2: "+(-d+a+sq_delta)/(2*c)+"\n\n";
	
		
	//	statics.FileStuff.fileSentence(dir+"dataMarkoff_WL"+ s1.length()+"_SI_"+w11.selfIntersection(), sent);
	
	
}
	
	public static String printMatrix(double[] m){
			String s = m[0]+"\t"+m[1]+"\n"+m[2]+"\t"+m[3];
			System.out.println(s);
			return s;
		
	}
}

package a_siddhartha_Bracket_in_ModularSurface;
import java.util.ArrayList;

import boundary.*;

public class Example2{
	public static void main(String[] args){

		String z = "aAbB"; //a = S, b = T
		SurfaceWord sw = new SurfaceWord(z);
		double[][] mat = 
		{{0,1,-1,0},
				{1,1,0,1}};
		sw.setHyperbolicMatrix(mat);
		String st= "ababbbaba";	
		//comp(sw,st);
		double[] crad;
		
		
		Word term, termRed;
		System.out.println("Bracket computations");
		String st1="bbbabb";
		
		st= "BBababbbababb";
		System.out.println(st+" "+st1);
		term =    Word.fromString((st+st1), sw.length());
		System.out.println("term1 "+term.toString());
		termRed = term.reduce();
		termRed = term.cyclicallyReduce();
		System.out.println("term "+sign(st1,st,sw)+" "+termRed.toString());
		
		System.out.println();
		st= "Bababbbabab";
		System.out.println(st+" "+st1+" "+"sign "+sign(st1,st,sw));
		System.out.println();
		st= "bbababbbabaBB";
		System.out.println(st+" "+st1+" "+"sign "+sign(st1,st,sw));
		System.out.println();
		st= "bbbababbbabaBBB";
		System.out.println(st+" "+st1+" "+"sign "+sign(st1,st,sw));
		System.out.println();
		st= "bbbbababbbabaBBBB";
		System.out.println(st+" "+st1+" "+"sign "+sign(st1,st,sw));
		System.out.println();
		st= "bbbaBababbbababaBBB";
		System.out.println(st+" "+st1+" "+"sign "+sign(st1,st,sw));
		
		
		System.out.println();	
		System.out.println();	
		System.out.println();	
		
		
		
		
		
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();	
		
		
		st= "Bababbbabab";	
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();	

		
		st= "BBababbbababb";
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();	
		
		st= "bababbbabaB";	
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();	
		System.out.println();	
		
		st= "bbababbbabaBB";	
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();	
		System.out.println();	
		
		st= "bbbababbbabaBBB";	
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();
		
		
		st= "babbbab";	
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();
		
		
		st= "bbbabbbaB";	
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();
		
		st= "abbbabb";	
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();
		
		st= "babbbab";	
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();
		
		st= "bbabbba";	
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();
		
		st= "ababbbaba";	
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();
		
		
		
		
		System.out.println();	
		
		
		st= "bbbabb";	
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();
		
		crad= cr( st , sw);
		System.out.println(st);
		System.out.println("center "+crad[0]);
		System.out.println("rad "+crad[1]);
		System.out.println();
		
	}
	
	/*
	 * Gets two words.
	 * Computes sign of the interection of the oriented axis.
	 * Need to input two hyperbolic matrix.
	 * 
	 */
	public static int sign(String w1, String w2, SurfaceWord sw){
		double[] m1 = getMatrix(sw,w1);
		double[] m2 = getMatrix(sw,w2);
		double tr1 = trace(m1);
		double tr2 = trace(m2);
		double[] fp1 = fp(m1);
		double[] fp2 = fp(m2);
		
		if(fp2[0] < fp1[0] & fp1[0]<fp2[1] & fp2[1]< fp1[1]){
			if(tr1*tr2>0) return 1;
			else return -1;
		}else if(fp1[0] < fp2[0] & fp2[0]<fp1[1] & fp1[1]< fp2[1]){
			if(tr1*tr2>0) return -1;
			else return 1;
		
		}
		
		return 0;
	}
	
	
	public static double[] cr(String str, SurfaceWord sw){

		Word v1= Word.fromString(str, sw.length());
		Curve c1  = new Curve(v1,sw);
		double[] m = c1.getMatrix();
		System.out.println("trace "+(m[0]+m[3]));
		if(m[2] !=0){
		return new double[] {(m[0]-m[3])/(2*m[2]) , Math.sqrt((m[0]+m[3])*(m[0]+m[3])-4)/(2*m[2])};
		}else{
			if(m[0]!=1){
				return new double[] { m[0]*m[1]/(1-m[0]*m[0]),1111111 };
			}else{
				return new double[] {1111111111,111111111};
			}
		}
		
	}
	
	public static double trace(double m[]){
		return m[0]+m[3];
	
	}
	public static double[] fp(double m[]){
		double delta = (m[0]-m[3])*(m[0]-m[3])+4*m[1]*m[2];
	 	System.out.println("delta "+delta+" "+(m[0]+m[3]));
		if(delta < 0){
			System.out.println("elliptic");
			return new double[] {20000000,0};
		}else if(delta==0){
			System.out.println("Parabolic");
			return new double[] {(m[0]-m[3])/2,2000000};
		}
		if(m[2]!=0){
			System.out.println("hyperbolic");
			double x1=((m[0]-m[3])+Math.sqrt(delta))/(2*m[2]);
			double x2=((m[0]-m[3])-Math.sqrt(delta))/(2*m[2]);
			return new double[] {Math.min(x1,x2),Math.max(x1, x2)};
		}else {
			System.out.println("elliptic");
			double[] answer= {m[1]/(m[3]-m[0]),100000};
			return answer;
		}

	}
	
	public static void comp(SurfaceWord sw, String str){
		Word v1;
		Curve c1;
		double[] m;
		double[] e;

		
		v1= Word.fromString(str, sw.length());
		c1  = new Curve(v1,sw);
		m= c1.getMatrix();
		e = fp(m);
		System.out.println(str);
		System.out.println("Fixed points "+e[0]+" "+e[1]);
		System.out.println(m[0]+" "+m[1]);
		System.out.println(m[2]+" "+m[3]);
		System.out.println("Trace "+(m[0]+m[3]));
		System.out.println();

	}

	public static double[] getMatrix(SurfaceWord sw, String str){
		Word v1;
		Curve c1;
		double[] m;
		v1= Word.fromString(str, sw.length());
		c1  = new Curve(v1,sw);
		m= c1.getMatrix();
		return m;
	

	}


}
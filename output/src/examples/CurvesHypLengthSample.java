package examples;
import java.util.Random;

import boundary.*;



public class CurvesHypLengthSample {
	public static void main(String[] args){
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		int total = 400;
		double xiyi=0;
		double xisquared =0;
		double xi=0;
		double yi=0;
		
			double a =0.3;
			double[] x=runSample(a,21,30, 200, 30,sw, "allLength1");
			
			
		
		double m = (xiyi)-xi*yi/total;
		m = m/(xisquared - xi/total);
		double b = yi - m*xi;
		System.out.println(m+ " "+b);
	}
	public static double[] runSample(double aa, double bb, double cc, int sampleSize, int length, SurfaceWord sw, String filename){

		/****************************************/
		/*    Sorted list by hyperbolic length  */
		/****************************************/
		//Need  a < 1 < b < c

		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};


		sw.setHyperbolicMatrix(mat);

		//System.out.println(("trace a" +mat[0][0]+mat[0][3]));
		//System.out.println("trace b "+ mat[1][0]+mat[1][3]);

		CurveList cl = new CurveList(sw);

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1] , sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();

		int counter=0;
		double totalLeng=0;
		double totalLengSquare=0;
		
		while(counter<=sampleSize){

			counter++;
			double leng=randomWordVector(length,sw);
		//	String legend = leng+"\t";

			
			statics.FileStuff.fileSentence(filename, leng+" ");
			totalLeng=totalLeng+leng;
			totalLengSquare=totalLengSquare+Math.pow(leng, 2);
		
			//statics.FileStuff.fileSentence("sampleSize"+sampleSize+"lenght"+length, legend);		

		}


		double mean = totalLeng/sampleSize;
		double var = totalLengSquare/sampleSize-Math.pow(mean, 2)/sampleSize;

		//		String legend = "Mean \t"+mean+"\t Var \t"+var+ " \t Mean/L^2 \t"+cmean+" \t Var/L^2 \t"+cvar+"\t par \t"+ aa+ " \t"+bb+"\t "+cc;
		double la = 2*(1+aa)/(1-aa);

		//String legend = mean+ "\t" + aa+ " \t"+ la+" \t"+bb+"\t "+cc;

		//statics.FileStuff.fileSentence(filename, legend);
		double[] answer = {mean,var}; 
		return answer;


	}

	/**
	 * This method gives a random  word vector.
	 * @param length
	 * @return
	 */
	public static double randomWordVector(int length, SurfaceWord sw) {

		int[] v = new int[length];
		Random generator = new Random();
		v[0]=generator.nextInt(sw.length());

		for (int h = 1; h <= length-1; h++) {
			int aux=generator.nextInt(sw.length()-1);
			if(aux< sw.bar((v[h-1]))){
				v[h] = aux;
			}else v[h]=aux+1;

			//	System.out.println(v[h]);

		}
		int aux=generator.nextInt(sw.length()-2);

		while(v[length-1]==sw.bar(v[0])){
			aux=generator.nextInt(sw.length()-1);
			if(aux<sw.bar(v[length-2])){
				v[length-1] = aux;
			}else v[length-1]=aux+1;
		}
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(v , sw.length());
		Curve c = new Curve(dcw, sw);
		return c.hyperbolicLength();

	}

	public static double randomWordVectorTrace(int length, SurfaceWord sw) {

		int[] v = new int[length];
		Random generator = new Random();
		v[0]=generator.nextInt(sw.length());

		for (int h = 1; h <= length-1; h++) {
			int aux=generator.nextInt(sw.length()-1);
			if(aux< sw.bar((v[h-1]))){
				v[h] = aux;
			}else v[h]=aux+1;

			//	System.out.println(v[h]);

		}
		int aux=generator.nextInt(sw.length()-2);

		while(v[length-1]==sw.bar(v[0])){
			aux=generator.nextInt(sw.length()-1);
			if(aux<sw.bar(v[length-2])){
				v[length-1] = aux;
			}else v[length-1]=aux+1;
		}
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(v , sw.length());
		Curve c = new Curve(dcw, sw);
		return c.hyperbolicTrace();

	}
}

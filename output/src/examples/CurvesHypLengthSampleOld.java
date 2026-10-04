package examples;
import java.util.Random;

import boundary.*;



public class CurvesHypLengthSampleOld {
	public static void main(String[] args){
		String z = "aAbB";
		int sampleSize =100000;
		int combLength = 32;
		double b= 5;
		double c=6;
		String filename="SampleSize"+sampleSize+"combLength"+combLength+"b"+b+"c"+c;
		//statics.FileStuff.fileSentence(filename, "Mean \t a \t length a \t b \t c \t Mean");
		SurfaceWord sw = new SurfaceWord(z);
		for(int j=0 ; j < 10 ; j ++){
		for(int h=1; h < 100 ; h++){
			runSample(0.1*j+h*0.001,b,c, sampleSize, combLength,sw, filename+j);
		}
		}
	}
	
	
	public static void runSample(double aa, double bb, double cc, int sampleSize, int length, SurfaceWord sw, String filename){	
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
			String legend = leng+"\t";

			totalLeng=totalLeng+leng;
			totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			//statics.FileStuff.fileSentence("sampleSize"+sampleSize+"lenght"+length, legend);		

		}


		double mean = totalLeng/sampleSize;
		//double var = totalLengSquare/sampleSize-Math.pow(mean, 2)/sampleSize;

		//		String legend = "Mean \t"+mean+"\t Var \t"+var+ " \t Mean/L^2 \t"+cmean+" \t Var/L^2 \t"+cvar+"\t par \t"+ aa+ " \t"+bb+"\t "+cc;
		double la = 2*Math.log((1+Math.sqrt(aa))/(1-Math.sqrt(aa)));

		String legend = mean+ "\t" + aa+ " \t"+ la+" \t"+bb+"\t "+cc+"\t "+mean;

		
		statics.FileStuff.fileSentence(filename, legend);
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

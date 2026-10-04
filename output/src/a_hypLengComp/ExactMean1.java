package a_hypLengComp;
import hypLengthComp.LengthSamples;
import statics.FileStuff;
import boundary.Curve;
import boundary.PrimitiveCyclicWord;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SimpleCurve;
import boundary.SurfaceWord;
import boundary.Word;


/**
 * @author moira
 *
 */

public class ExactMean1 {
	
	

	
	/**
	 * Computes mean(all words of comb. length = length) and variance in pants (both divided by word length). 
	 * Saves results in filename
	 * @param sw
	 * @param length
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 */
	public static void meanVarPants(SurfaceWord sw,int length, double la, double lb, double lc, String filename){
		double[][] mat = LengthSamples.computeMatrix(la,lb,lc);
		sw.setHyperbolicMatrix(mat);	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[length], sw.length());
		int counter=0;
		double totalLeng=0;
		double totalLengSquare=0;
		Curve c = new Curve(dcw, sw);
		c.initialize();

		while(c.length()==length){
			//System.out.println(c);
			counter++;
			double leng=c.hyperbolicLength();
			//	System.out.println(leng);
			totalLeng=totalLeng+leng;
			totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			c = c.next();
		}
		double mean = totalLeng/counter;
		double var = totalLengSquare/counter-mean*mean;
		double st = Math.sqrt(var);


		String legend =length+"\t"+ la+"\t"+ lb+"\t"+ lc+"\t "+mean+"\t"+var+"\t"+st;
		System.out.println(legend);

		statics.FileStuff.fileSentence(filename, legend);
		//	return mean;
	}












	/**
	 * Computes mean(all words of comb. length = length) and the variance.
	 * Computes mean and variance of traces of all words too.
	 * Saves results in filename
	 * @param sw
	 * @param wordLength
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @return
	 */
	public static void MeanVar_LengthPants(SurfaceWord sw,int wordLength, double la, double lb, double lc, String filename){
		

		double[][] mat = LengthSamples.computeMatrix(la,lb,lc);
	
		sw.setHyperbolicMatrix(mat);	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[wordLength], sw.length());
		int counter=0;
		double totalLeng=0;
		double totalLengSquare=0;
	//	double totalTrace=0;
	//	double totalTraceSquare=0;
		Curve c = new Curve(dcw, sw);
		c.initialize();

		while(c.length()==wordLength){
			//System.out.println(c);
			counter++;
			double leng=c.hyperbolicLength();
		//	double tr= c.hyperbolicTrace();
			//	System.out.println(leng);
			totalLeng=totalLeng+leng;
			totalLengSquare=totalLengSquare+Math.pow(leng, 2);
		//	totalTrace=totalTrace+tr;
		//	totalTraceSquare=totalTraceSquare+Math.pow(tr, 2);
			c = c.next();
		}
		double mean = totalLeng/counter;
		double var = totalLengSquare/counter-mean*mean;
	//	double meanTrace = totalTrace/counter;
	//	double varTrace = totalTraceSquare/counter-meanTrace*meanTrace;


		String legend =wordLength+"\t"+  la+" \t"+lb+"\t "+lc+"\t "+mean/wordLength+"\t"+var/wordLength;
		System.out.println(legend);

		statics.FileStuff.fileSentence(filename, legend);
	}

	
	/**
	 * Computes mean(all words of comb. length = length) and variance in pants (both divided by word length). 
	 * Saves results in filename
	 * @param sw
	 * @param length
	 * @param x
	 * @param s
	 * @param t
	 * @param filename
	 */
	public static void meanVarTorus11(SurfaceWord sw,int length, double x, double s, double t, String filename){
		
		//here you write the correct parameters.
		double det1 = 1/Math.sqrt(t*x*x-t*x);
		double det2 = 1/Math.sqrt(t*x*x-t*x);
		double[][] mat ={{t*x*det1,x*det1,t*det1,x*det1},{0,x*det2,-s*det2,x*(1+s)*det2}};
		sw.setHyperbolicMatrix(mat);	
		
		
		
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[length], sw.length());
		int counter=0;
		double totalLeng=0;
		double totalLengSquare=0;
		Curve c = new Curve(dcw, sw);
		c.initialize();

		while(c.length()==length){
		//	System.out.println(c+" "+c.hyperbolicLength());
			counter++;
			double leng=c.hyperbolicLength();
			//	System.out.println(leng);
			totalLeng=totalLeng+leng;
			totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			c = c.next();
		}
		double mean = totalLeng/counter;
		double var = totalLengSquare/counter-mean*mean;
		double st = Math.sqrt(var);


		String legend =length+"\t"+ x+"\t"+ s+"\t"+ t+"\t "+(mean/length)+"\t"+(var/length)+"\t";
		System.out.println(legend);

		statics.FileStuff.fileSentence(filename, legend);
		//	return mean;
	}
	


	
}

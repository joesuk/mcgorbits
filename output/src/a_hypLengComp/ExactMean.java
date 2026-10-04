package a_hypLengComp;
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
/**
 * @author moira
 *
 */
public class ExactMean {
	
	/**
	 * Returns mean(all words of comb. length = length)/(combinatorial length)
	 * @param sw aAbB
	 * @param swc  abcABC
	 * @param length
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 */
	public static double  meanDividedByCLabc(SurfaceWord swc, SurfaceWord sw,int length, double la, double lb, double lc, String filename){
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[length], swc.length());
		int counter=0;
		double totalLeng=0;
		double totalLengSquare=0;
		Curve c = new Curve(dcw, sw);
		c.initialize();

		while(c.length()==length ){
			//System.out.println(c);
						//Check that c does not contain ab, bc, ca,  BA, CB, AC.
			//If it doesn't answer=true
			boolean answer= true;
			
			if(answer){
				counter++;
				//Pass the abc curve to ab, compute length and store. 
				String sc=c.toString();
				
				//In sc replace all c by BA, C... get a String c1
				String c1 = "abb"; //c is in aAbB
			
				Word w2 = new Word(Word.fromString(c1,4));
				Curve c2 = new Curve(w2, sw);
				double leng=c2.hyperbolicLength();
				totalLeng=totalLeng+leng;
				
			
			//	totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			//Find the next abc curve.
			}
			c = c.next();//c is in abcABC
		}
		double mean = totalLeng/(counter*length);
		//	double var = totalLengSquare/counter-mean*mean;
   
   if(mean < 1000000000){    
		String legend =length+"\t"+la+"\t"+lb+"\t"+lc+"\t"+mean;
		String legend1 ="["+la+","+lb+","+lc+","+mean+"],";
		System.out.println(legend);
		statics.FileStuff.fileSentence(filename, legend);
		statics.FileStuff.fileSentence(filename+"x", legend1);
   }
		
		return mean;
	}

	
	public static void meanSimple(SurfaceWord sw,int length, double la, double lb, double lc, String filename){
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);	
		PrimitiveCyclicWord dcw = new PrimitiveCyclicWord(new int[1], sw.length());
		SimpleCurve sc = new SimpleCurve(dcw, sw);
		int counter=0;
		double totalLeng=0;
		double totalLengSquare=0;
		//Curve c = new Curve(sc, sw);
		sc.initialize();

		while(sc.length()==length){
			//System.out.println(c);
			counter++;
			double leng=sc.hyperbolicLength();
			//	System.out.println(leng);
			totalLeng=totalLeng+leng;
			totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			
			sc = sc.next();
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
	 * Computes mean(all words of comb. length = length) and variance. 
	 * Saves results in filename
	 * @param sw
	 * @param length
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 */
	public static void mean(SurfaceWord sw,int length, double la, double lb, double lc, String filename){
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
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
	 * Returns mean(all words of comb. length = length)/(combinatorial length)
	 * @param sw
	 * @param length
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 */
	public static double  meanDividedByCL(SurfaceWord sw,int length, double la, double lb, double lc, String filename){
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[length], sw.length());
		int counter=0;
		double totalLeng=0;
		double totalLengSquare=0;
		Curve c = new Curve(dcw, sw);
		c.initialize();

		while(c.length()==length ){
			//System.out.println(c);
			counter++;
			double leng=c.hyperbolicLength();
			//	System.out.println(leng);
			totalLeng=totalLeng+leng;
			//	totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			c = c.next();
		}
		double mean = totalLeng/(counter*length);
		//	double var = totalLengSquare/counter-mean*mean;
   
   if(mean < 1000000000){    
		String legend =length+"\t"+la+"\t"+lb+"\t"+lc+"\t"+mean;
		String legend1 ="["+la+","+lb+","+lc+","+mean+"],";
		System.out.println(legend);
		statics.FileStuff.fileSentence(filename+"other", legend);
		statics.FileStuff.fileSentence(filename+"xxx", legend1);
   }
		
		return mean;
	}

	
	public static double  meanDividedByCLNOFile(SurfaceWord sw,int length, double la, double lb, double lc, String filename){
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[length], sw.length());
		int counter=0;
		double totalLeng=0;
		double totalLengSquare=0;
		Curve c = new Curve(dcw, sw);
		c.initialize();

		while(c.length()==length ){
			//System.out.println(c);
			counter++;
			double leng=c.hyperbolicLength();
			//	System.out.println(leng);
			totalLeng=totalLeng+leng;
			//	totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			c = c.next();
		}
		double mean = totalLeng/(counter*length);
		//	double var = totalLengSquare/counter-mean*mean;
  
		
		return mean;
	}



	public static void meanDividedByCLkl(SurfaceWord sw,int length,  double lb, double ct, String filename){
		double[] parameters = LengthSamples.computeParameters(lb,lb,ct*lb	);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
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
			//	totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			c = c.next();
		}
		double mean = totalLeng/(counter*length*lb);
		//	double var = totalLengSquare/counter-mean*mean;


		String legend =lb+"\t"+length+"\t"+ct+"\t"+mean;
		System.out.println(legend);

		statics.FileStuff.fileSentence(filename, legend);
		//	return mean;
	}


	public static void meanDividedByCLandL(SurfaceWord sw,int length, double la, double lb, double lc, String filename){
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
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
			//	totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			c = c.next();
		}
		double mean = totalLeng/(counter*length*lb);
		//	double var = totalLengSquare/counter-mean*mean;


		String legend =la+"\t"+lb+"\t"+lc+"\t"+lc/lb+"\t"+mean;
		System.out.println(legend);

		statics.FileStuff.fileSentence(filename, legend);
		//	return mean;
	}



	/**
	 * Returns mean(all words of comb. length = length) 
	 * @param sw
	 * @param length
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @return
	 */
	public static double meanNotSavingInFile(SurfaceWord sw,int length, double la, double lb, double lc, String filename){
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[length], sw.length());
		int counter=0;
		double totalLeng=0;
		//double totalLengSquare=0;
		Curve c = new Curve(dcw, sw);
		c.initialize();

		while(c.length()==length){
			//System.out.println(c);
			counter++;
			double leng=c.hyperbolicLength();
			//	System.out.println(leng);
			totalLeng=totalLeng+leng;
			//totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			c = c.next();
		}
		double mean = totalLeng/counter;

		return mean;
	}


	/**
	 * Computes mean(all words of comb. length = length) and the variance.
	 * Computes mean and variance of traces of all words too.
	 * Saves results in filename
	 * @param sw
	 * @param length
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @return
	 */
	public static void meanTraceParam_a_b_c(SurfaceWord sw,int length, double aa, double bb, double cc, String filename){
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[length], sw.length());
		int counter=0;
		double totalLeng=0;
		double totalLengSquare=0;
		double totalTrace=0;
		double totalTraceSquare=0;
		Curve c = new Curve(dcw, sw);
		c.initialize();

		while(c.length()==length){
			//System.out.println(c);
			counter++;
			double leng=c.hyperbolicLength();
			double tr= c.hyperbolicTrace();
			//	System.out.println(leng);
			totalLeng=totalLeng+leng;
			totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			totalTrace=totalTrace+tr;
			totalTraceSquare=totalTraceSquare+Math.pow(tr, 2);
			c = c.next();
		}
		double mean = totalLeng/counter;
		double var = totalLengSquare/counter-mean*mean;
		double meanTrace = totalTrace/counter;
		double varTrace = totalTraceSquare/counter-meanTrace*meanTrace;


		String legend =length+"\t"+  aa+" \t"+bb+"\t "+cc+"\t "+mean+"\t"+var+"\t "+meanTrace+"\t"+varTrace;
		System.out.println(legend);

		statics.FileStuff.fileSentence(filename, legend);
	}

	public static void meanTraceLengths(SurfaceWord sw,int combLength, double la, double lb, double lc, double cte,String filename){
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);

		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);	
		
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[combLength], sw.length());
		int counter=0;
		
		double totalTrace=0;
		//double totalTraceSquare=0;
		Curve c = new Curve(dcw, sw);
		c.initialize();

		while(c.length()==combLength){
			counter++;
			double tr= c.hyperbolicTrace();
			totalTrace=totalTrace+tr;
			c = c.next();
		}
		
		double meanTrace =  totalTrace/(counter*combLength);
		

		String legend =combLength+"\t"+  la+"\t "+lb+"\t "+lc+"\t"+ meanTrace+"\t"+Math.log(meanTrace);
		System.out.println(legend);

		statics.FileStuff.fileSentence(filename, legend);
	}

	/**
	 * Computes mean(all words of comb. length = length) and the variance.
	 * Computes mean and variance of traces of all words too.
	 * Saves results in filename
	 * @param sw
	 * @param length
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @return
	 */
	public static void meanTraceParamLengths(SurfaceWord sw,int length, double la, double lb, double lc, String filename){
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);


		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[length], sw.length());
		int counter=0;
		double totalLeng=0;
		double totalLengSquare=0;
		double totalTrace=0;
		double totalTraceSquare=0;
		Curve c = new Curve(dcw, sw);
		c.initialize();

		while(c.length()==length){
			//System.out.println(c);
			counter++;
			double leng=c.hyperbolicLength();
			double tr= c.hyperbolicTrace();
			//	System.out.println(leng);
			totalLeng=totalLeng+leng;
			totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			totalTrace=totalTrace+tr;
			totalTraceSquare=totalTraceSquare+Math.pow(tr, 2);
			c = c.next();
		}
		double mean = totalLeng/counter;
		double var = totalLengSquare/counter-mean*mean;
		double meanTrace = totalTrace/counter;
		double varTrace = totalTraceSquare/counter-meanTrace*meanTrace;


		String legend =length+"\t"+  la+" \t"+lb+"\t "+lc+"\t "+mean+"\t"+var+"\t "+meanTrace+"\t"+varTrace;
		System.out.println(legend);

		statics.FileStuff.fileSentence(filename, legend);
	}


	/**
	 * Returns mean(all words of comb. length = length)/(combinatorial length)
	 * @param sw
	 * @param length
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 */
	public  double  meanDividedByCLAnswer(SurfaceWord sw,int length, double la, double lb, double lc, String filename){
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc =parameters[2];
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
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
			//	totalLengSquare=totalLengSquare+Math.pow(leng, 2);
			c = c.next();
		}
		double mean = totalLeng/(counter*length);
		//	double var = totalLengSquare/counter-mean*mean;


		String legend =la+"\t"+lb+"\t"+lc+"\t"+mean;
		System.out.println(legend);

		//	statics.FileStuff.fileSentence(filename, legend);
		return mean;
	}
}

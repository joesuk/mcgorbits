package hypLengthComp;

import java.util.*;

import statics.CreateDirectory;
import statics.FileStuff;


import boundary.Curve;
import boundary.CurveList;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SimpleCurve;
import boundary.SurfaceWord;



/**
 * @author moira
 *
 */
public class Histogram {
	
	
	/**
	 * Creates a tex file with a list of sample geometric lengths of curves of a certain combinatorial length 
	 * @param sampleSize
	 * @param len
	 * @param sw
	 * 
	 */
	public static void findSampleListSepartedTabs(int sampleSize, int len,  SurfaceWord sw){
		String dir ="dataHistogram/";
		statics.CreateDirectory.create(dir);
		String filename1 = dir+"lengthIs"+len+"sampleSize is"+sampleSize+".txt";
		String sentence = "Lengths ";
		statics.FileStuff.fileSentence(filename1, sentence);
		System.out.println(sentence);

	// count = 0;
		
		
		
		ArrayList<Double> list = new ArrayList<Double>();
		System.out.println("Starts here");
		
		for(int h = 1 ; h < sampleSize ; h++){
			
			double geoLen =randomWordVectorLength(len, sw);
			
			list.add(geoLen);
			
			sentence = geoLen+"\t";
			statics.FileStuff.fileSentence(filename1, sentence);

							

		//	count++;
															
			}
		//Collections.sort(list);
	}
	
	/** Computes a histogram of curves of the trace given combinatorial length 
	 * and organizes a histogram with the prescribed number of bins.
	 * @param sampleSize
	 * @param len
	 * @param sw
	 * @param bins
	 */
	public static void makeHistogramPrescribedBinsSAMPLE(int sampleSize, int len,  SurfaceWord sw, int bins){
		String dir ="dataOct2011/";
		statics.CreateDirectory.create(dir);
		String filename1 = dir+"lengthIs"+len+"sampleSize is"+sampleSize+".txt";
		String sentence = "Lengths ";
		statics.FileStuff.fileSentence(filename1, sentence);
		System.out.println(sentence);

		int count = 0;
		double sumGeoLen=0;
		double sumGeoLen2=0;
		
		
		ArrayList<Double> list = new ArrayList<Double>();
		System.out.println("Starts here");
		
		for(int h = 1 ; h < sampleSize ; h++){
			
			double geoLen =randomWordVectorLength(len, sw);
			
			list.add(geoLen);
			
			sentence = geoLen+"\t";
			statics.FileStuff.fileSentence(filename1, sentence);

			int geoLengthFloor = (int) (Math.round(geoLen));					

			//compute Mean and Standard deviation
			sumGeoLen = sumGeoLen + geoLen;
			sumGeoLen2= sumGeoLen2+ Math.pow(geoLen, 2);
			count++;
															
			}
		Collections.sort(list);
		double min = list.get(0);
		double max= list.get(list.size()-1);
		System.out.println(min+" "+max);
		
		int[] binFilled = new int[bins+1];
		
		double binSize =  (max-min)/bins;
		for(int i = 0 ; i < list.size() ; i++){
			binFilled[(int) Math.round((list.get(i)-min)/binSize)]++;
			
		}
		
		statics.FileStuff.fileVector(binFilled, filename1 +bins, "");
	}
	
	public static void computeMeanStandardMinMaxDevSample(int sampleSize, int len,  
			double la, double lb, double lc, String s){
		String dir ="June2012/";
		statics.CreateDirectory.create(dir);
		String filename1 = dir+"sampleSize is"+sampleSize+s+".txt";
		
		

		SurfaceWord sw = new SurfaceWord("aAbB");
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
		
		double sumGeoLen=0;
		double sumGeoLen2=0;
	
		//System.out.println("Starts here");
		
		for(int h = 1 ; h < sampleSize ; h++){
			
			double geoLen =randomWordVectorLength(len, sw);
			if(geoLen < 100000){
			
			//compute Mean and Standard deviation
				if(sumGeoLen2<1000000000){
					sumGeoLen = sumGeoLen + geoLen;
					sumGeoLen2= sumGeoLen2+ Math.pow(geoLen, 2);
				}
			}}
		
		double mean = sumGeoLen/sampleSize;
		double stanDev = sumGeoLen2/sampleSize-mean*mean;
	
		String legend =(la+lb)+"\t"+lc+"\t"+mean+"\t"+ stanDev;
		System.out.println(legend);
		statics.FileStuff.fileSentence(filename1, legend);
	}
	
	
	public static void computeMeanStandardMinMaxDevAll( int len, double la, double lb, double lc, SurfaceWord sw){
		String dir ="DataOct2012_1/";
		statics.CreateDirectory.create(dir);
		String filename = dir+"la"+la+"lb"+lb+"lc"+lc+"len"+len;
		String filenameEst = dir+"stats"+"la"+la+"lb"+lb+"lc"+lc;
		
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
		
		double sumGeoLen=0;
		double sumGeoLen2=0;
		int counter =0;
		System.out.println("Starts here");

		//create the Curves
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[len], sw.length());
		Curve sc = new Curve(pdcw, sw);
		sc = sc.next();
		
		
		double geoLen =sc.hyperbolicLength();
	    String sentence  = "data={"+(geoLen)+",";
		
		statics.FileStuff.fileSentence(filename, "data={"+ geoLen);
		
		while(sc.length()<len+1){
			//System.out.println(sc.toString());
			 geoLen =sc.hyperbolicLength();
				sentence = ","+(geoLen);
				statics.FileStuff.fileSentence(filename, sentence);	
					
				
			//	System.out.println(sentence);
				
				//compute Mean and Standard deviation
					if(sumGeoLen2<Math.pow(10, 15)){
						sumGeoLen = sumGeoLen + geoLen;
						sumGeoLen2= sumGeoLen2+ Math.pow(geoLen, 2);
					}else{
						statics.FileStuff.fileSentence(filenameEst, "out");	
					}
				sc = sc.next();
				counter++;
				
	}
		sentence = "}";
		statics.FileStuff.fileSentence(filename, sentence);		
		double mean = sumGeoLen/counter;
		double variance = sumGeoLen2/counter-mean*mean;
	
		String legend =la+"\t"+lb+"\t"+lc+"\t"+len+"\t"+
			(mean)+"\t"+ (variance)+"\t"+(mean/len)+"\t"+(variance/(len));
		System.out.println(legend);
		statics.FileStuff.fileSentence(filenameEst, legend);
	}
	
	/**
	 * Creates a list of sample lengths.
	 * 
	 * @param sampleSize
	 * @param len
	 * @param sw
	 */
	public static void findSampleLengthSeparatedByComma(int sampleSize, int len,  SurfaceWord sw, String filename){
		filename = filename+"_"+sampleSize+"_"+len;
		System.out.println("Starts here "+ len);
		
		String sentence="data={"+randomWordVectorLength(len,sw)+",";
		statics.FileStuff.fileSentenceNoNewLine(filename, sentence);	
		double geoLen;
	
		
		for(int h = 1 ; h < sampleSize ; h++){	
		    geoLen =randomWordVectorLength(len,sw);
			//sentence =sentence+ geoLen+",";
			statics.FileStuff.fileSentenceNoNewLine(filename, geoLen+", ");									
			}
	
		geoLen =randomWordVectorLength(len,sw);
		statics.FileStuff.fileSentenceNoNewLine(filename, geoLen+"}");	
		System.out.println("End here "+ len);
	}
	
	
		
	
	
	/**
	 * Creates a list of sample lengths.
	 * 
	 * @param sampleSize
	 * @param len
	 * @param sw
	 */
	public static void findSampleLength(int sampleSize, int len,  SurfaceWord sw, String filename){
		
		String sentence;

	
	//	ArrayList<Double> list = new ArrayList<Double>();
		System.out.println("Starts here");
		double geoLen =randomWordVectorLength(len,sw);
	
		
		statics.FileStuff.fileSentence(filename, "data={"+ geoLen);
		for(int h = 1 ; h < sampleSize ; h++){	
		    geoLen =randomWordVectorLength(len,sw);
			sentence = (geoLen)+",";
			statics.FileStuff.fileSentence(filename, sentence);										
			}
	
		geoLen =randomWordVectorLength(len,sw);
		statics.FileStuff.fileSentence(filename,geoLen+"}" );
	}
	
	
	/**
	 * Creates a list of sample lengths.
	 * 
	 * @param sampleSize
	 * @param len
	 * @param sw
	 */
	public static void findSampleSILength(int sampleSize, int len,  SurfaceWord sw){
		String dir ="dataNov2011/";
		statics.CreateDirectory.create(dir);
		String filename1 = dir+"sampleOf"+len+"sampleSize is"+sampleSize+".txt";
		String sentence;

	
		ArrayList<Double> list = new ArrayList<Double>();
		System.out.println("Starts here");
		
		for(int h = 1 ; h < sampleSize ; h++){	
			double geoLen =randomWordVectorLength(len,sw);
			sentence = (geoLen)+"\t";
			statics.FileStuff.fileSentence(filename1, sentence);										
			}
	
	
	}
	
	
	/**
	 * This creates a file of a list of the geometric length of all words, 
	 * by first creating  list and then saving the whole list (as opposed to save it one by one)
	 * @param len
	 * @param sw
	 * @param filename
	 */
	public static void findSortedListOfAllGLengthsCommas(int len,  SurfaceWord sw, String filename, String dir){
		
		statics.CreateDirectory.create(dir);
	//	String  filename1 = filename;	
		filename = dir+"/"+len+filename;
		//System.out.println(filename);
	
		//statics.FileStuff.fileSentenceNoNewLine(filename, sentence);	
		ArrayList<Double> list = new ArrayList<Double>();
		System.out.println("Starts here");
		
		String legend="";
		//create the Curves
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[len], sw.length());
		Curve sc = new Curve(pdcw, sw);
		
		//We can add curves into the CurveList.
		//Let's add all curves with length at most 4
		while(sc.length()<len+1){
			//double geoLen =sc.hyperbolicLength();
			list.add(sc.hyperbolicLength());
			
			sc = sc.next();
		}
		Collections.sort(list);
	
		Iterator itr = list.iterator(); 
		
		Double element = (Double) itr.next();
		String theList = "data={"+element;
		statics.FileStuff.fileSentenceNoNewLine(filename, legend);
		
		
		while(itr.hasNext()) {
			
		     element = (Double) itr.next(); 
		     theList= theList+","+element;
		  //   statics.FileStuff.fileSentenceNoNewLine(filename, legend); 
		    
		//    System.out.print(element + " ");
				

		} 
	
		statics.FileStuff.fileSentence(filename, theList+"}");
		
		
		//double min = list.get(0);
		//double max= list.get(list.size()-1);
		
		//String legend1 =len+"\t"+ min+"\t "+max;
		//statics.FileStuff.fileSentence(filename+"1", legend1);
		System.out.println("end");
		
		
			
		
		
		//statics.FileStuff.fileVector(binFilled, filename1, "");
	}
	
	
	public static void findListAllGeoLengthCommas(int len,  SurfaceWord sw, String filename){
		
		filename = filename+"_"+len;
	//	ArrayList<Double> list = new ArrayList<Double>();
		System.out.println("Starts here");
				//create the Curves
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[len], sw.length());
		Curve sc = new Curve(pdcw, sw);
		sc = sc.next();
		System.out.println("Starts here");
		double geoLen =sc.hyperbolicLength();
	    String sentence  = "data={"+(geoLen)+",";
		
		statics.FileStuff.fileSentence(filename, "data={"+ geoLen);
		
		while(sc.length()<len+1){
			//System.out.println(sc.toString());
			 geoLen =sc.hyperbolicLength();
				sentence = ","+(geoLen);
				statics.FileStuff.fileSentence(filename, sentence);	
			
				sc = sc.next();
		}
		
		sentence = "}";
		statics.FileStuff.fileSentence(filename, sentence);	
	
			
			

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
	
	public static double[] randomWordVectoMatrix(int length, SurfaceWord sw) {

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
		return c.productOfMatrices();

	}
	
	public static Curve randomWord(int length, SurfaceWord sw) {

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
		return c;

	}
	
	/**
	 * Gets a reduced but not necessarily cyclically reduced random vector, and computes the corresponding matrix.
	 * @param length
	 * @param sw
	 * @return
	 */
	public static double[] randomWordVectoMatrixReduButNotCycRedDet0(int length, SurfaceWord sw) {

		int[] v = new int[length];
		Random generator = new Random();
		v[0]=2*generator.nextInt(2);

		for (int h = 1; h <= length-1; h++){
			int aux=2*generator.nextInt(2);
		
					

		}
	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(v , sw.length());
		Curve c = new Curve(dcw, sw);
	//	System.out.println(c.toString());
		return c.productOfMatrices();

	}
	
	//THIS program is for the word aAbB ONLY
	/**WARNING
	 * @param length
	 * @param sw
	 * @return
	 */
	public static double[] randomWordVectoMatrixReduButNotCycRedDetNot0(int length, SurfaceWord sw) {

		int[] v = new int[length];
		Random generator = new Random();
		v[0]=generator.nextInt(sw.length());

		for (int h = 1; h <= length-1; h++){
			int aux=generator.nextInt(sw.length());
		    v[h] = aux;
					

		}
	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(v , sw.length());
		Curve c = new Curve(dcw, sw);
		//System.out.println(c.toString());
		return c.productOfMatrices();

	}
	public static double randomWordVectorLength(int length, SurfaceWord sw) {

		int[] v = new int[length];
		Random generator = new Random();
		v[0]=generator.nextInt(sw.length());

		for (int h = 1; h <= length-1; h++) {
			int aux=generator.nextInt(sw.length()-1);
			if(aux< sw.bar((v[h-1]))){
				v[h] = aux;
			}else v[h]=aux+1;

			

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
	
	
	public static double[] randomWordVectorLengthSI(int length, SurfaceWord sw) {

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
		return new double[] {c.hyperbolicLength(), c.selfIntersection()};

	}

	public static void makeHistogramPrescribedBins(int len,  SurfaceWord sw, int bins){
		String dir ="dataSept2011/";
		statics.CreateDirectory.create(dir);
		String filename1 = dir+"lengthIs"+len+"bins"+bins+"length"+len+".txt";
	
		
		
		ArrayList<Double> list = new ArrayList<Double>();
		System.out.println("Starts here");
		
		
		//create the SimpleCurves
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[len], sw.length());
		Curve sc = new Curve(pdcw, sw);
		
		//We can add curves into the CurveList.
		//Let's add all curves with length at most 4
		while(sc.length()<len+1){
		//	double geoLen =sc.hyperbolicLength();
			list.add(sc.hyperbolicLength());
			sc = sc.next();
		}
		Collections.sort(list);
	
		
		double min = list.get(0);
		double max= list.get(list.size()-1);
		System.out.println(min+" "+max);
		
		int[] binFilled = new int[bins+1];
		
		double binSize =  (max-min)/bins;
		for(int i = 0 ; i < list.size() ; i++){
			binFilled[(int) Math.round((list.get(i)-min)/binSize)]++;
			
		}
		
		statics.FileStuff.fileVector(binFilled, filename1, "");
	}
	

}


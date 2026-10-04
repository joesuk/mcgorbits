package hypLengthComp;

import java.util.ArrayList;
import java.util.Collections;

import boundary.SurfaceWord;

public class RandomWords {
	
	
	public static void listOfLengthsTorus11(int sampleSize, int wordLength, double x, double t,double s){
		
		SurfaceWord sw = new SurfaceWord("abAB");
		
		
		
		double totalLeng=0;
		double totalLengSquare=0;
		
		
		String dir ="torus/";
		statics.CreateDirectory.create(dir);
		String filename=dir+"SampleSize"+sampleSize+"_"+x+"_"+t+"_"+s+"wordLength_"+wordLength;
		
		//here you write the correct parameters.
		double det1 = 1/Math.sqrt(t*x*x-t*x);
		double det2 = 1/Math.sqrt(t*x*x-t*x);
		double[][] mat ={{t*x*det1,x*det1,t*det1,x*det1},{0,x*det2,-s*det2,x*(1+s)*det2}};
		sw.setHyperbolicMatrix(mat);		
		System.out.println("Starts here");
		String sentence = "data = { "; 
		//length a \t length b \t length c \t aa \t bb \t cc\t mean\t var"; 
		//statics.FileStuff.fileSentence(filename, sentence);
		for(int h = 0; h < sampleSize-1 ; h ++){
			double geoLen = Histogram.randomWordVectorLength(wordLength, sw);
			totalLeng=totalLeng+geoLen;
			totalLengSquare=totalLengSquare+Math.pow(geoLen, 2);
		//	System.out.println(geoLen);
		
			sentence = sentence+geoLen+" , ";//"\t";
			

		//	int geoLengthFloor = (int) (Math.round(geoLen));					
															
			}
		double geoLen = Histogram.randomWordVectorLength(wordLength, sw);
		sentence = sentence+geoLen+"}";//"\t";
		statics.FileStuff.fileSentence(filename, sentence);
		//Collections.sort(list);
		
		
		double mean = totalLeng/sampleSize;
		double var = totalLengSquare/sampleSize-mean*mean;
	//	double st = Math.sqrt(var);


		String legend =wordLength+"\t"+ x+"\t"+ s+"\t"+ t+"\t "+(mean/wordLength)+"\t"+(var/wordLength)+"\t";
		System.out.println(legend);

		statics.FileStuff.fileSentence(filename, legend);
		
		System.out.println("The end");		
			
		}

	public static void listOfLengthsPants(int sampleSize, int combLength, double la, double lb,double lc){
		
		SurfaceWord sw = new SurfaceWord("aAbB");
		String dir ="Maskit-Li/";
		statics.CreateDirectory.create(dir);
		String filename=dir+"SampleSize"+sampleSize+"_"+la+"_"+lb+"_"+lc+"wordLength_"+combLength;
		
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
		System.out.println("Starts here");
		String sentence = "data = { "; 
		//length a \t length b \t length c \t aa \t bb \t cc\t mean\t var"; 
		//statics.FileStuff.fileSentence(filename, sentence);
		for(int h = 0; h < sampleSize-1 ; h ++){
			double geoLen = Histogram.randomWordVectorLength(combLength, sw);
		//	System.out.println(geoLen);
		
			sentence = sentence+geoLen+" , ";//"\t";
			

		//	int geoLengthFloor = (int) (Math.round(geoLen));					
															
			}
		double geoLen = Histogram.randomWordVectorLength(combLength, sw);
		sentence = sentence+geoLen+"}";//"\t";
		statics.FileStuff.fileSentence(filename, sentence);
		//Collections.sort(list);
				
				
			
		}
	
	
	public static void listOfLengthsTrace(int sampleSize, int combLength, double la, double lb,double lc){
		
		SurfaceWord sw = new SurfaceWord("aAbB");
		String dir ="Maskit-Li/";
		statics.CreateDirectory.create(dir);
		String filename=dir+"SampleSize"+sampleSize+"_"+la+"_"+lb+"_"+lc;
		
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
		//System.out.println("Starts here");
		double total=0;;
		double totalSq=0;
		String sentence = ""; 
		//length a \t length b \t length c \t aa \t bb \t cc\t mean\t var"; 
		//statics.FileStuff.fileSentence(filename, sentence);
		for(int h = 0; h < sampleSize-1 ; h ++){
			double trace = Histogram.randomWordVectorTrace(combLength, sw);
			total= total+Math.log(Math.abs(trace));
			totalSq= totalSq+Math.log(Math.abs(trace))*Math.log(Math.abs(trace));
		//	System.out.println(geoLen);
		
		//	sentence = sentence+geoLen+" , ";//"\t";
			

		//	int geoLengthFloor = (int) (Math.round(geoLen));					
															
			}
		double mean = total/sampleSize;
		double sd =Math.sqrt( totalSq/sampleSize-mean*mean);
		sentence = combLength+"\t"+(sd/combLength)+"\t"+mean;
		statics.FileStuff.fileSentence(filename, sentence);
		System.out.println(sentence);
		//Collections.sort(list);
				
				
			
		}
	public static void matrixEntries(int sampleSize, int combLength, double la, double lb,double lc){
		
		SurfaceWord sw = new SurfaceWord("aAbB");
		String dir ="Maskit-Li/";
		statics.CreateDirectory.create(dir);
		String filename=dir+"SampleSize"+sampleSize+"_"+la+"_"+lb+"_"+lc;
		
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
    	double aa = parameters[0];
    	double bb = parameters[1];
    	double cc =parameters[2];
   // 	System.out.println(parameters[0]+","+parameters[1]+","+parameters[2]);
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
		{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);	
		//System.out.println("Starts here");
		double[] total = new double[5];
		double[] totalSq = new double[5];
		double[] totalraw = new double[5];
		double[] totalSqraw = new double[5];
		String sentence = ""; 
		//length a \t length b \t length c \t aa \t bb \t cc\t mean\t var"; 
		//statics.FileStuff.fileSentence(filename, sentence);
	//	String title = "la"+"\t"+"lb"+"\t"+"lc"+"\t"+combLength;
		statics.FileStuff.fileSentence(filename+"ALL", sentence);
		statics.FileStuff.fileSentence(filename, sentence);
		
		for(int randomC = 0; randomC < sampleSize-1 ; randomC ++){
			double[] geoLen = Histogram.randomWordVectoMatrix(combLength, sw);
			 double trace =(geoLen[0]+geoLen[3]);
			sentence = geoLen[0]+" \t "+geoLen[1]+"\t"+geoLen[2]+" \t "+geoLen[3]+" \t "+trace;	
			statics.FileStuff.fileSentence(filename, sentence);
			for(int n=0 ; n <4 ; n++){
			statics.FileStuff.fileSentence(filename+n, Math.log(Math.abs(geoLen[n]))+",");
			}
			for(int u =0 ; u <=3 ; u++){
				total[u]= total[u]+Math.log(Math.abs(geoLen[u]));
				totalSq[u]= totalSq[u]+Math.log(Math.abs(geoLen[u]))*Math.log(Math.abs(geoLen[u]));	
				totalraw[u]= totalraw[u]+geoLen[u];
				totalSqraw[u]= totalSqraw[u]+geoLen[u]*geoLen[u];	
			}
			
			
			total[4]= total[4]+Math.log(Math.abs(trace));
			totalSq[4]= totalSq[4]+Math.log(Math.abs(trace))*Math.log(Math.abs(trace));
			totalraw[4]= totalraw[4]+trace;
			totalSqraw[4]= totalSqraw[4]+trace*trace;	
		}
		
		
		
	
		
		//	System.out.println(geoLen);
		
		//	sentence = sentence+geoLen+" , ";//"\t";
			

		//	int geoLengthFloor = (int) (Math.round(geoLen));					
		double mean[]=new double[5];
		double sd[]=new double[5];
		sentence = combLength+"\t";
	
		for(int j =0 ; j <=4 ; j++){	
			 mean[j] = total[j]/sampleSize;
			 sd[j] =Math.sqrt( totalSq[j]/sampleSize-mean[j]*mean[j]);
			sentence =sentence+ (sd[j]/combLength)+"\t";
			
		}
		

		for(int j =0 ; j <=4 ; j++){	
			 mean[j] = total[j]/sampleSize;
			sentence =sentence+ mean[j]+"\t";	
		}
		statics.FileStuff.fileSentence(filename+"ALL", sentence);
	//	System.out.println(sentence);		
				
			
		
		//	int geoLengthFloor = (int) (Math.round(geoLen));					
		double meanraw[]=new double[5];
		double sdraw[]=new double[5];
		sentence = combLength+"\t";
	
		for(int j =0 ; j <=4 ; j++){	
			 meanraw[j] = totalraw[j]/sampleSize;
			 sdraw[j] =Math.sqrt( totalSqraw[j]/sampleSize-meanraw[j]*meanraw[j]);
			//sentence =sentence+ (sdraw[j]/combLength)+"\t";
			
		}
		

		for(int j =0 ; j <=4 ; j++){	
			
			sentence =sentence+ meanraw[j]+"\t";	
		}
		statics.FileStuff.fileSentence(filename+"ALL_RAW", sentence);
		System.out.println(sentence);		
				
			
		}
	public static void sortedListOfLengths(int sampleSize, int combLength, double la, double lb,double lc){
		
		
		SurfaceWord sw = new SurfaceWord("aAbB");
		String dir ="dataOct2012_1/";
		statics.CreateDirectory.create(dir);
		String filename=dir+"SampleSize"+sampleSize+"_"+la+"_"+lb+"_"+lc+"wordLength_"+combLength;
		String sentence = "data = { "; 
		//length a \t length b \t length c \t aa \t bb \t cc\t mean\t var"; 
		statics.FileStuff.fileSentence(filename, sentence);		
		
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
		
		ArrayList<Double> list = new ArrayList<Double>();
		System.out.println("Starts here");
	
		for(int h = 0; h < sampleSize-1 ; h ++){
			double geoLen = Histogram.randomWordVectorLength(combLength, sw);
			
			
			
			sentence = geoLen+" , ";//"\t";
			
			statics.FileStuff.fileSentence(filename, sentence);
		//	int geoLengthFloor = (int) (Math.round(geoLen));					
															
			}
		double geoLen = Histogram.randomWordVectorLength(combLength, sw);	
		sentence = sentence+geoLen+"}";//"\t";
		statics.FileStuff.fileSentence(filename, sentence);
		Collections.sort(list);
				
				
			
		}
	
	
	public static void randomProductCycRedMatrix(int sampleSize, int combLength, double la, double lb,double lc){
		
		SurfaceWord sw = new SurfaceWord("aAbB");
		String dir ="Mar20/";
		statics.CreateDirectory.create(dir);
		String filename=dir+"Matrix11"+sampleSize+"_"+la+"_"+lb+"_"+lc+"CL"+"_"+combLength;
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
		
		
		
		
	
		System.out.println("Starts here");
		String sentence ="x" ;
		//length a \t length b \t length c \t aa \t bb \t cc\t mean\t var"; 
		//statics.FileStuff.fileSentence(filename, sentence);
		for(int h = 0; h < sampleSize-1 ; h ++){
			double[] geoLen = Histogram.randomWordVectoMatrix(combLength, sw);
		
			sentence = geoLen[0]+" \t "+geoLen[1]+"\t"+geoLen[2]+" \t "+geoLen[3];
			System.out.println(sentence);
			statics.FileStuff.fileSentence(filename, sentence);
		
		}
	}
	
	
	public static void randomProductNonNecCycRedMatrix(int sampleSize, int combLength, double la, double lb,double lc){
		
		SurfaceWord sw = new SurfaceWord("aAbB");
		String dir ="Mar20/";
		statics.CreateDirectory.create(dir);
		String filename=dir+"Matrix11"+sampleSize+"_"+la+"_"+lb+"_"+lc+"CL"+"_"+combLength;
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
		
		
	
	//	System.out.println("Starts here");
		String sentence ="x" ;
		//length a \t length b \t length c \t aa \t bb \t cc\t mean\t var"; 
		//statics.FileStuff.fileSentence(filename, sentence);
		for(int h = 0; h < sampleSize-1 ; h ++){
			double[] geoLen = Histogram.randomWordVectoMatrixReduButNotCycRedDetNot0(combLength, sw);
		
			sentence = geoLen[0]+" \t "+geoLen[1]+"\t"+geoLen[2]+" \t "+geoLen[3];
			System.out.println(sentence);
			statics.FileStuff.fileSentence(filename, sentence);
		
		}
	}
	

	    public static void computeLengthsSample(double la, double lb, double lc, int combLen, int sampleSize){
	    	String z = "aAbB";
			SurfaceWord sw = new SurfaceWord(z);
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
			 
			String dir ="NewData10May2012/";
			statics.CreateDirectory.create(dir);
		    String filename =dir+ la+"_"+lb+"_"+lc;
		    Histogram.findSampleLengthSeparatedByComma(sampleSize    , combLen, sw, filename);
	    	
	    }

		/**
		 * This gives prod of matrices,
		 * If boolean is yes admits matrices with det 0 and makes random products of two matrices. 
		 * Otherwise, makes random products of four matrices.
		 * @param b
		 * @param sampleSize
		 * @param combLength
		 * @param la
		 * @param lb
		 * @param lc
		 * @param ld
		 * @param la1
		 * @param lb1
		 * @param lc1
		 * @param ld1
		 */
		public static void prodGivenMatrices(boolean det0,int sampleSize, int combLength, double la, double lb,double lc,double ld,
				double la1, double lb1,double lc1,double ld1){
			
			SurfaceWord sw = new SurfaceWord("aAbB");
			String dir ="prodArbitraryMatrix/";
			statics.CreateDirectory.create(dir);
			String filename=dir+"Matrix"+sampleSize+"CL"+"_"+combLength;
			
			
			double[][] mat = 
			{{la,lb,lc,ld},
					{la1,lb1,lc1,ld1}};
			sw.setHyperbolicMatrix(mat);	
		//	System.out.println(la*ld-lc*lb);
			double[] geoLenTotal = new double[4];
			double[] geoLenTotalSq = new double[4];
		//	System.out.println("Starts here");
			String sentence = ""; 
			//length a \t length b \t length c \t aa \t bb \t cc\t mean\t var"; 
			//statics.FileStuff.fileSentence(filename, sentence);
			for(int h = 0; h < sampleSize ; h ++){
				double[] geoLen = new double[4];
				if(det0 == true){
				geoLen = Histogram.randomWordVectoMatrixReduButNotCycRedDet0(combLength, sw);
				}else { 
						geoLen = Histogram.randomWordVectoMatrixReduButNotCycRedDetNot0(combLength, sw);
						
				}
				sentence = la+" \t "+lb+" \t "+lc+" \t "+ld+" \t "+la1+" \t "+lb1+" \t "+lc1+" \t "+ld1+" \t ";
				for(int u =0 ; u < 4 ; u++){
					geoLenTotal[u]=geoLenTotal[u]+Math.log(Math.abs(geoLen[u]));
					geoLenTotalSq[u]=geoLenTotalSq[u]+Math.log(Math.abs(geoLen[u]))*Math.log(Math.abs(geoLen[u]));
					statics.FileStuff.fileSentence(sentence+u, Math.log(Math.abs(geoLen[u]))+",");
				}
				for (int s =0 ;s<4 ; s++){
					geoLen[s]=Math.log(Math.abs(geoLen[s]));
				}
				sentence = sentence+geoLen[0]+" \t "+geoLen[1]+"\t"+geoLen[2]+" \t "+geoLen[3];
			//	System.out.println(sentence);
				statics.FileStuff.fileSentence(filename, sentence);
				
			}
			String sentence1=sentence;
			double[] mean = new double[4];
			double[] var = new double[4];
			for(int u =0 ; u < 4 ; u++){
				mean[u]=geoLenTotal[u]/sampleSize;
				sentence1=sentence1+mean[u]+"\t";
				var[u]=geoLenTotalSq[u]/sampleSize - mean[u]*mean[u];
			}	
			for(int u =0 ; u < 4 ; u++){
				sentence1=sentence1+Math.sqrt(var[u])+"\t";
				
			}	
			String sentence12 = sentence + "\t"+Math.sqrt(var[0])/combLength;
			//System.out.println(sentence1);
		//	System.out.println(combLength+"\t"+sentence12);
			statics.FileStuff.fileSentence(filename+"ALL", sentence1);
				
		}
		
		
		/**
		 * Computes statistics of with words that are  reduced not nec. cyclically reduces
		 * @param det0
		 * @param sampleSize
		 * @param combLength
		 * @param la
		 * @param lb
		 * @param lc
		 */
		public static void prodGivenMatricesPants(String filename,int sampleSize, int combLength, double la, double lb,double lc){
			
			SurfaceWord sw = new SurfaceWord("aAbB");
			String dir ="Mar20/";
			statics.CreateDirectory.create(dir);
		//	String filename = dir+"data";
			//String filename=dir+"Matrix"+sampleSize+"_"+la+"_"+lb+"_"+lc+"CL"+"_"+combLength;
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
		//	System.out.println(la*ld-lc*lb);
			double[] geoLenTotal = new double[4];
			double[] geoLenTotalSq = new double[4];
		//	System.out.println("Starts here");
		//	
			//length a \t length b \t length c \t aa \t bb \t cc\t mean\t var"; 
			String localFilename=filename+la+"_"+lb+"_"+lc;
			//statics.FileStuff.fileSentence(, sentence);
			for(int h = 0; h < sampleSize ; h ++){
				double[] geoLen = new double[4];
				
				geoLen = Histogram.randomWordVectoMatrixReduButNotCycRedDetNot0(combLength, sw);
						
				
				
				for(int u =0 ; u < 4 ; u++){
					geoLenTotal[u]=geoLenTotal[u]+Math.log(Math.abs(geoLen[u]));
					geoLenTotalSq[u]=geoLenTotalSq[u]+Math.log(Math.abs(geoLen[u]))*Math.log(Math.abs(geoLen[u]));
					statics.FileStuff.fileSentence(localFilename+"_"+combLength+"_"+u, Math.log(Math.abs(geoLen[u]))+",");
				}
				
				String sentence = geoLen[0]+" \t "+geoLen[1]+"\t"+geoLen[2]+" \t "+geoLen[3];
			//	System.out.println(sentence);
				statics.FileStuff.fileSentence(localFilename+"_"+combLength, sentence);
				
			}
		
			String sentence1 =  la+" \t "+lb+" \t "+lc +"\t"+combLength+"\t"; 
			double[] mean = new double[4];
			double[] var = new double[4];
			for(int u =0 ; u < 4 ; u++){
				mean[u]=geoLenTotal[u]/(sampleSize*sampleSize);
				sentence1=sentence1+mean[u]+"\t";
				var[u]=geoLenTotalSq[u]/sampleSize - mean[u]*mean[u];
			}	
			for(int u =0 ; u < 4 ; u++){
				sentence1=sentence1+Math.sqrt(var[u])+"\t";
				
			}	
		//	 sentence1 = sentence1 + "\t"+Math.sqrt(var[4])/combLength;
			//System.out.println(sentence1);
		//	System.out.println(combLength+"\t"+sentence12);
			statics.FileStuff.fileSentence(filename, sentence1);
				
		}

}

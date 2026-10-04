package a_torusPantsHL;

import java.awt.List;
import java.util.ArrayList;

import a_hypLengComp.Histogram;
import boundary.Curve;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;
import boundary.Word;

public class DriverTorus {

	public static void main(String[] args) {
		
		String dir="tori/";
		statics.CreateDirectory.create(dir);
		ArrayList<SurfaceWord> sww = new ArrayList<SurfaceWord>();
		
		sww.add(Parametrizations.torus(7, 8, 0.01));
		sww.add(Parametrizations.torus(7, 8, 0.3));
	//	sww.add(Parametrizations.torus(7, 8, 0.6));
	//	sww.add(Parametrizations.torus(7, 8, 0.8));
	//	sww.add(Parametrizations.torus(7, 8, 1));
		for(int h = 5 ; h < 6 ; h++){
			
			skManyTori(5 ,sww,dir);
			//StudyMetric(7,8,0.4,h);
			
			
		}
		
		
	}
	
	
	public static void StudyMetric(double lambda, double beta, double theta, int maxWL){
		String dir = "Torus"+(2*beta)+"_"+(2*lambda)+"/";

		statics.CreateDirectory.create(dir);
		
		SurfaceWord sw = Parametrizations.torus(lambda, beta,theta);
	
	
		s_k_MaskitShort(sw,maxWL,"mirz",dir );
	



		
	
		
	}
	
	public static void randomMetric(int minWL, int maxWL,int maxMetric,String dir, SurfaceWord sw){
		String label =  "la\t lb \t    labAB \t  total   \t    maxCL    \t  mean    \t    var    \t    boundHypLength" ;
		for(int h= minWL; h<maxWL; h++){
			for(int beta=1 ; beta < maxMetric ; beta++ ){
				for(int lambda= beta+1 ; lambda < maxMetric ; lambda++){
					//					double ll=lambda/4;
					//double lb= beta/4;
					double lb = Math.random()*100;
					double ll= Math.random()*100;
					System.out.println(lb+"\t"+ll);
					if(Parametrizations.checkParametersRectangularTorus(lb,ll)){
						dir = "2Torus"+(2*lb)+"_"+(2*ll)+"/";
						//dir="tr/";
						statics.CreateDirectory.create(dir);
						if(h==minWL){statics.FileStuff.fileSentence(dir+"summary"+(2*lb)+"_"+(2*ll), label);}

						sw = Parametrizations.torusRectangular(lb, ll);
						//s_k_Maskit(sw,"name"+h,h,"TotalCount12",dir );

					}
				}
			}

		}


	}

	public static void mir(SurfaceWord swTorus, String filename, int maxCL,	String filenameAll, String dir) {

		double la = Parametrizations.singleCurveLength("a", swTorus);
		double lb = Parametrizations.singleCurveLength("b", swTorus);
		double labAB = Parametrizations.singleCurveLength("abAB", swTorus);
		double constant1 = Math.min(Math.min(labAB * 0.25, la), lb);
		String metric =  " la " + la
				+ " lb " + lb + " Max wl " + maxCL+"Const " + constant1 + " Bd " + labAB ;
		System.out.println(metric);

		int maxSI = maxCL * maxCL / 4 + maxCL / 2;

		int[][] mirz2 = new int[(int) (constant1 * maxCL)][maxSI];
		int[][] mirz4 = new int[(int) (constant1 * maxCL)][maxSI];
		int[][] mirz8 = new int[(int) (constant1 * maxCL)][maxSI];
		int[][] mirz1 = new int[(int) (5 + constant1 * maxCL)][maxSI];
		int[][] mirz_5 = new int[(int) (2 * constant1 * maxCL)][maxSI];
		int[][] mirz_25 = new int[(int) (4 * constant1 * maxCL)][maxSI];

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
				new int[1], swTorus.length());
		Curve c = new Curve(dcw, swTorus);
		c.initialize();

		double ll = constant1 * maxCL;

		while (c.length() <= maxCL) {
			if (c.hyperbolicLength() <= ll) {
				mirz1[(int) (c.hyperbolicLength())][c.selfIntersection() + 1]++;
				mirz2[(int) (c.hyperbolicLength() * 0.5)][c.selfIntersection() + 1]++;
				mirz4[(int) (c.hyperbolicLength() * 0.25)][c.selfIntersection() + 1]++;
				mirz8[(int) (c.hyperbolicLength() * 0.125)][c
						.selfIntersection() + 1]++;
				mirz_5[(int) (c.hyperbolicLength() * 2)][c.selfIntersection() + 1]++;
				mirz_25[(int) (c.hyperbolicLength() * 4)][c.selfIntersection() + 1]++;

			}
			c = c.next();
		}
		statics.FileStuff.fileMatrix(mirz1, dir + "mirz1" + filename);
		statics.FileStuff.fileMatrix(mirz2, dir + "mirz2" + filename);
		statics.FileStuff.fileMatrix(mirz4, dir + "mirz4" + filename);
		statics.FileStuff.fileMatrix(mirz8, dir + "mirz8" + filename);
		statics.FileStuff.fileMatrix(mirz_5, dir + "mirz5" + filename);
		statics.FileStuff.fileMatrix(mirz_25, dir + "mirz5" + filename);

	}
	public static void s_k_MaskitNew(SurfaceWord swTorus, 
			int maxCL, String filenameAll, String dir,double l1, double l2, double l3) {

		double la = Parametrizations.singleCurveLength("a", swTorus);
		double lb = Parametrizations.singleCurveLength("b", swTorus);
		double labAB = Parametrizations.singleCurveLength("abAB", swTorus);
		double constantTorus = Math.min(2*Math.min(l1, l2),l3);
		String metric = " la " + la+ " lb " + lb + " Max wl " + maxCL+" Const " + constantTorus + " Bd " + labAB ;
		System.out.println(metric);
		//	System.out.println(labAB);
		int maxSI = maxCL * maxCL / 4 + maxCL;
		double[][] histog = new double[maxSI][3];

		double boundHypLength = constantTorus * maxCL;
		
		String ptsFile = dir + "pts" + la + "_" + lb + "_" + maxCL + "_";
		String lengthFile=dir+"lengths"+maxCL;
		
		
		String[] minW = new String[maxSI];

		for (int h = 0; h < maxSI; h++) {
			histog[h][0] = h;
			histog[h][1] = 0;
			histog[h][2] = 1000;
			
		}

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1], swTorus.length());
		Curve ctor=new Curve(dcw, swTorus);
		ctor.initialize();
		int actualMaxSI = 0;


		int[] testSI = new int[100];
		boolean testAll = true;
		boolean testPts=true;
		
		for(int h=1 ; h<100 ; h++){	testSI[h]=0;}
			double cteTorMin=100;
			double cteTorMax=0;
			double cteTorMinq=100;
			double cteTorMaxq=0;
			double araCte=0;
		
			while (ctor.length() <= maxCL) {
				cteTorMin = Math.min(cteTorMin, ctor.hyperbolicLength()/ctor.length());
				cteTorMax = Math.max(cteTorMax, ctor.hyperbolicLength()/ctor.length());
				if (ctor.hyperbolicLength() <= boundHypLength ) {
					cteTorMinq = Math.min(cteTorMinq, ctor.hyperbolicLength()/ctor.length());
					cteTorMaxq = Math.max(cteTorMaxq, ctor.hyperbolicLength()/ctor.length());
					araCte = Math.max(araCte, ctor.selfIntersection()/(ctor.hyperbolicLength()*ctor.hyperbolicLength()));
					int si = ctor.selfIntersection();
					String hlen = String.format("%.3f", ctor.hyperbolicLength());
				
				if(testPts==true){
					testPts=false;
					//statics.FileStuff.fileSentenceNoNewLine(ptsFile, "pts={{"+hlen+","+ctor.selfIntersection()+"}");
				}else{
					//statics.FileStuff.fileSentenceNoNewLine(ptsFile, ",{"+hlen+","+ctor.selfIntersection()+"}");
				}
				
				
				if (testAll == true) {
					//statics.FileStuff.fileSentenceNoNewLine(lengthFile, "al={" + hlen);
					//testAll = false;
				} else {
					statics.FileStuff.fileSentenceNoNewLine(lengthFile, "," + hlen);
				}
				
				
				
				if (testSI[si] == 0) {
					testSI[si] = 1;
					statics.FileStuff.fileSentenceNoNewLine(lengthFile+"_"+si, "al={" + hlen);
				} else if (testSI[si] == 1) {
					statics.FileStuff.fileSentenceNoNewLine(lengthFile+"_"+si, "," + hlen);
				}

				actualMaxSI = Math.max(actualMaxSI, ctor.selfIntersection());
				histog[ctor.selfIntersection()][1]++;
				if (ctor.hyperbolicLength() < histog[ctor.selfIntersection()][2]) {
					histog[ctor.selfIntersection()][2] = ctor.hyperbolicLength();
				}

			}
			ctor = ctor.next();
		}
		
		String sentence = maxCL+"\t"+constantTorus*maxCL+"\t"+araCte;
		System.out.println(sentence);
		statics.FileStuff.fileSentence(dir+"ara", sentence);

		int hh = 0;
		while (testSI[hh] == 1) {
			statics.FileStuff.fileSentenceNoNewLine(lengthFile+"_"+hh, "};");
			hh++;
		}
		statics.FileStuff.fileSentenceNoNewLine(lengthFile, "};");
		statics.FileStuff.fileSentenceNoNewLine(ptsFile, "};");
		

		double m = 0;
		double m2 = 0;
		double total = 0;
		for (int h = 0; h < maxSI; h++) {
			m = m + histog[h][0] * histog[h][1];
			m2 = m2 + histog[h][0] * histog[h][0] * histog[h][1];
			total = total + histog[h][1];

		}

		double mean = m / total;
		double var = m2 / total - mean * mean;
		String legend = la + "\t" + lb + "\t" + labAB + "\t"+(int)total+"\t" + maxCL + "\t"
				+ mean + "\t" + var + "\t" + boundHypLength ;
			//	+ (mean / (boundHypLength * boundHypLength)) + "\t"	+ (var / (boundHypLength * boundHypLength * boundHypLength);
				
				
						//+"\t"+m+"\t"+total+"\t"+cteTorMin+"\t"+cteTorMax+"\t"+cteTorMinq+"\t"+cteTorMaxq);
	System.out.println(sentence);
	//
		//
		statics.FileStuff.fileSentence(dir+"summary", legend);
		for (int i = 0; i < maxSI; i++) {
			String matrixRow = "";
			matrixRow += histog[i][0] + "\t" + histog[i][1] ;
			statics.FileStuff.fileSentence(dir + filenameAll+"XX", matrixRow);
	
		}
		statics.FileStuff.fileSentence(dir+ filenameAll, la + "\t" + lb + "\t"+ String.format("%.3f", labAB) + "\t"+maxCL+"\t"+(int)total+"\t"+String.format("%.3f", constantTorus));
		
	}
	
	/**
	 *Need to complete this program.
	 * @param maxCL
	 * @param sww
	 */
	public static void skManyTori(int maxCL, ArrayList<SurfaceWord> sww, String dir ){
	
		ArrayList<Double> la = new ArrayList<Double>();
		ArrayList<Double> lb = new ArrayList<Double>();
		ArrayList<Double> labAB = new ArrayList<Double>();
		ArrayList<Double> cte = new ArrayList<Double>();
		ArrayList<Double> maxHL = new ArrayList<Double>();
		int maxSI = maxCL * maxCL / 4 ;
		ArrayList<int[][]> histog = new ArrayList<int[][]>();
		//ArrayList[] histog = new ArrayList[300][maxSI];
	
		for(int j = 0 ; j < sww.size() ; j++){
			histog.add(new int[300][maxSI]);
		}
		
		for(int j = 0 ; j < sww.size() ; j++){
			la.add(Parametrizations.singleCurveLength("a", sww.get(j)));
			System.out.println(Parametrizations.singleCurveLength("a", sww.get(j)));
			lb.add(Parametrizations.singleCurveLength("b", sww.get(j)));
			labAB.add(Parametrizations.singleCurveLength("abAB", sww.get(j)));
			cte.add(Math.min(la.get(j), Math.min(lb.get(j),0.25*labAB.get(j))));
			maxHL.add(cte.get(j)*maxCL);
			
			
			
		}
		

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1], sww.get(1).length());
		dcw.initialize();
		
		int actualMaxSI = 0;


		while (dcw.length() <= maxCL) {
		///	System.out.println(dcw.toString());
			for(int j = 0; j < sww.size() ; j++){

			//	Curve ctor=new Curve(dcw, sww.get(j));
				
				Curve ctor = new Curve( new Word(Word.fromString(dcw.toString(),4)),sww.get(j));
			//	ctor.initialize();
		//	System.out.println(dcw.toString()+" "+ctor.hyperbolicLength());
				if (ctor.hyperbolicLength() <= maxHL.get(j) ) {
					(histog.get(j))[(int) Math.floor(ctor.hyperbolicLength())][ctor.selfIntersection()]++;
					//	actualMaxSI = Math.max(actualMaxSI, ctor.selfIntersection());
				}
			}
			dcw = dcw.next();
		}
		

		
		for(int j = 0 ; j < sww.size() ; j++){
			String name= la.get(j)+"_"+lb.get(j)+"_"+labAB.get(j)+"_"+maxCL;
			statics.FileStuff.fileMatrix(histog.get(j), dir+name+"_"+j);
		}
		

	}
	
	
	
	

	public static void s_k_MaskitWord(SurfaceWord swTorus, String filename,
			int maxCL, String filenameAll, String dir) {

		double la = Parametrizations.singleCurveLength("a", swTorus);
		double lb = Parametrizations.singleCurveLength("b", swTorus);
		double labAB = Parametrizations.singleCurveLength("abAB", swTorus);
		double constantTorus = Math.min(Math.min(labAB * 0.25, la), lb);
		String metric = " la " + la+ " lb " + lb + " Max wl " + maxCL+" Const " + constantTorus + " Bd " + labAB ;
		System.out.println(metric);
		//	System.out.println(labAB);
		int maxSI = maxCL * maxCL / 4 + maxCL;
		double[][] histog = new double[maxSI][3];

		String ptsFile = dir + "pts" + la + "_" + lb + "_" + maxCL + "_";
		String lengthFile=dir+"lengths"+maxCL;



		double boundHypLength = constantTorus * maxCL;

		int[][] mirz = new int[1+(int) boundHypLength][maxSI];

		String[] minW = new String[maxSI];

		for (int h = 0; h < maxSI; h++) {
			histog[h][0] = h;
			histog[h][1] = 0;
			histog[h][2] = 1000;

		}

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1], swTorus.length());
		Curve ctor=new Curve(dcw, swTorus);
		ctor.initialize();
		int actualMaxSI = 0;




		double cteTorMin=100;
		double cteTorMax=0;
		double cteTorMinq=100;
		double cteTorMaxq=0;
		double araCte=0;

		while (ctor.length() <= maxCL) {
			cteTorMin = Math.min(cteTorMin, ctor.hyperbolicLength()/ctor.length());
			cteTorMax = Math.max(cteTorMax, ctor.hyperbolicLength()/ctor.length());
			if (ctor.hyperbolicLength() <= boundHypLength ) {

				mirz[(int) Math.floor(ctor.hyperbolicLength())][ctor.selfIntersection()]++;


				cteTorMinq = Math.min(cteTorMinq, ctor.hyperbolicLength()/ctor.length());
				cteTorMaxq = Math.max(cteTorMaxq, ctor.hyperbolicLength()/ctor.length());
				araCte = Math.max(araCte, ctor.selfIntersection()/(ctor.hyperbolicLength()*ctor.hyperbolicLength()));
				//	int si = ctor.selfIntersection();
				//	String hlen = String.format("%.3f", ctor.hyperbolicLength());






				//	statics.FileStuff.fileSentenceNoNewLine(ptsFile+si, "pts={{"+hlen+","+si+","+ctor.toString()+"}");
				//	statics.FileStuff.fileSentence(ptsFile+si, ctor.toString()+"\t"+hlen+"\t"+si);


				actualMaxSI = Math.max(actualMaxSI, ctor.selfIntersection());
				histog[ctor.selfIntersection()][1]++;

			}
			ctor = ctor.next();
		}


	}

	
	
	
	public static void s_k_MaskitShort(SurfaceWord swTorus, 
			int maxCL, String filenameAll, String dir) {

		double la = Parametrizations.singleCurveLength("a", swTorus);
		double lb = Parametrizations.singleCurveLength("b", swTorus);
		double labAB = Parametrizations.singleCurveLength("abAB", swTorus);
		double constantTorus = Math.min(Math.min(labAB * 0.25, la), lb);
		String metric = " la " + la+ " lb " + lb + " Max wl " + maxCL+" Const " + constantTorus + " Bd " + labAB ;
		System.out.println(metric);
		//	System.out.println(labAB);
		int maxSI = maxCL * maxCL / 4 + maxCL;
		double[][] histog = new double[maxSI][3];

		double boundHypLength = constantTorus * maxCL;

		int[][] mirz = new int[1+(int) boundHypLength][maxSI];

		String[] minW = new String[maxSI];

		for (int h = 0; h < maxSI; h++) {
			histog[h][0] = h;
			histog[h][1] = 0;
			histog[h][2] = 1000;

		}

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1], swTorus.length());
		Curve ctor=new Curve(dcw, swTorus);
		ctor.initialize();
		int actualMaxSI = 0;


		while (ctor.length() <= maxCL) {
			
			if (ctor.hyperbolicLength() <= boundHypLength ) {

				mirz[(int) Math.floor(ctor.hyperbolicLength())][ctor.selfIntersection()]++;

				actualMaxSI = Math.max(actualMaxSI, ctor.selfIntersection());
				histog[ctor.selfIntersection()][1]++;

			}
			ctor = ctor.next();
		}
		
		statics.FileStuff.fileMatrix(mirz, dir+filenameAll+maxCL);


	}

	
	public static void correlation(SurfaceWord sw1, SurfaceWord sw2, int sampleSize, String filename1,String filename2,
			 String dir,int wl) {
	
		System.out.println("Start");
		
		
		String name1 = dir + filename1;
		String name2 = dir + filename2;
		
	
		boolean testPts=true;
		int counter =0;
		statics.FileStuff.fileSentenceNoNewLine(name1, "pts={");
		statics.FileStuff.fileSentenceNoNewLine(name2, "pts={");
		while (counter< sampleSize) {
				Curve c=Histogram.randomWord(wl, sw1);
				statics.FileStuff.fileSentenceNoNewLine(name1, "{"+c.hyperbolicLength()+","+c.selfIntersection()+"},");
				
				
				 c=Histogram.randomWord(wl, sw2);
				 statics.FileStuff.fileSentenceNoNewLine(name2, "{"+c.hyperbolicLength()+","+c.selfIntersection()+"},");
					
				counter++;
			
		}
	
		
		
		Curve c=Histogram.randomWord(wl, sw1);
	
		statics.FileStuff.fileSentenceNoNewLine(name1, "{"+c.hyperbolicLength()+","+c.selfIntersection()+"}};");
		 c=Histogram.randomWord(wl, sw2);
		 statics.FileStuff.fileSentenceNoNewLine(name2, "{"+c.hyperbolicLength()+","+c.selfIntersection()+"}};");
	}
	

}

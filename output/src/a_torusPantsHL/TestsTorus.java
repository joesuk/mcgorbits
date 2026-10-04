package a_torusPantsHL;

import java.util.ArrayList;

import boundary.Curve;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;
import boundary.Word;

public class TestsTorus {


	
	public static void manyTori(double[][][] mm, String filename,	int maxWL) {
		String dir="Toriiii/";
	
		statics.CreateDirectory.create(dir);

		SurfaceWord sw = new SurfaceWord("abAB");
	//	double constantTorus = Math.min(l3, 2*Math.min(l1, l2));
		//String metric = " la " + la+ " lb " + lb + " Max wl " + maxWL+" Const " + constantTorus + " Bd " + labAB ;
	//	double boundHypLength = constantTorus * maxWL;
		//System.out.println( metric);

		ArrayList<Double> l1 = new ArrayList<Double>();
		ArrayList<Double> l2 = new ArrayList<Double>();
		ArrayList<Double> l3 = new ArrayList<Double>();
		ArrayList<String> file = new ArrayList<String>();
		ArrayList<Double> maxHL = new ArrayList<Double>();
		ArrayList<SurfaceWord> swt = new ArrayList<SurfaceWord>();
		
		for(int h = 0  ; h <mm.length ; h++){
			double [][] matr={mm[h][1],mm[h][2]};
			SurfaceWord swaux = new SurfaceWord("abAB");
		    swaux.setHyperbolicMatrix(matr);
		    swt.add(sw);
		    l1.add(mm[h][0][0]);
		    l2.add(mm[h][0][1]);
		    l3.add(mm[h][0][2]);
		    file.add(mm[h][0][0]+"_"+mm[h][0][1]+"_"+mm[h][0][2]);
		    System.out.println(mm[h][0][0]+"_"+mm[h][0][1]+"_"+mm[h][0][2]);
		    maxHL.add(maxWL*Math.min(mm[h][0][2], 2*Math.min(mm[h][0][1],mm[h][0][0])));
		        
		}
		
	
		
		
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1], 4);
		Curve ctor=new Curve(dcw, sw);
		ctor.initialize();
		


		while (ctor.length() <=maxWL){
			String str = ctor.toString();
			int si = ctor.selfIntersection();
			Word w1 = new Word(Word.fromString(str,4));

			for(int h = 0  ; h < mm.length ; h++){
				SurfaceWord swh = new SurfaceWord("abAB");
				double [][] matr={mm[h][1],mm[h][2]};
		
				swh.setHyperbolicMatrix(matr);
				Curve ch = new Curve(w1, swh);
				
				// System.out.println(ch.toString()+" "+ch.hyperbolicLength()+"_"+h+" "+maxHL.get(h)+" "+(ch.hyperbolicLength()<maxHL.get(h)));
				if(ch.hyperbolicLength()<maxHL.get(h)){
					statics.FileStuff.fileSentenceNoNewLine(dir+file.get(h)+maxWL+"_si_"+si,String.format("%.4f", ch.hyperbolicLength())+",");
					statics.FileStuff.fileSentenceNoNewLine(dir+file.get(h)+maxWL,String.format("%.4f", ch.hyperbolicLength())+",");
			
					statics.FileStuff.fileSentenceNoNewLine(dir+file.get(h)+maxWL+"PTS","{"+si+","+String.format("%.4f", ch.hyperbolicLength())+"},");
				}

			}




			ctor = ctor.next();
		}


	}

	
	public static void hausDimTorus(SurfaceWord swTorus, String filename,	int maxWL,  double l1, double l2, double l3) {

		double la = Parametrizations.singleCurveLength("a", swTorus);
		double lb = Parametrizations.singleCurveLength("b", swTorus);
		double labAB = Parametrizations.singleCurveLength("abAB", swTorus);
		double constantTorus = Math.min(l3, 2*Math.min(l1, l2));
		String metric = " la " + la+ " lb " + lb + " Max wl " + maxWL+" Const " + constantTorus + " Bd " + labAB ;
		double boundHypLength = constantTorus * maxWL;
		System.out.println( metric);

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1], swTorus.length());
		Curve ctor=new Curve(dcw, swTorus);
		ctor.initialize();
	

		int counter =0;
	
			
			while (ctor.length() <=maxWL){
				if (ctor.hyperbolicLength() <= boundHypLength ) {
					counter++;}
				
				//System.out.println(h+" "+counter+"\t"+ctor.toString()+"\t"+ctor.hyperbolicLength()+"\t"+boundHypLength);
				ctor = ctor.next();
			}
		
			statics.FileStuff.fileSentence(filename,l1+","+l2+","+l3+","+la+","+lb+","+labAB+","+maxWL+","+boundHypLength+","+counter);	
	}
	
	public static void s_k_Maskit(SurfaceWord swTorus, String filename,String filenameAll,String dir,double constantTorus, int maxCL){

		double la = Parametrizations.singleCurveLength("a", swTorus);
		double lb = Parametrizations.singleCurveLength("b", swTorus);
		double labAB = Parametrizations.singleCurveLength("abAB", swTorus);

		String metric = " la " + la+ " lb " + lb + " Max wl " + maxCL+" Const " + constantTorus + " Bd " + labAB ;
		double boundHypLength = constantTorus * maxCL;
		System.out.println( metric);
		System.out.println("The constant is "+constantTorus );
	//	System.out.println(labAB);
		int maxSI = maxCL * maxCL / 4 + maxCL;
		double[][] histog = new double[maxSI][3];
		int[][] mirz = new int[1+(int) boundHypLength][maxSI];
		
		String ptsFile = dir + "pts"+filename  + maxCL + "_";
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
			double araCteMax=0;
			double araCteMin=10000;
		
			while (ctor.length() <= maxCL) 
			{
				cteTorMin = Math.min(cteTorMin, ctor.hyperbolicLength()/ctor.length());
				cteTorMax = Math.max(cteTorMax, ctor.hyperbolicLength()/ctor.length());
			
				
				if (ctor.hyperbolicLength() <= boundHypLength ) {
				mirz[(int) Math.floor(ctor.hyperbolicLength())][ctor.selfIntersection()]++;
					
					cteTorMinq = Math.min(cteTorMinq, ctor.hyperbolicLength()/ctor.length());
					cteTorMaxq = Math.max(cteTorMaxq, ctor.hyperbolicLength()/ctor.length());
					araCteMax = Math.max(araCteMax, ctor.selfIntersection()/(ctor.hyperbolicLength()*ctor.hyperbolicLength()));
					if(ctor.selfIntersection()>0){
						araCteMin = Math.min(araCteMin, ctor.selfIntersection()/(ctor.hyperbolicLength()*ctor.hyperbolicLength()));
					}	
					int si = ctor.selfIntersection();
					String hlen = String.format("%.3f", ctor.hyperbolicLength());
				
				if(testPts==true){
					testPts=false;
					  statics.FileStuff.fileSentenceNoNewLine(ptsFile, "pts={{"+hlen+","+ctor.selfIntersection()+"}");
				}else{
					statics.FileStuff.fileSentenceNoNewLine(ptsFile, ",{"+hlen+","+ctor.selfIntersection()+"}");
				}
				
				
				if (testAll == true) {
					statics.FileStuff.fileSentenceNoNewLine(lengthFile, "al={" + hlen);
					testAll = false;
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

				}//I think this is the right place
			ctor = ctor.next();
		}
		

			
		

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
		String s= "la  \t lb \t labAB  \t  total \t   maxWL \t mean \t var \t maxHL \t (mean/maxHL^2) \t var/(maxHL^3)" +
				"";
	
		String legend = la + "\t" + lb + "\t" + labAB + "\t"+(int)total+"\t" + maxCL + "\t"
				+ mean + "\t" + var + "\t" + boundHypLength +"\t"+
				+ (mean / (boundHypLength * boundHypLength)) + "\t"	+ (var / (boundHypLength * boundHypLength * boundHypLength))
				+ "\t"+ araCteMax +"\t" +araCteMin;
				
				
						//+"\t"+m+"\t"+total+"\t"+cteTorMin+"\t"+cteTorMax+"\t"+cteTorMinq+"\t"+cteTorMaxq);
	System.out.println(legend);
	statics.FileStuff.fileSentence(dir+filenameAll, legend);
		
		for (int i = 0; i < maxSI; i++) {
			String matrixRow = "";
			matrixRow += histog[i][0] + "\t" + histog[i][1] +"\t"+histog[i][2];
			statics.FileStuff.fileSentence(dir + "hist"+filename+maxCL, matrixRow);
	
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





		while (ctor.length() <= maxCL) {
			
			if (ctor.hyperbolicLength() <= boundHypLength ) {

				
				//	int si = ctor.selfIntersection();
				//	String hlen = String.format("%.3f", ctor.hyperbolicLength());

				statics.FileStuff.fileSentence(dir +"allC_"+maxCL,ctor.toString()+"\t"+ctor.hyperbolicLength()+"\t"+ctor.selfIntersection());
				actualMaxSI = Math.max(actualMaxSI, ctor.selfIntersection());
				histog[ctor.selfIntersection()][1]++;
				
				if(ctor.hyperbolicLength()<histog[ctor.selfIntersection()][2]){
					histog[ctor.selfIntersection()][2]=ctor.hyperbolicLength();
					minW[ctor.selfIntersection()]=ctor.toString();
				}
				

			}
			ctor = ctor.next();
		}

		for (int i = 0; i < maxSI; i++) {
			String matrixRow = "";
			matrixRow += histog[i][0] + "\t" + histog[i][1] + "\t" + histog[i][2] +"\t"+minW[i];
			statics.FileStuff.fileSentence(dir +"hist"+String.format("%.3f", boundHypLength), matrixRow);

		}
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

		

				actualMaxSI = Math.max(actualMaxSI, ctor.selfIntersection());
				histog[ctor.selfIntersection()][1]++;
				if(ctor.hyperbolicLength()< histog[ctor.selfIntersection()][2]){
					histog[ctor.selfIntersection()][2]=ctor.hyperbolicLength();
				}

			}
			ctor = ctor.next();
		}
		
	
		statics.FileStuff.fileMatrix(histog, dir+"matrix"+maxCL);


	}
	
	
	
	
	
	


	
	
	
	
	
}

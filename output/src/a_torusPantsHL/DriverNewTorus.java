package a_torusPantsHL;

import java.awt.List;
import java.util.ArrayList;

import a_hypLengComp.Histogram;
import boundary.Curve;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;
import boundary.Word;

public class DriverNewTorus {

	public static void main(String[] args) {
		
		double  l1=1.6;
		double l2=2;
		double l3=2;
		double[][] m=  {{0.132945,0,0,7.52189},{1.80517,2.13322,2.13322,3.07484}};
		
		
		
		int minWL=14;
		int maxWL=22;
		
		String dir="1Torus"+l1+"_"+l2+"_"+l3+"/";
		statics.CreateDirectory.create(dir);
		String s= "la  \t lb \t labAB  \t  total \t   maxWL \t mean \t var \t maxHL \t (mean%maxHL^2) "+
				"\t var%(maxHL^3) \t araCteMax \t araCteMin";
		String filename ="m"+l1+"_"+l2+"_"+l3;
		String filenameAll=filename+"summary";
		statics.FileStuff.fileSentence(dir+filenameAll, s);
		
	
				
				
		double cte= Math.min( l3 , 2*Math.min(l1,l2));
		SurfaceWord sw = Parametrizations.torus(m);
		
		for(int h = minWL ; h <=maxWL ; h++){
			
			s_k_Maskit(sw,filename,filenameAll,h,dir,cte);
			
			
			
		}
		
		
	}
	
	
		
	


	public static void s_k_Maskit(SurfaceWord swTorus, String filename,String filenameAll,
			int maxCL,  String dir,double constantTorus) {

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
		
			while (ctor.length() <= maxCL) {
				cteTorMin = Math.min(cteTorMin, ctor.hyperbolicLength()/ctor.length());
				cteTorMax = Math.max(cteTorMax, ctor.hyperbolicLength()/ctor.length());
				if (ctor.hyperbolicLength() <= boundHypLength ) {
					mirz[(int) Math.floor(ctor.hyperbolicLength())][ctor.selfIntersection()]++;
					
					cteTorMinq = Math.min(cteTorMinq, ctor.hyperbolicLength()/ctor.length());
					cteTorMaxq = Math.max(cteTorMaxq, ctor.hyperbolicLength()/ctor.length());
					araCteMax = Math.max(araCteMax, ctor.selfIntersection()/(ctor.hyperbolicLength()*ctor.hyperbolicLength()));
					araCteMin = Math.min(araCteMin, ctor.selfIntersection()/(ctor.hyperbolicLength()*ctor.hyperbolicLength()));
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

			}
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

	
	
	
	public static void s_k_MaskitNew(SurfaceWord swTorus, 
			int maxCL, String filenameAll, String dir,double constantTorus) {

		double la = Parametrizations.singleCurveLength("a", swTorus);
		double lb = Parametrizations.singleCurveLength("b", swTorus);
		double labAB = Parametrizations.singleCurveLength("abAB", swTorus);

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
				if(ctor.hyperbolicLength()< histog[ctor.selfIntersection()][2]){
					histog[ctor.selfIntersection()][2]=ctor.hyperbolicLength();
				}

			}
			ctor = ctor.next();
		}
		
		statics.FileStuff.fileMatrix(mirz, dir+filenameAll+maxCL);
		statics.FileStuff.fileMatrix(histog, dir+"matrix"+maxCL);


	}

	
	
}

package a_torusPantsHL;

import java.awt.List;
import java.util.ArrayList;

import a_hypLengComp.Histogram;
import boundary.Curve;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;
import boundary.Word;

public class DriverLASTTORUS {

	public static void main(String[] args) {

		SurfaceWord sw = new SurfaceWord("abAB");
		int minWL=10;
		
		for(int wl =minWL ; wl < 21	 ; wl++){
			double[][] mmSmall =  {{0.001,7.600905,0.000956164},{0.0004999997298,0,0,2000.001081},{1.0000005000,0.0010000001667,0.0010000001667,1.0000005000}};
			
			// torusFinish(mmSmall,wl, (wl==minWL));
			
			 
			
			 
			//pantsFinish(0.25,0.25,0.5, wl, (wl==minWL));
			//double[][] mm1= {{7,8,14.25},{0.000335463,0,0,2980.96},{518.014,547.478,547.478,578.62}};
			//(mm1, wl, (wl==minWL) );		
//
//			
//			double[][] mm2= {{7,9,9},{0.0001234087754,0,0,8103.151471},{1.0041489470,33.16136648,33.16136648,1096.128448}};
//			torusFinish(mm2, wl, (wl==minWL) );	
//	
//			double[][] mm3= {{1,2,2},{0.1352005399,0,0,7.396420167},{1.545153639,1.182362957,1.182362957,1.551937685}};
//			torusFinish(mm3, wl, (wl==minWL) );	
		
//			double[][] mm4= {{3,3,5},{0.04976890642,0,0,20.09286665},{7.538115504,9.696098598,9.696098598,12.60452005}};
//			torusFinish(mm4, wl, (wl==minWL) );	
////			
//			double[][] mm5= {{2,3,3},{0.04947464804,0,0,20.21237219},{1.531431782,2.937177356,2.937177356,6.286281200}};
//			torusFinish(mm5, wl, (wl==minWL) );
////			
////			double[][] mm6= {{4,5,5},{0.006729922034,0,0,148.5901315},{1.088048993,7.599643723,7.599643723,53.99994405}};
//			torusFinish(mm6, wl, (wl==minWL) );
		}
//			
			

	}


	public static String pantsFinish(double l1, double l2,double l3, int wl, boolean store){
		// double[][] mm1 = {{3,3,5},{0.04976890642,0,0,20.09286665},{7.538115504,9.696098598,9.696098598,12.60452005}};

		
		 SurfaceWord sw =Parametrizations.pants(l1, l2, l3);

		 String dir="0Pants"+l1+"_"+l2+"_"+l3+"/";
		 
		statics.CreateDirectory.create(dir);
		
		torus(sw, wl, "all",dir ,l1, l2,l3);
		if(store){
			statics.FileStuff.fileSentence(dir+"summary","l1 \t l2 \t l3 \t  total\t maxCL \t mean \t var\t boundHypLength\t(mean / (boundHypLength * boundHypLength)" +
				"\t var / (boundHypLength * boundHypLength * boundHypLength)");
		}
		return dir;
	}
	
	public static void torusFinish(double[][] mm1, int wl, boolean store){
		// double[][] mm1 = {{3,3,5},{0.04976890642,0,0,20.09286665},{7.538115504,9.696098598,9.696098598,12.60452005}};
		 double l1=mm1[0][0];
		 double l2=mm1[0][1];
		 double l3=mm1[0][2];			
		 double [][] m1  = {mm1[1],mm1[2]};
		 SurfaceWord sw = Parametrizations.torus(m1);

		 String dir="1Torus"+l1+"_"+l2+"_"+l3+"/";
		statics.CreateDirectory.create(dir);
		torus(sw, wl, "all",dir ,l1, l2,l3);
		if(store){statics.FileStuff.fileSentence(dir+"summary","l1 \t l2 \t l3 \t  total\t maxCL \t mean \t var\t boundHypLength\t(mean / (boundHypLength * boundHypLength)" +
				"\t var / (boundHypLength * boundHypLength * boundHypLength)");
		}
	}
	public static void torusWords(SurfaceWord swTorus, 
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

		String ptsFile = dir + "pts" + l1 + "_" + l1 + "_" +l3+"_"+ maxCL + "_";
		String ctesFile = dir + "ctes" + l1 + "_" + l2 + "_"+l3+"_" + maxCL + "_";
		String lengthFile=dir+"lengths"+maxCL;




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
		double qiCteMin=100;
		double qiCteMax=0;

		double araCteWLMax=0;
	

		//	double cteTorMinq=100;
		//	double cteTorMaxq=0;
		double araCte=0;
		statics.FileStuff.fileSentence(ctesFile,"h \t qiCteMin \t qiCteMax \t araCteWLMnx \taraCteWLM");

		for(int h=1 ; h <=maxCL ; h++){
			araCteWLMax=0;
		

			qiCteMin=100;
			qiCteMax=0;

			
			while (ctor.length()==h) {

				
				if (ctor.hyperbolicLength() <= boundHypLength ) {
					//	cteTorMinq = Math.min(cteTorMinq, ctor.hyperbolicLength()/ctor.length());
					//	cteTorMaxq = Math.max(cteTorMaxq, ctor.hyperbolicLength()/ctor.length());
					araCte = Math.max(araCte, ctor.selfIntersection()/(ctor.hyperbolicLength()*ctor.hyperbolicLength()));
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
				
				qiCteMin = Math.min(qiCteMin, ctor.hyperbolicLength()*Math.pow(ctor.length(),-1));
				qiCteMax = Math.max(qiCteMax, ctor.hyperbolicLength()/ctor.length());

				araCteWLMax = Math.max(araCteWLMax,
						ctor.selfIntersection()*Math.pow((ctor.hyperbolicLength())*ctor.hyperbolicLength(),-1));
		
			
				ctor = ctor.next();
			}
			
			statics.FileStuff.fileSentence(ctesFile,h+"\t"+ String.format("%.5f",  qiCteMin)+
					"\t"+String.format("%.5f", qiCteMax)+"\t"+
					String.format("%.5f", araCteWLMax));


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
		String legend = l1 + "\t" + l2 + "\t" + l3 + "\t"+(int)total+"\t" + maxCL + "\t"
				+ mean + "\t" + var + "\t" + boundHypLength +"\t"+ (mean / (boundHypLength * boundHypLength))
				+ "\t"	+ (var / (boundHypLength * boundHypLength * boundHypLength));




		//+"\t"+m+"\t"+total+"\t"+cteTorMin+"\t"+cteTorMax+"\t"+cteTorMinq+"\t"+cteTorMaxq);
		System.out.println(sentence);
		//
		//
		statics.FileStuff.fileSentence(dir+"summary", legend);

		for (int i = 0; i < maxSI; i++) {
			String matrixRow = "";
			matrixRow += histog[i][0] + "\t" + histog[i][1] + "\t" + histog[i][2] ;
			statics.FileStuff.fileSentence(dir +"hist"+String.format("%.3f", boundHypLength), matrixRow);

		}

	}

	public static void torus(SurfaceWord swTorus, 
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

		String ptsFile = dir + "pts" + l1 + "_" + l1 + "_" +l3+"_"+ maxCL + "_";
		String ctesFile = dir + "ctes" + l1 + "_" + l2 + "_"+l3+"_" + maxCL + "_";
		String lengthFile=dir+"lengths"+maxCL;




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
		double qiCteMin=100;
		double qiCteMax=0;

		double araCteWLMax=0;
	

		//	double cteTorMinq=100;
		//	double cteTorMaxq=0;
		double araCte=0;
		statics.FileStuff.fileSentence(ctesFile,"h \t qiCteMin \t qiCteMax \t araCteWLMnx \taraCteWLM");

		for(int h=1 ; h <=maxCL ; h++){
			araCteWLMax=0;
		

			qiCteMin=100;
			qiCteMax=0;

			
			while (ctor.length()==h) {

				
				if (ctor.hyperbolicLength() <= boundHypLength ) {
					//	cteTorMinq = Math.min(cteTorMinq, ctor.hyperbolicLength()/ctor.length());
					//	cteTorMaxq = Math.max(cteTorMaxq, ctor.hyperbolicLength()/ctor.length());
					araCte = Math.max(araCte, ctor.selfIntersection()/(ctor.hyperbolicLength()*ctor.hyperbolicLength()));
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
				
				qiCteMin = Math.min(qiCteMin, ctor.hyperbolicLength()*Math.pow(ctor.length(),-1));
				qiCteMax = Math.max(qiCteMax, ctor.hyperbolicLength()/ctor.length());

				araCteWLMax = Math.max(araCteWLMax,
						ctor.selfIntersection()*Math.pow((ctor.hyperbolicLength())*ctor.hyperbolicLength(),-1));
		
			
				ctor = ctor.next();
			}
			
			statics.FileStuff.fileSentence(ctesFile,h+"\t"+ String.format("%.5f",  qiCteMin)+
					"\t"+String.format("%.5f", qiCteMax)+"\t"+
					String.format("%.5f", araCteWLMax));


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
		String legend = l1 + "\t" + l2 + "\t" + l3 + "\t"+(int)total+"\t" + maxCL + "\t"
				+ mean + "\t" + var + "\t" + boundHypLength +"\t"+ (mean / (boundHypLength * boundHypLength))
				+ "\t"	+ (var / (boundHypLength * boundHypLength * boundHypLength));




		//+"\t"+m+"\t"+total+"\t"+cteTorMin+"\t"+cteTorMax+"\t"+cteTorMinq+"\t"+cteTorMaxq);
		System.out.println(sentence);
		//
		//
		statics.FileStuff.fileSentence(dir+"summary", legend);

		for (int i = 0; i < maxSI; i++) {
			String matrixRow = "";
			matrixRow += histog[i][0] + "\t" + histog[i][1] + "\t" + histog[i][2] ;
			statics.FileStuff.fileSentence(dir +"hist"+String.format("%.3f", boundHypLength), matrixRow);

		}
	}













}

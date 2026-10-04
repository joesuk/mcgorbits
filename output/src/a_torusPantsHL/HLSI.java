package a_torusPantsHL;

import java.util.ArrayList;

import a_hypLengComp.SurfaceWordWithMatrices;
import boundary.Curve;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;
import boundary.Word;

public class HLSI {


	/**
	 * Produces a file with the self intersection and length of all geodesic of
	 * hyperbolic length at most maxCL/constant where constant = min{la,lb,
	 * lc/2}.
	 * 
	 * @param sw
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @param maxWordLength
	 */
	public static void correlationPants( double la, double lb,
			double lc, String filename, int maxWordLength) {
		double cte = Math.min(lc / 2, Math.min(la, lb));

		SurfaceWord sw=Parametrizations.pants(la,lb,lc);
	

		String dir = "data2014/";
		statics.CreateDirectory.create(dir);
		filename = dir + filename + maxWordLength + "_" + la + "_" + lb + "_"	+ lc;
		// double constant = Math.min(Math.min(lc / 2, (la + lb) / 2), Math
		// .min(lb, la));
		System.out.println(maxWordLength + " " + la + " " + lb + " " + lc);

		// statics.FileStuff.fileSentence(filename, la + " " + lb + " " + lc +
		// ");

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
				new int[1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();
		String sentence = "data={";
		// String filename = "ADX" + combLength1;
		sentence = sentence + "{" + c.hyperbolicLength() + ","
				+ c.selfIntersection() + "}";
		c = c.next();
		while (c.length() <= maxWordLength) {
			if (c.hyperbolicLength() < maxWordLength / cte) {
				//

				sentence = sentence + ",{" + c.hyperbolicLength() + ","
						+ c.selfIntersection() + "}";

				c = c.next();
			}
		}
		sentence = sentence + "}";

		// System.out.println(cl1+"\t "+p+"\t "+q);
		statics.FileStuff.fileSentence(filename, sentence);

	}


	public static double hypLengthFromTrace(double trace) {
		return 2 * Math.log(0.5 * (Math.abs(trace) + Math.sqrt(trace * trace
				- 4)));

	}

	public static void manyRandomMatrices(String filename, int maxCL,
			String allFilename, int tot, double kte, String dir) {

		ArrayList<SurfaceWordWithMatrices> listPants = new ArrayList<SurfaceWordWithMatrices>();
		double maxHypLength = 0;


		// compute matrices and stuff for each metric.
		for(int h =0 ; h< tot ; h++){
			SurfaceWordWithMatrices pwm = new SurfaceWordWithMatrices(
					Math.random()*10,Math.random()*10,Math.random()*10,Math.random()*10,Math.random()*10,Math.random()*10, maxCL);
			listPants.add(pwm);


		}
		// System.out.println("Metrics number "+counterMetr+" "+na+
		// " "+nb+" "+nc);

		System.out.println("Metrics number " + tot);
		// System.out.println("\nStarting with WL "+" "+maxCL+"\t"+m1[0]+"\t"+m1[1]+"\t"+m1[2]+".");
		System.out.println("Max WL is " + maxCL + ".");

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
				new int[1], 4);
		Curve c = new Curve(dcw, listPants.get(0).getSw());
		c.initialize();

		int actualMaxSI = 0;

		// String filename2="alltheInfo"+maxCL;

		// statics.FileStuff.fileSentence(filename2,"abc");

		while (c.length() <= maxCL) {
			actualMaxSI = Math.max(actualMaxSI, c.selfIntersection());

			// need to study this curve for all metrics
			for (int h = 0; h < tot; h++) {
				Word w0 = new Word(Word.fromString(c.toString(), 4));
				PrimitiveDoublyCyclicWord w2 = new PrimitiveDoublyCyclicWord(
						w0.getWord(), 4);
				// PrimitiveDoublyCyclicWord i2 = w2;
				c = new Curve(w2, listPants.get(h).getSw());

				if (c.hyperbolicLength() <= listPants.get(h).getMaxHL()) {

					double[][] histAux = listPants.get(h).getHist();

					maxHypLength = Math.max(maxHypLength, c.hyperbolicLength());
					histAux[c.selfIntersection()][1]++;
					if (histAux[c.selfIntersection()][2] == -1) {
						histAux[c.selfIntersection()][2] = c.hyperbolicLength();
					} else if (c.hyperbolicLength() < histAux[c
					                                          .selfIntersection()][2]) {
						histAux[c.selfIntersection()][2] = c.hyperbolicLength();
					}
					// System.out.println(c.toString()+" "+c.hyperbolicLength()+" "+"bla");
					listPants.get(h).setHist(histAux);

				}
			}
			c = c.next();

		}

		for (int h = 0; h < listPants.size(); h++) {
			listPants.get(h).fileHist(filename);
			statics.FileStuff.fileSentence(filename + "all", listPants.get(h)
					.giveData());
			statics.FileStuff.fileSentence(dir + "metric"
					+ listPants.get(h).giveLengthBC(), listPants.get(h)
					.giveData());

		}

		System.out.println(maxCL + " done\n\n");

	}


	public static int mirz_Hist_Pants(double la, double lb, double lc, int maxWL, String filename, String dir) {
		
		SurfaceWord sw1 = Parametrizations.pants(la, lb, lc);
		double constant1 = Math.min(Math.min(la, lb), lc*0.5);
		int maxSI = (int) (maxWL * maxWL / 4 + maxWL / 2) + 2;
		double maxHypLength = 0;
		int size =10+(int) (constant1 * maxWL);
		//int[][] mirz = new int[size][maxSI];
		
		String filename1=dir+filename+maxWL+"_";
		System.out.println("aB "+ Parametrizations.singleCurveLength("aB", sw1));
		
		System.out.println("\nStarting with WL " + " " + maxWL + "\t. The maxSI is "+maxSI+". Size is "+size);
		System.out.println("Max WL is " + maxWL + ".");
		
		double[][] hist = new double[maxSI][3];
		int[] wls = new int[maxWL];
		
		for (int h = 0; h < maxSI; h++) {
			hist[h][0] = h;
			hist[h][1] = 0;
			hist[h][2] = -1;
			//hist[h][3] = -1;
			
		}
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1], sw1.length());
		Curve c = new Curve(dcw, sw1);
		c.initialize();

		int actualMaxSI = 0;
		String[] su = new String[maxSI];
		for(int r=0 ; r < maxSI ; r++){
			su[r]="";
			}
		boolean testAll = true;
		
		int[] testSI = new int[100];
		
		double cte=100;
		while (c.length() <= maxWL) {
		//	 su[c.selfIntersection()]=su[c.selfIntersection()]+"\""+c.toString()+"\",";
		//	System.out.println(su[c.selfIntersection()]);
			
			cte  = Math.min(cte, c.hyperbolicLength()/c.length());
			
			if (c.hyperbolicLength() <= constant1*maxWL) {
				
				String hlen = String.format("%.15f", c.hyperbolicLength());
				int si= c.selfIntersection();
				
				
				
				if (testAll == true) {
				//	statics.FileStuff.fileSentenceNoNewLine(filename1, "al={" + hlen);
					statics.FileStuff.fileSentenceNoNewLine(filename1, "pts={{" + hlen+","+si+"}");
					testAll = false;
				} else {
					statics.FileStuff.fileSentenceNoNewLine(filename1 , ",{" + hlen+","+si+"}");
				}
				/*
				String filename2=filename1+"si"+si;
				if (testSI[si] == 0) {
					testSI[si] = 1;
					statics.FileStuff.fileSentenceNoNewLine(filename2, "al={" + hlen);
				} else if (testSI[si] == 1) {
					statics.FileStuff
							.fileSentenceNoNewLine(filename2, "," + hlen);
				}
				
				*/
				wls[c.length()]++;
				
				actualMaxSI = Math.max(actualMaxSI, c.selfIntersection());
				maxHypLength = Math.max(maxHypLength, c.hyperbolicLength());
				hist[c.selfIntersection()][1]++;
		
				if (hist[c.selfIntersection()][2] == -1 ||c.hyperbolicLength()<=hist[c.selfIntersection()][2]) {
					
					hist[c.selfIntersection()][2] = c.hyperbolicLength();
					
				//	statics.FileStuff.fileSentence(dir+filename+"SI"+ c.selfIntersection(), c.toString()+"\t"+c.hyperbolicTrace()+"\t"+Math.log(c.hyperbolicTrace()));
				
				}
			}
			c = c.next();
		}
		
		
		int hh = 0;
		while (testSI[hh] == 1) {
			statics.FileStuff.fileSentenceNoNewLine(filename1+"si"+hh, "};");
			hh++;
		}
		statics.FileStuff.fileSentenceNoNewLine(filename1, "};");
		
		
		
		System.out.println(maxWL + " done\n\n");
		double m = 0;
		double m2 = 0;
		double total = 0;
		for (int h = 0; h <= actualMaxSI; h++) {
			m = m + hist[h][0] * hist[h][1];
			m2 = m2 + hist[h][0] * hist[h][0] * hist[h][1];
			total = total + hist[h][1];
		}


	
		
		boolean test = true;
		boolean test1=false;
		
		for(int r=0; r<actualMaxSI ; r++){
			test=test & ((hist[r][2]<1.3+hist[r+1][2])) ;
			test1 = test1 || hist[r+1][2]==-1;
	
		}
		 
		int answer=-1;
		
		
		
		//statics.FileStuff.fileSentence(dir+"answer", answer[0]+"holds. "+answer[1]+" -2 "+answer[2]+" doesNotHold");
		for(int s = 0 ; s <= actualMaxSI ; s++){
			
		//	System.out.println(s+" "+maxSI+" "+actualMaxSI);
		//	System.out.println(words[s]);
		//	System.out.println(hist[1][s]);
			String oracion = hist[s][0]+"\t"+hist[s][1]+"\t"+hist[s][2];
		//	System.out.println(oracion+"\n\n");
			statics.FileStuff.fileSentence(filename+"UU", oracion);
			if(!test & !test1){ 
				statics.FileStuff.fileSentence(filename+"000", oracion);
			}
			
		}
	
		statics.FileStuff.fileVector(wls,dir+ filename+"word_"+maxWL, "WordLengths of words of geom length²"+constant1*maxWL);
	//	statics.FileStuff.fileVector(inter, filename+"word_"+maxWL, s6);
		
		double mean = m / total;
		double var = m2 / total - mean * mean;
		//	double quo = (actualMaxSI / (hist[actualMaxSI][2] * (hist[actualMaxSI][2])));

		
		
		statics.FileStuff.fileSentence("count", la + "\t" + lb + "\t" + lc + "\t" + ((int) total )+ "\t"+ maxWL+"\t"+constant1);

		return answer;

	}

	public static void pantsCount(double la, double lb, double lc, int maxWL) {
	
		SurfaceWord sw1 = Parametrizations.pants(la, lb, lc);
		double constant1 = Math.min(Math.min(la, lb), lc*0.5);
	
	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1], sw1.length());
		Curve c = new Curve(dcw, sw1);
		c.initialize();

		int counter=0;
		while (c.length() <= maxWL) {
		//	 su[c.selfIntersection()]=su[c.selfIntersection()]+"\""+c.toString()+"\",";
		
			
			if (c.hyperbolicLength() <= constant1*maxWL) {
				
				counter++;
			}
			c = c.next();
		}
		

		System.out.println(la+"\t"+"\t"+lb+"\t"+lc+"\t"+maxWL+"\t"+(constant1*maxWL)+"\t"+counter);
		statics.FileStuff.fileSentence("countPantsSummary", la+"\t"+"\t"+lb+"\t"+lc+"\t"+maxWL+"\t"+(constant1*maxWL)+"\t"+counter);
	}



	public static void mirz_Ara_Pants(double la, double lb, double lc,
				String filename, int maxWL, String dir) {
				int maxSI=maxWL*maxWL/3;
			double[][] msi = new double[maxSI][4];
			SurfaceWord sw1 = Parametrizations.pants(la, lb, lc);
			
			double constant1 = Math.min(Math.min(la, lb), lc*0.5);
	//	 
			System.out.println("Max WL is " + maxWL + ".");
	
			PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
					new int[1], sw1.length());
			Curve c = new Curve(dcw, sw1);
			c.initialize();
		//	String name1=dir+"pts"+maxWL;
			double araCteMin =10000;
			double araCteMax=0;
			boolean first =true;
			double quasiIsomCteDown=100;
			double quasiIsomCteUp=0;
			int total =0;
			String fn =dir+"allRatio"+maxWL;
			statics.FileStuff.fileSentenceNoNewLine(fn,"ration={" );
			
			for(int h =0 ; h < maxSI ; h++){
				msi[h][0]=h;
				
				msi[h][1]=100000;
				msi[h][3]=100000;
			}
			while (c.length() <= maxWL) {
				
					
				
				if (c.hyperbolicLength() <= constant1*maxWL) {
					//System.out.println(c.selfIntersection()+" "+c.hyperbolicLength());
					quasiIsomCteDown  = Math.min(quasiIsomCteDown, c.hyperbolicLength()/c.length());
					quasiIsomCteUp  = Math.max(quasiIsomCteUp, c.hyperbolicLength()/c.length());
					total++;
					double ratio =c.selfIntersection()/(c.hyperbolicLength()*c.hyperbolicLength());
					araCteMax= Math.max(araCteMax, c.selfIntersection()/(c.hyperbolicLength()*c.hyperbolicLength()));
					araCteMin= Math.min(araCteMin, c.selfIntersection()/(c.hyperbolicLength()*c.hyperbolicLength()));
					String hlen = String.format("%.6f",(c.selfIntersection()/(c.hyperbolicLength()*c.hyperbolicLength()))  );
					msi[c.selfIntersection()][1]=Math.min(msi[c.selfIntersection()][1], ratio);
					msi[c.selfIntersection()][2]=Math.max(msi[c.selfIntersection()][2], ratio);
					msi[c.selfIntersection()][3]=Math.min(msi[c.selfIntersection()][3], c.hyperbolicLength());
					if(first){
						statics.FileStuff.fileSentenceNoNewLine(fn,hlen );
						first=false;
					}else{
						statics.FileStuff.fileSentenceNoNewLine(fn,","+hlen);
					}
				}
				
				c = c.next();
			}
		statics.FileStuff.fileSentenceNoNewLine(fn,"};" );
		String legend = maxWL+"\t"+constant1*maxWL+"\t"+total+"\t"+araCteMin+"\t"+araCteMax+"\t"+quasiIsomCteDown+"\t"+quasiIsomCteUp;
		System.out.println(legend);
		statics.FileStuff.fileSentence(filename, legend);
		statics.FileStuff.fileMatrix(msi, fn+"1");
	

		}
	
	public static void mirz_Ara_Torus(double lam, double bet, 
			String filename, int maxWL, String dir) {
			int maxSI=maxWL*maxWL/3;
		double[][] msi = new double[maxSI][4];
		SurfaceWord sw1 = Parametrizations.torusRectangular(lam, bet);
		//CHOOSING BD
		
		double la = Parametrizations.singleCurveLength("a", sw1);
		double lb = Parametrizations.singleCurveLength("b", sw1);
		double labAB = Parametrizations.singleCurveLength("abAB", sw1);
		double constantTorus = Math.min(Math.min(labAB * 0.25, la), lb);
//	 
		System.out.println("Max WL is " + maxWL + ".");
		System.out.println("The length of aB is "+Parametrizations.singleCurveLength("abaB", sw1)+" ab "+Parametrizations.singleCurveLength("ab", sw1));
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
				new int[1], sw1.length());
		Curve c = new Curve(dcw, sw1);
		c.initialize();
	//	String name1=dir+"pts"+maxWL;
		double araCteMin =10000;
		double araCteMax=0;
		boolean first =true;
		double quasiIsomCteDown=100;
		double quasiIsomCteUp=0;
		int total =0;
		String fn =dir+"allRatio"+maxWL;
		statics.FileStuff.fileSentenceNoNewLine(fn,"ration={" );
		
		for(int h =0 ; h < maxSI ; h++){
			msi[h][0]=h;
			
			msi[h][1]=100000;
			msi[h][3]=100000;
		}
		while (c.length() <= maxWL) {
			
				
			
			if (c.hyperbolicLength() <= constantTorus*maxWL) {
				//System.out.println(c.selfIntersection()+" "+c.hyperbolicLength());
				quasiIsomCteDown  = Math.min(quasiIsomCteDown, c.hyperbolicLength()/c.length());
				quasiIsomCteUp  = Math.max(quasiIsomCteUp, c.hyperbolicLength()/c.length());
				total++;
				double ratio =c.selfIntersection()/(c.hyperbolicLength()*c.hyperbolicLength());
				araCteMax= Math.max(araCteMax, ratio);
				araCteMin= Math.min(araCteMin, c.selfIntersection()/(c.hyperbolicLength()*c.hyperbolicLength()));
				String hlen = String.format("%.6f",(c.selfIntersection()/(c.hyperbolicLength()*c.hyperbolicLength()))  );
				msi[c.selfIntersection()][1]=Math.min(msi[c.selfIntersection()][1], ratio);
				msi[c.selfIntersection()][2]=Math.max(msi[c.selfIntersection()][2], ratio);
				msi[c.selfIntersection()][3]=Math.min(msi[c.selfIntersection()][3], c.hyperbolicLength());
				if(first){
					statics.FileStuff.fileSentenceNoNewLine(fn,hlen );
					first=false;
				}else{
					statics.FileStuff.fileSentenceNoNewLine(fn,","+hlen);
				}
			}
			
			c = c.next();
		}
	statics.FileStuff.fileSentenceNoNewLine(fn,"};" );
	String legend = maxWL+"\t"+constantTorus*maxWL+"\t"+total+"\t"+araCteMin+"\t"+araCteMax+"\t"+quasiIsomCteDown+"\t"+quasiIsomCteUp;
	System.out.println(legend);
	statics.FileStuff.fileSentence(filename, legend);
	statics.FileStuff.fileMatrix(msi, fn+"SI");


	}

	public static int mirz_Hist_PantsWords(double la, double lb, double lc, String filename, int maxWL, String allFilename, String dir) {
	
		SurfaceWord sw1 = Parametrizations.pants(la, lb, lc);
		double constant1 = Math.min(Math.min(la, lb), lc*0.5);
		int maxSI = (int) (maxWL * maxWL / 4 + maxWL / 2) + 2;
		double maxHypLength = 0;
	//	int size =10+(int) (constant1 * maxWL)/denominator;

		
		System.out.println("\nStarting with WL " + " " + maxWL + "\t. The maxSI is "+maxSI);
		System.out.println("Max WL is " + maxWL + ".");
	
		double[][] hist = new double[maxSI][4];
		String[] words = new String[maxSI+1];
		int[] wls = new int[maxWL+5];
	//	int[] inter = new int[10*maxWL];
		
		
		for (int h = 0; h < maxSI; h++) {
			hist[h][0] = h;
			hist[h][1] = 0;
			hist[h][2] = -1;
			hist[h][3] = -1;
			//words[h]="";
		}
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
				new int[1], sw1.length());
		Curve c = new Curve(dcw, sw1);
		c.initialize();
	
		int actualMaxSI = 0;
		String[] su = new String[maxSI];
		for(int r=0 ; r < maxSI ; r++){
			su[r]="";
			}
		double cte=100;
		while (c.length() <= maxWL) {
			if(c.selfIntersection() <= (maxWL-1)*0.5){
			String sent =c.toString()+"\t"+c.hyperbolicLength()+"\t"+c.selfIntersection();
			
			System.out.println(sent);
			statics.FileStuff.fileSentence(dir+"allCurves"+la+"_"+lb+"_"+lc+maxWL, sent);
		//	 su[c.selfIntersection()]=su[c.selfIntersection()]+"\""+c.toString()+"\",";
		//	System.out.println(su[c.selfIntersection()]);
			
				cte  = Math.min(cte, c.hyperbolicLength()/c.length());
			
			if (c.hyperbolicLength() <= constant1*maxWL) {
				
			//	String hlen = String.format("%.15f", c.hyperbolicLength());
			//	int si= c.selfIntersection();
				
				
				wls[c.length()]++;
			
				//	System.out.println(c.selfIntersection()+"\t"+ c.hyperbolicLength()+"\t"+(int) c.hyperbolicLength()+"\t"+c.toString());
				actualMaxSI = Math.max(actualMaxSI, c.selfIntersection());
				maxHypLength = Math.max(maxHypLength, c.hyperbolicLength());
				hist[c.selfIntersection()][1]++;
			//	if (hist[c.selfIntersection()][2] == -1 || c.hyperbolicLength() < hist[c.selfIntersection()][2]) {
				if (hist[c.selfIntersection()][3] == -1 ||c.hyperbolicLength()<=hist[c.selfIntersection()][3]) {
					
					hist[c.selfIntersection()][3] = c.hyperbolicLength();
					words[c.selfIntersection()]=c.toString();
					
				//	statics.FileStuff.fileSentence(dir+filename+"SI"+ c.selfIntersection(), c.toString()+"\t"+c.hyperbolicTrace()+"\t"+Math.log(c.hyperbolicTrace()));
				
				}}
			}
			c = c.next();
		}
	
		System.out.println(maxWL + " done\n\n");
		double m = 0;
		double m2 = 0;
		double total = 0;
		for (int h = 0; h <= actualMaxSI; h++) {
			m = m + hist[h][0] * hist[h][1];
			m2 = m2 + hist[h][0] * hist[h][0] * hist[h][1];
			total = total + hist[h][1];
		}
	
		statics.FileStuff.fileSentence(dir+"constant",maxWL+"\t"+cte);
		filename = dir + filename + "wl_" + maxWL + "_la" + la + "_lb"
				+ lb + "_lc" + lc;
		 String sentence = "Checked all geodesics up to hyp length "
				+ (maxWL * constant1) + ". " + "Actual Max hyp length is "
				+ maxHypLength + ". Max word length is " + maxWL
				+ " Total number of geod: " + total;
		//statics.FileStuff.fileSentence(filename, sentence);
		
		boolean test = true;
		boolean test1=false;
		
		for(int r=0; r<actualMaxSI ; r++){
			test=test & ((hist[r][3]<1.3+hist[r+1][3])) ;
			test1 = test1 || hist[r+1][3]==-1;
	
		}
		 
		
		
		
		//statics.FileStuff.fileSentence(dir+"answer", answer[0]+"holds. "+answer[1]+" -2 "+answer[2]+" doesNotHold");
		for(int s = 0 ; s <= actualMaxSI ; s++){
			
		//	System.out.println(s+" "+maxSI+" "+actualMaxSI);
		//	System.out.println(words[s]);
		//	System.out.println(hist[1][s]);
		//	String oracion = hist[s][0]+"\t"+hist[s][1]+"\t"+hist[s][2]+"\t"+hist[s][3]+"\t"+words[s];
		//	System.out.println(oracion+"\n\n");
		//	statics.FileStuff.fileSentence(filename+"UU", oracion);
			if(true){ 
				//statics.FileStuff.fileSentence(filename+"000", oracion);
			}//
			
		}
	//	statics.FileStuff.fileMatrix(hist, filename);
	
	//	statics.FileStuff.fileVector(wls, filename+"word_"+maxWL, "WordLengths of words of geom length²"+constant1*maxWL);
	//	statics.FileStuff.fileVector(inter, filename+"word_"+maxWL, s6);
		
		
	//	statics.FileStuff.fileSentence(dir+"count", la + "\t" + lb + "\t" + lc + "\t" + total + "\t"+ maxWL+"\t"+constant1);
	//	statics.FileStuff.fileSentence(dir + allFilename, sentence);
;
		return 1;
	
	}


	public static void pantsHistWLGL(double la, double lb, double lc, int maxWL, String filename, String dir) {
	
		SurfaceWord sw1 = Parametrizations.pants(la, lb, lc);
		
		double constant1 = Math.min(Math.min(la, lb), lc*0.5);
		String filename1=dir+filename+maxWL+"_";
		
		System.out.println("\nStarting with WL " + " " + maxWL+"\t"+constant1);
		System.out.println("Max WL is " + maxWL + ".");
		
		
		
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[maxWL], sw1.length());
		Curve c = new Curve(dcw, sw1);
		c.initialize();
	
		double cte=100;
		double cteM=0;
		boolean testAll = true;
		
		
	
		while (c.length() <= maxWL) {
			cte  = Math.min(cte, c.hyperbolicLength()/c.length());
			cteM  = Math.max(cteM, c.hyperbolicLength()/c.length());
			
				String hlen = String.format("%.15f", c.hyperbolicLength());
				int si= c.selfIntersection();
				
				
				
				if (testAll == true) {
					statics.FileStuff.fileSentenceNoNewLine(filename1, "al={" + hlen);
				
					testAll = false;
				} else {
					statics.FileStuff.fileSentenceNoNewLine(filename1 , "," + hlen);
				}
				
			
			c = c.next();
		}
		
		
		
		statics.FileStuff.fileSentenceNoNewLine(filename1, "};");
		
		statics.FileStuff.fileSentence(filename+"cte", String.format("%.15f",cte));
		
		
		System.out.println(maxWL + " done\n\n");
		
	
	
		
	
	}



}

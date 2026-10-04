package a_hypLengComp;

import java.util.ArrayList;

import boundary.Curve;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;

public class Shortcuts {
	
	public static void allMeanSDMaxMin(int len, double la, double lb, double lc){
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		
		
		
		double[] parameters = LengthSamples.computeParameters(la,lb,lc);
		
		 double aa = parameters[0];
    	 double bb = parameters[1];
    	 double cc =parameters[2];
		 double d = 1/(cc-bb);
		 double e =1/(1-aa);
		 double[][] mat =	{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);
		
		//Histogram.findSortedListOfAllGLengthsCommas(6, sw, "list"+la+"_"+lb+"_"+lc);
		Histogram.computeMeanStandardMinMaxDevAll(len, la, lb, lc,  sw);
	}
	public static void getabc(double la, double lb, double lc){
	
		
		double[] par = a_hypLengComp.LengthSamples.computeParameters(la, lb,lc);

		String sentence =la + "\t " + lb + " \t" + lc + "\t"+par[0]+"\t"+par[1]+"\t"+par[2];
		statics.FileStuff.fileSentence("test231111.txt" ,sentence);
		System.out.println(sentence);
		
		
		
	}
	

	
	public static void traceMatrices(int sampleSize, int combLength, double la, double lb,double lc,double ld,
			double la1, double lb1,double lc1,double ld1){
		
		SurfaceWord sw = new SurfaceWord("aAbB");
		String dir ="Maskit-Li-Mar12/";
		statics.CreateDirectory.create(dir);
	
		String filename=dir+"MatrixTRACE"+sampleSize+"_"+la+"_"+lb+"_"+lc+"_"+ld+"_"+la1+"_"+lb1+"_"+lc1+"_"+ld1+"_"
		+"CL"+"_"+combLength;
		System.out.println("trace1 ="+(la+ld));
		System.out.println("trace2= "+(la1+ld1));
		//statics.FileStuff.fileSentence(filename, "trace1= "+(la+ld));
		//statics.FileStuff.fileSentence(filename, "trace2 ="+(la1+ld1));
		double[][] mat = 
		{{la,lb,lc,ld},
				{la1,lb1,lc1,ld1}};
		sw.setHyperbolicMatrix(mat);	
		
	
		System.out.println("Starts here");
		String sentence = "data = { "; 
		//length a \t length b \t length c \t aa \t bb \t cc\t mean\t var"; 
		//statics.FileStuff.fileSentence(filename, sentence);
		for(int h = 0; h < sampleSize-1 ; h ++){//this gives TRACE
			double geoLen = Math.log(Histogram.randomWordVectorTrace(combLength, sw));
			
			///if(geoLen != Double.NaN){
			//list.add(geoLen);
				//System.out.println(geoLen);
			sentence = sentence+geoLen+" , ";//"\t";
		//	}else System.out.println(geoLen);

		//	int geoLengthFloor = (int) (Math.round(geoLen));					
															
			}
		double geoLen = Math.log(Histogram.randomWordVectorTrace(combLength, sw));
		
		
		
		sentence = sentence+geoLen+"}";//"\t";
		statics.FileStuff.fileSentence(filename, sentence);
		//Collections.sort(list);
				
				
			
	}
	
	public static double[] mean1( double la, double lb, double lc, double hypLength) {
		
		SurfaceWord sw = new SurfaceWord("aAbB");
		
		double[] parameters = a_hypLengComp.LengthSamples.computeParameters(la, lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc = parameters[2];
		double d = 1 / (cc - bb);
		double e = 1 / (1 - aa);
	//	String sentence =la + "\t " + lb + " \t" + lc + "\t"+aa+"\t"+bb+"\t"+cc;
	//	statics.FileStuff.fileSentence("test.txt" ,sentence);
	//	System.out.println(la + "\t " + lb + " \t" + lc + "\t"+aa+"\t"+bb+"\t"+cc);
		double[][] mat = { { (1 + aa) * e, 2 * aa * e, 2 * e, (1 + aa) * e },
				{ (bb + cc) * d, -2 * bb * cc * d, -2 * d, (bb + cc) * d } };

		sw.setHyperbolicMatrix(mat);
		double constant =  Math.min(lc / 2, Math.min(lb, la));
	//	System.out.println(la + " " + lb + " " + lc + " " + "constant "		+ constant);
		
		//statics.FileStuff.fileSentence(filename, la + " " + lb + " " + lc + " "
		int cl1 = 1;
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();
		
		double hl = 0;
		int counter = 0;
		while (c.length()*constant <= hypLength) {
			if (c.hyperbolicLength() < hypLength) {
				hl=hl+c.hyperbolicLength();
				counter++;
			//	System.out.println(hl+" "+counter);
			}//else System.out.println(c.toString()+" "+c.hyperbolicLength()+" "+constant);
			c = c.next();
		//	System.out.println(hl+" "+counter+" "+c.toString());
		}
		//System.out.println(la+"\t"+ lb+"\t"+ lc+ "\t"+counter+" "+ hl/counter+" "+hl);
		return  new double[] {counter, hl/counter,constant};
	}
	
	public static void hlengthSI1(SurfaceWord sw, double la, double lb, double lc, String filename, double hypLength) {
		filename = filename+hypLength;
		double constant =  Math.min(lc / 2, Math.min(lb, la));
		System.out.println(hypLength+" "+la + " " + lb + " " + lc + " " + "constant "
				+ constant);
		
		statics.FileStuff.fileSentence(filename, la + " " + lb + " " + lc + " "
				+ "constant " + constant);
		int cl1 = 1;

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[cl1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();

		// String filename = "ADX" + combLength1;
		while (c.length()*constant <= hypLength) {
			//
			// System.out.println("XX"+sentence);
		//	statics.FileStuff.fileSentence(filename + cl1,sentence);
			if (c.hyperbolicLength() < hypLength) {
				String sentence=c.toString()+" \t"+c.length()+"\t" +c.hyperbolicLength()+ "\t" + c.selfIntersection();
				// System.out.println(sentence);
				statics.FileStuff.fileSentence(filename ,sentence);

			}

			c = c.next();
		}
		// System.out.println(cl1+"\t "+p+"\t "+q);
		// statics.FileStuff.fileSentence(filename,cl1+"\t "+p+"\t "+q);

	}
	
	

	/**
	 * Produces a file with the self intersection and length of all geodesic of length at most maxCL/constant.
	 * @param sw
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @param maxCL
	 */
	public static void distrib1(SurfaceWord sw, double la, double lb, double lc,
			String filename, int maxCL) {
		filename = filename+maxCL;
		double constant =  Math.min(Math.min(lc / 2, (la + lb) / 2), Math
				.min(lb, la));
		System.out.println(maxCL+" "+la + " " + lb + " " + lc + " " + "constant "
				+ constant);

		statics.FileStuff.fileSentence(filename, la + " " + lb + " " + lc + " "
				+ "constant " + constant);
	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
				new int[1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();

		// String filename = "ADX" + combLength1;
		while (c.length()*constant <= maxCL) {
			//
			// System.out.println("XX"+sentence);
		//	statics.FileStuff.fileSentence(filename + cl1,sentence);
		//	String sentence= c.selfIntersection()+"";
				String sentence=c.toString()+" \t"+c.length()+"\t" +c.hyperbolicLength()+ "\t" + c.selfIntersection();
				// System.out.println(sentence);
				statics.FileStuff.fileSentence(filename ,sentence);

			

			c = c.next();
		}
		// System.out.println(cl1+"\t "+p+"\t "+q);
		// statics.FileStuff.fileSentence(filename,cl1+"\t "+p+"\t "+q);

	}
	
	/**
	 *
	 *Computes the list of self-intersections of all geodesics up to certain length.
	 * @param sw
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @param maxCL
	 */
	public static void hypLengthOnlySI1(SurfaceWord sw, double la, double lb, double lc,String filename, int maxCL) {
		filename = "N"+filename+maxCL+"_"+la+"_"+lb+"_"+lc+"_"+maxCL;
		double constant = Math.min(lc / 2, Math.min(lb, la));
		String s="maxCL: "+maxCL+" "+la + " " + lb + " " + lc + " " + "constant "	+ constant+"\t max hyp Length: "+constant*maxCL;
		System.out.println(s);
		int[] si= new int[maxCL*maxCL/4+maxCL/2];
		//statics.FileStuff.fileSentence(filename, s);
		

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
				new int[1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();

		 ArrayList<Integer> list = new ArrayList<Integer>();
		// String filename = "ADX" + combLength1;
		while (c.length() <= maxCL) {
			//
			if(c.hyperbolicLength()<=constant*maxCL){
				si[c.selfIntersection()]++;
				list.add(c.selfIntersection());
			}

			c = c.next();
		}
		// System.out.println(cl1+"\t "+p+"\t "+q);
		 statics.FileStuff.fileVector(si, filename, "si up to length "+maxCL);
		 statics.FileStuff.fileSentence(filename, s);
		 String tab="";
		 for(int h =0 ; h < list.size() ; h++){
			tab=tab+list.get(h)+"\n";
			// statics.FileStuff.fileVector(si, filename+"TT", list.get(h)+""); 
		 }
		// statics.FileStuff.fileVector(si, filename+"T", tab);
	}

	public static void constantWord1(SurfaceWord sw, double la, double lb,
			double lc, String filename, int maxCL) {
		double constant = 2 / Math.min(lc, Math.min(lb, la));
		System.out.println(la + " " + lb + " " + lc + " " + "constant "
				+ constant);

		statics.FileStuff.fileSentence(filename, la + " " + lb + " " + lc + " "
				+ "constant " + constant);
		for (int cl1 = 3; cl1 < maxCL; cl1++) {
			PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
					new int[cl1], sw.length());
			Curve c = new Curve(dcw, sw);
			c.initialize();
			double q = 1;
			double p = 1;
			// String filename = "Constant1" ;

			// String filename = "ADX" + combLength1;
			while (c.length() == cl1) {
				if (p < c.hyperbolicLength() / c.length()) {
					p = c.hyperbolicLength() / c.length();
					String sentence = "p " + cl1 + "\t " + p + "\t "
							+ c.toString();
					System.out.println(sentence);
					statics.FileStuff
							.fileSentence(filename + "curve", sentence);
				}
				if (q < c.length() / c.hyperbolicLength()) {
					q = c.length() / c.hyperbolicLength();
					String sentence = "q " + cl1 + "\t " + q + "\t "
							+ c.toString();
					System.out.println(sentence);
					statics.FileStuff
							.fileSentence(filename + "curve", sentence);
				}

				// System.out.println(c.length() + " " + c.toString() + " " +
				// c.hyperbolicLength());
				/*
				 * if (c.length() < constant*combLength1 ) { //
				 * System.out.println(c.toString()+" " +c.hyperbolicLength()+
				 * "\t" + c.selfIntersection());
				 * statics.FileStuff.fileSentence(filename,
				 * c.hyperbolicLength()+ "\t" + c.selfIntersection());
				 * 
				 * }
				 */
				c = c.next();
			}
			System.out.println(cl1 + "\t " + p + "\t " + q);
			statics.FileStuff.fileSentence(filename, cl1 + "\t " + p + "\t "
					+ q);
		}
	}
	
	public static double[] mean( double la, double lb, double lc, double hypLength) {
		
		SurfaceWord sw = new SurfaceWord("aAbB");
		
		double[] parameters = a_hypLengComp.LengthSamples.computeParameters(la, lb,lc);
		double aa = parameters[0];
		double bb = parameters[1];
		double cc = parameters[2];
		double d = 1 / (cc - bb);
		double e = 1 / (1 - aa);
	//	String sentence =la + "\t " + lb + " \t" + lc + "\t"+aa+"\t"+bb+"\t"+cc;
	//	statics.FileStuff.fileSentence("test.txt" ,sentence);
	//	System.out.println(la + "\t " + lb + " \t" + lc + "\t"+aa+"\t"+bb+"\t"+cc);
		double[][] mat = { { (1 + aa) * e, 2 * aa * e, 2 * e, (1 + aa) * e },
				{ (bb + cc) * d, -2 * bb * cc * d, -2 * d, (bb + cc) * d } };

		sw.setHyperbolicMatrix(mat);
		double constant =  Math.min(lc / 2, Math.min(lb, la));
	//	System.out.println(la + " " + lb + " " + lc + " " + "constant "		+ constant);
		
		//statics.FileStuff.fileSentence(filename, la + " " + lb + " " + lc + " "
		int cl1 = 1;
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();
		
		double hl = 0;
		int counter = 0;
		while (c.length()*constant <= hypLength) {
			if (c.hyperbolicLength() < hypLength) {
				hl=hl+c.hyperbolicLength();
				counter++;
			//	System.out.println(hl+" "+counter);
			}//else System.out.println(c.toString()+" "+c.hyperbolicLength()+" "+constant);
			c = c.next();
		//	System.out.println(hl+" "+counter+" "+c.toString());
		}
		//System.out.println(la+"\t"+ lb+"\t"+ lc+ "\t"+counter+" "+ hl/counter+" "+hl);
		return  new double[] {counter, hl/counter,constant};
	}
	
	public static void hlengthSI(SurfaceWord sw, double la, double lb, double lc, String filename, double hypLength) {
		filename = filename+hypLength;
		double constant =  Math.min(lc / 2, Math.min(lb, la));
		System.out.println(hypLength+" "+la + " " + lb + " " + lc + " " + "constant "
				+ constant);
		
		statics.FileStuff.fileSentence(filename, la + " " + lb + " " + lc + " "
				+ "constant " + constant);
		int cl1 = 1;

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[cl1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();

		// String filename = "ADX" + combLength1;
		while (c.length()*constant <= hypLength) {
			//
			// System.out.println("XX"+sentence);
		//	statics.FileStuff.fileSentence(filename + cl1,sentence);
			if (c.hyperbolicLength() < hypLength) {
				String sentence=c.toString()+" \t"+c.length()+"\t" +c.hyperbolicLength()+ "\t" + c.selfIntersection();
				// System.out.println(sentence);
				statics.FileStuff.fileSentence(filename ,sentence);

			}

			c = c.next();
		}
		// System.out.println(cl1+"\t "+p+"\t "+q);
		// statics.FileStuff.fileSentence(filename,cl1+"\t "+p+"\t "+q);

	}
	
	

	/**
	 * Produces a file with the self intersection and length of all geodesic of length at most maxCL/constant.
	 * @param sw
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @param maxCL
	 */
	public static void distrib(SurfaceWord sw, double la, double lb, double lc,
			String filename, int maxCL) {
		filename = filename+maxCL;
		double constant =  Math.min(Math.min(lc / 2, (la + lb) / 2), Math
				.min(lb, la));
		System.out.println(maxCL+" "+la + " " + lb + " " + lc + " " + "constant "
				+ constant);

		statics.FileStuff.fileSentence(filename, la + " " + lb + " " + lc + " "
				+ "constant " + constant);
	
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
				new int[1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();

		// String filename = "ADX" + combLength1;
		while (c.length()*constant <= maxCL) {
			//
			// System.out.println("XX"+sentence);
		//	statics.FileStuff.fileSentence(filename + cl1,sentence);
		//	String sentence= c.selfIntersection()+"";
				String sentence=c.toString()+" \t"+c.length()+"\t" +c.hyperbolicLength()+ "\t" + c.selfIntersection();
				// System.out.println(sentence);
				statics.FileStuff.fileSentence(filename ,sentence);

			

			c = c.next();
		}
		// System.out.println(cl1+"\t "+p+"\t "+q);
		// statics.FileStuff.fileSentence(filename,cl1+"\t "+p+"\t "+q);

	}
	
	/**
	 *
	 *Computes the list of self-intersections of all geodesics up to certain length.
	 * @param sw
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @param maxCL
	 */
	public static void hypLengthOnlySI(SurfaceWord sw, double la, double lb, double lc,String filename, int maxCL) {
		filename = "N"+filename+maxCL+"_"+la+"_"+lb+"_"+lc+"_"+maxCL;
		double constant = Math.min(lc / 2, Math.min(lb, la));
		String s="maxCL: "+maxCL+" "+la + " " + lb + " " + lc + " " + "constant "	+ constant+"\t max hyp Length: "+constant*maxCL;
		System.out.println(s);
		int[] si= new int[maxCL*maxCL/4+maxCL/2];
		//statics.FileStuff.fileSentence(filename, s);
		

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
				new int[1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();

		 ArrayList<Integer> list = new ArrayList<Integer>();
		// String filename = "ADX" + combLength1;
		while (c.length() <= maxCL) {
			//
			if(c.hyperbolicLength()<=constant*maxCL){
				si[c.selfIntersection()]++;
				list.add(c.selfIntersection());
			}

			c = c.next();
		}
		// System.out.println(cl1+"\t "+p+"\t "+q);
		 statics.FileStuff.fileVector(si, filename, "si up to length "+maxCL);
		 statics.FileStuff.fileSentence(filename, s);
		 String tab="";
		 for(int h =0 ; h < list.size() ; h++){
			tab=tab+list.get(h)+"\n";
			// statics.FileStuff.fileVector(si, filename+"TT", list.get(h)+""); 
		 }
		// statics.FileStuff.fileVector(si, filename+"T", tab);
	}

	public static void constantWord(SurfaceWord sw, double la, double lb,
			double lc, String filename, int maxCL) {
		double constant = 2 / Math.min(lc, Math.min(lb, la));
		System.out.println(la + " " + lb + " " + lc + " " + "constant "
				+ constant);

		statics.FileStuff.fileSentence(filename, la + " " + lb + " " + lc + " "
				+ "constant " + constant);
		for (int cl1 = 3; cl1 < maxCL; cl1++) {
			PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
					new int[cl1], sw.length());
			Curve c = new Curve(dcw, sw);
			c.initialize();
			double q = 1;
			double p = 1;
			// String filename = "Constant1" ;

			// String filename = "ADX" + combLength1;
			while (c.length() == cl1) {
				if (p < c.hyperbolicLength() / c.length()) {
					p = c.hyperbolicLength() / c.length();
					String sentence = "p " + cl1 + "\t " + p + "\t "
							+ c.toString();
					System.out.println(sentence);
					statics.FileStuff
							.fileSentence(filename + "curve", sentence);
				}
				if (q < c.length() / c.hyperbolicLength()) {
					q = c.length() / c.hyperbolicLength();
					String sentence = "q " + cl1 + "\t " + q + "\t "
							+ c.toString();
					System.out.println(sentence);
					statics.FileStuff
							.fileSentence(filename + "curve", sentence);
				}

				// System.out.println(c.length() + " " + c.toString() + " " +
				// c.hyperbolicLength());
				/*
				 * if (c.length() < constant*combLength1 ) { //
				 * System.out.println(c.toString()+" " +c.hyperbolicLength()+
				 * "\t" + c.selfIntersection());
				 * statics.FileStuff.fileSentence(filename,
				 * c.hyperbolicLength()+ "\t" + c.selfIntersection());
				 * 
				 * }
				 */
				c = c.next();
			}
			System.out.println(cl1 + "\t " + p + "\t " + q);
			statics.FileStuff.fileSentence(filename, cl1 + "\t " + p + "\t "
					+ q);
		}
	}

}

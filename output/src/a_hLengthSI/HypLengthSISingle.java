package a_hLengthSI;


import hypLengthComp.LengthSamples;

import java.util.ArrayList;



import a_hypLengComp.ExactMean1;
import boundary.*;

/*
 * Note:
 * m=min lenght of segment in polygon
 * M=max length of segment in polygon
 * l(w) geometric length
 * cl(w) combinatorial lenght
 * 
 *  then m. cl(w) � l(w) � M. cl(w)
 *  
 *   Claim m = min{l1, l2, l3/2}
 *   Proof: divide in hexagons, etc.
 *   
 *   If l(w) � K then cl(w) � K/m.
 *   
 *   The set of geodesics of geometric length at most K,
 *    is contained in the set of words of CL at most K/m..
 */

public class HypLengthSISingle {
	
	
	public static void meanVary(SurfaceWord sw, String filename ,int cl){
		 
	       for(int ka=1 ; ka<7 ; ka++){
	    	   for(int kb=1 ; kb<7 ; kb++){
	    		   for(int kc=1 ; kc<7 ; kc++){
	    			   double la = ka*1.75;
	    			   double lb =kb*1.75;
	    			   double lc =kc*1.75;
	    	String sentence =   la+"\t"+ lb+"\t"+ lc+ "\t"+mean(sw, la,lb,lc, cl);
	    	statics.FileStuff.fileSentence(filename ,sentence);
	       //  ExactMean.meanTraceParamLengths(sw,maxCL, la,lb, lc, filename);
	    	   }}    }
	}

	public static double[] mean(SurfaceWord sw, double la, double lb, double lc, double hypLength) {
		
		double constant =  Math.min(lc / 2, Math.min(lb, la));
		System.out.println(hypLength+" "+la + " " + lb + " " + lc + " " + "constant "		+ constant);
		
		//statics.FileStuff.fileSentence(filename, la + " " + lb + " " + lc + " "
		int cl1 = 1;
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[cl1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();
		
		double hl = 0;
		int counter = 0;

		while (c.length()*constant <= hypLength) {
			
			if (c.hyperbolicLength() < hypLength) {
				hl=hl+c.hyperbolicLength();
				counter++;
			}

			c = c.next();
		}
		System.out.println(counter+" "+ hl/counter);
		return  new double[] {counter, hl/counter};
	}
	
	/**
	 * @param sw
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @param hypLength
	 */
	public static void hlengthSI(SurfaceWord sw, double la, double lb, double lc, String filename, double hypLength) {
		String dir ="dataApril2013/";
		statics.CreateDirectory.create(dir);
		filename = dir+filename+hypLength;
		double constant =  Math.min(lc / 2, Math.min(lb, la));
		System.out.println(hypLength+" "+la + " " + lb + " " + lc + " " + "constant "
				+ constant);
		
		statics.FileStuff.fileSentence(filename+1, la + " " + lb + " " + lc + " "
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
				//String sentence=c.hyperbolicLength()+",";
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
	 * @param sw
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @param hypLength
	 */
	public static void hlengthSITable(SurfaceWord sw, double la, double lb, double lc, String filename, double hypLength) {
		String dir ="dataApril2012/";
		statics.CreateDirectory.create(dir);
		

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
		
		filename = dir+filename+hypLength;
		double constant =  Math.min(lc / 2, Math.min(lb, la));
		System.out.println(hypLength+" "+la + " " + lb + " " + lc + " " + "constant "
				+ constant);
		
		statics.FileStuff.fileSentence(filename+1, la + " " + lb + " " + lc + " "
				+ "constant " + constant);
		int cl1 = 1;

		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[cl1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();

		while (c.length()*constant <= hypLength) {
			//
			// System.out.println("XX"+sentence);
		//	statics.FileStuff.fileSentence(filename + cl1,sentence);
			if (c.hyperbolicLength() < hypLength) {
				//String sentence=c.hyperbolicLength()+",";
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
	 * Produces a file with the self intersection and length 
	 * of all geodesic of length at most maxCL/constant.
	 * @param sw
	 * @param la
	 * @param lb
	 * @param lc
	 * @param filename
	 * @param maxCL
	 */
	public static void distrib(SurfaceWord sw, double la, double lb, double lc,
											String filename,int maxCL) {
		String dir ="dataApril2013/";
		statics.CreateDirectory.create(dir);
		filename =dir+ filename+maxCL;
		double constant =  Math.min(Math.min(lc / 2, (la + lb) / 2), Math
				.min(lb, la));
		System.out.println(maxCL+" "+la + " " + lb + " " + lc + " " + "constant "
				+ constant+(maxCL/constant));

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
	public static void hypLengthOnlySI(SurfaceWord sw, double la, double lb, double lc, String filename, double hypLength) {
		filename = "N"+filename+hypLength+"_"+la+"_"+lb+"_"+lc;
		double constant = Math.min(lc / 2, Math.min(lb, la));
	//	String s="maxCL: "+hypLength+" "+la + " " + lb + " " + lc + " " + "constant "	+ constant+"\t max hyp Length: "+constant;
	//	System.out.println(s);
		int[][] si= new int[40][40];
		//statics.FileStuff.fileSentence(filename, s);
		
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
		
		 int cl1 = 1;
		 PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[cl1], sw.length());
		 Curve c = new Curve(dcw, sw);
		 c.initialize();
		 System.out.println(c.toString()+" XX "+c.selfIntersection()+" this "+c.hyperbolicLength());
		// String filename = "ADX" + combLength1;
		
		while (c.length()*constant <= hypLength) {
			//System.out.println(c.toString()+" "+c.selfIntersection()+" this "+c.hyperbolicLength());
				if (c.hyperbolicLength() < hypLength) {
					System.out.println(c.toString()+" "+c.selfIntersection()+" this " +c.hyperbolicLength()+" "+Math.ceil(c.hyperbolicLength()));
					si[(int) Math.ceil(c.hyperbolicLength())][c.selfIntersection()]++;
			//		list.add(c.selfIntersection());
					

				}

				c = c.next();
			}
		 
	 statics.FileStuff.fileMatrix(si, filename+hypLength ,filename+hypLength);	
		// System.out.println(cl1+"\t "+p+"\t "+q);
	//	 statics.FileStuff.fileVector(si, filename, "si up to length "+hypLength);
		// statics.FileStuff.fileSentence(filename, s);
	//	 String tab="";
	//	 for(int h =0 ; h < list.size() ; h++){
	//		tab=tab+list.get(h)+"\n";
	//		// statics.FileStuff.fileVector(si, filename+"TT", list.get(h)+""); 
		// }
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

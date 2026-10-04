package a_keren_GL_samplingByWL;



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
 *  then m. cl(w) ² l(w) ² M. cl(w)
 *  
 *   Claim m = min{l1, l2, l3/2}
 *   Proof: divide in hexagons, etc.
 *   
 *   If l(w) ² K then cl(w) ² K/m.
 *   
 *   The set of geodesics of geometric length at most K, is contained in the set of words of CL at most K/m..
 *   
 *   
 *   Thus we will look all geodesics of length at most K, as a subset of the set of words of comb length K/m. Now, if m is small, 
 *   K/m is big. This makes computations hard or impossible 
 *   
 *   Thus, one should choose parameters so K/m ² CL, where CL is a combinatorial length the computer can deal with.
 *   
 *   In the program mean we input maxHypLength and metric. 
 *   We compute all cl, such tha cl.m ² maxHypLength.
 *   We need m.maxHypLength ² 10, or 11, 12
 *   
 *   Since m = min { la, lb, lc/2}, then one should take  
 *   
 */

public class DriverMeanAllHypLengthVaries {
	public static void main(String[] args) {
		int maxHypLength = 10;
		System.out.println(maxHypLength);
		String filename ="name111x2x"+maxHypLength;
		String sentence1="la"+"\t"+" lb"+"\t"+" lc"+ "\t"+"maxHypL"+"\t"+"counter"+"\t"+"average";;
		statics.FileStuff.fileSentence(filename ,sentence1);

		for(int ka=1 ; ka<200 ; ka++){
				  double la = 2;
					double[] m= mean( la,la,la, 1+ka*0.25);;
					
				
						String sentence =   la+"\t"+ la+"\t"+ la+"\t"+(1+ka*0.1)+ "\t"+m[0]+"\t"+m[1];
						statics.FileStuff.fileSentence(filename ,sentence);
						  System.out.println(sentence);
					
				
	}

}


public static double[] mean( double la, double lb, double lc, double hypLength) {

	SurfaceWord sw = new SurfaceWord("aAbB");

	double[] parameters = hypLengthComp.LengthSamples.computeParameters(la, lb,lc);
	double aa = parameters[0];
	double bb = parameters[1];
	double cc = parameters[2];
	double d = 1 / (cc - bb);
	double e = 1 / (1 - aa);
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

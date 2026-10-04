package a_hLengthSI;
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
*/		

public class Constant {
	public static void main(String[] args) {
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);

		double la = 10;
		double lb = 1;
		double lc = 0.1;

		double[] parameters = hypLengthComp.LengthSamples.computeParameters(la, lb,
				lc);
		
       for(int h = 1 ; h<40 ; h++){
    	  lc=lc+0.1;
    	  la=la-0.001;
    	  lb=lb-0.001;
    	  parameters = hypLengthComp.LengthSamples.computeParameters(la, lb,lc);
    //	 System.out.println(lc);
    	 double aa = parameters[0];
 		double bb = parameters[1];
 		double cc = parameters[2];
 		double d = 1 / (cc - bb);
 		double e = 1 / (1 - aa);
 		double[][] mat = { { (1 + aa) * e, 2 * aa * e, 2 * e, (1 + aa) * e },
 				{ (bb + cc) * d, -2 * bb * cc * d, -2 * d, (bb + cc) * d } };

 		
 		sw.setHyperbolicMatrix(mat);
		constant(sw, la,lb,lc, "testC1",14);
       }
 
	}
	
	
	public static void constant(SurfaceWord sw, double la, double lb, double lc, String filename, int maxCL ){
		double constant = Math.min(lc/2 , Math.min(lb , la ));
		System.out.println(la+" "+lb+" "+lc+" "+"constant "+constant);
		
		statics.FileStuff.fileSentence(filename,la+" "+lb+" "+lc+" "+"constant "+constant);
	       for (int cl1 = 1; cl1 < maxCL; cl1++) {
	        	PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
						new int[cl1], sw.length());
	        	Curve c = new Curve(dcw, sw);
	        	c.initialize();
	        	double q = 1;
				double p = 1;
				//String filename = "Constant1" ;
				
				//String filename = "ADX" + combLength1;
				while(c.length()==cl1 ){
						p=c.hyperbolicLength()/c.length();
				//	p= Math.max(c.hyperbolicLength()/c.length(),p);
					q= Math.max(c.length()/c.hyperbolicLength(),q);
					if(p+0.00000000001<constant){
						String sentence = cl1+"\t "+c.toString()+"\t "+constant+" \t"+p;
						System.out.println(sentence);
						statics.FileStuff.fileSentence(filename,sentence);
					}
					//System.out.println(c.length() + " " + c.toString() + " "	+ c.hyperbolicLength());
					/*
					if (c.length() < constant*combLength1 ) {
					//	 System.out.println(c.toString()+" " +c.hyperbolicLength()+ "\t" + c.selfIntersection());
						statics.FileStuff.fileSentence(filename, c.hyperbolicLength()+ "\t" + c.selfIntersection());

					}
					*/
					c = c.next();
				}
				//System.out.println(cl1+"\t "+p+"\t "+q);
				//statics.FileStuff.fileSentence(filename,cl1+"\t "+p+"\t "+q);
	        }
	}
	
	
	public static void constantWord(SurfaceWord sw, double la, double lb, double lc, String filename, int maxCL ){
		double constant = 2 / Math.min(lc , Math.min(lb , la ));
		System.out.println(la+" "+lb+" "+lc+" "+"constant "+constant);
		
		statics.FileStuff.fileSentence(filename,la+" "+lb+" "+lc+" "+"constant "+constant);
	       for (int cl1 = 3; cl1 < maxCL; cl1++) {
	        	PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(
						new int[cl1], sw.length());
	        	Curve c = new Curve(dcw, sw);
	        	c.initialize();
	        	double q = 1;
				double p = 1;
				//String filename = "Constant1" ;
				
				//String filename = "ADX" + combLength1;
				while(c.length()==cl1 ){
					if(p<c.hyperbolicLength()/c.length()){
						p=c.hyperbolicLength()/c.length();
						String sentence="p "+cl1+"\t "+p+"\t "+c.toString();
						System.out.println(sentence);
						statics.FileStuff.fileSentence(filename+"curve",sentence);
					}
					if(q<c.length()/c.hyperbolicLength()){
						q=c.length()/c.hyperbolicLength();
						String sentence ="q "+cl1+"\t "+q+"\t "+c.toString();
						System.out.println(sentence);
						statics.FileStuff.fileSentence(filename+"curve",sentence);
					}
					
					
					//System.out.println(c.length() + " " + c.toString() + " "	+ c.hyperbolicLength());
					/*
					if (c.length() < constant*combLength1 ) {
					//	 System.out.println(c.toString()+" " +c.hyperbolicLength()+ "\t" + c.selfIntersection());
						statics.FileStuff.fileSentence(filename, c.hyperbolicLength()+ "\t" + c.selfIntersection());

					}
					*/
					c = c.next();
				}
				System.out.println(cl1+"\t "+p+"\t "+q);
				statics.FileStuff.fileSentence(filename,cl1+"\t "+p+"\t "+q);
	        }
	}
	
	
}

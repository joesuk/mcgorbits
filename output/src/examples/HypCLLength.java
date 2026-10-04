package examples;
import boundary.*;


public class HypCLLength {
	public static void main(String[] args){
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		
		double maxHypLength=9;
		double constant = 1.8;
		double maxCLLength= maxHypLength*constant;
		double spread =3;
		/****************************************/
		/*    Sorted list by hyperbolic length  */
		/****************************************/
		//Need  a < 1 < b < c
		double aa = 0.9999;
		double bb= 1010;
		double cc= 1020;
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
				{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		int aux =(int) Math.round(maxHypLength*constant*spread);
		int[] hyp = new int[aux];
		System.out.println(aux);
		sw.setHyperbolicMatrix(mat);
		CurveList cl = new CurveList(sw);
		
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1] , sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();
		
		
		double totalhypLength=0;
		int totalselfint =0;
		while(c.length()<=maxCLLength){
			if(c.hyperbolicLength()<maxHypLength){
				cl.add(c);
				
				int l=(int) Math.round(c.hyperbolicLength()*spread);
				
				hyp[l]++;
				totalselfint=totalselfint+c.selfIntersection();
				totalhypLength = totalhypLength + c.hyperbolicLength();
				
				//System.out.println(c.toString());
				}
			
			c = c.next();
		}
		
		
		
		String filename = "testaAbB CL00"+maxHypLength;
		statics.FileStuff.fileVector(hyp, filename, "Self int up to hyp length "+ maxHypLength);
		statics.FileStuff.fileSentence(filename, "Average length "+totalhypLength/cl.size());
		statics.FileStuff.fileSentence(filename, "Average length over l^2 "
				+totalhypLength/(cl.size()*maxHypLength*maxHypLength));
		
		

		
	}
}

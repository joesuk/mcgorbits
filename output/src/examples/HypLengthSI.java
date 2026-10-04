package examples;
import boundary.*;


public class HypLengthSI {
	public static void main(String[] args){
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		double maxHypLength=10;
		double constant = 1.8;
		//int total =100;
		/****************************************/
		/*    Sorted list by hyperbolic length  */
		/****************************************/
		//Need  a < 1 < b < c
		double aa = 0.001;
		double bb= 1.01;
		double cc= 1000;
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
				{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,-2*bb*cc*d,-2*d,(bb+cc)*d}};
		int[] inter = new int[(int) (maxHypLength*constant*maxHypLength*constant/4+2)];
		
		sw.setHyperbolicMatrix(mat);
		CurveList cl = new CurveList(sw);
		
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1] , sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();
		
		double maxCLLength= maxHypLength*constant;
		double totalhypLength=0;
		int totalselfint =0;
		while(c.length()<maxCLLength){
			if(c.hyperbolicLength()<maxHypLength){
				cl.add(c);
				inter[c.selfIntersection()]++;
				totalselfint=totalselfint+c.selfIntersection();
				totalhypLength = totalhypLength + c.hyperbolicLength();
				
				//System.out.println(c.toString());
				}
			
			c = c.next();
		}
		
		String filename = "testaAbB"+maxHypLength;
		statics.FileStuff.fileVector(inter, filename, "Self int up to hyp length "+ maxHypLength);
		statics.FileStuff.fileSentence(filename, "Average length "+totalhypLength/cl.size());
		statics.FileStuff.fileSentence(filename, "Average length over l^2 "
				+totalhypLength/(cl.size()*maxHypLength*maxHypLength));
		
		

		
	}
}

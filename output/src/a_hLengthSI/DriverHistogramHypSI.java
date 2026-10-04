package a_hLengthSI;

import hypLengthComp.LengthSamples;
import boundary.SurfaceWord;

public class DriverHistogramHypSI {
	public static void main(String[] args){
		String z = "aAbB";
		
		
		double la= 0.5;
		double lb= 0.9999;
		double lc =1.;
		SurfaceWord sw = new SurfaceWord("aAbB");
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
		
	//	HypLengthSISingle.distrib(sw, la, lb, lc,"new1", 9);
	//	HypLengthSISingle.distrib(sw, la, lb, lc,"new1", 4);
		
		
	//
	}

}

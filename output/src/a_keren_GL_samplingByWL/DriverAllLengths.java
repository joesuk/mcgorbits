package a_keren_GL_samplingByWL;


import hypLengthComp.Histogram;
import hypLengthComp.LengthSamples;
import boundary.SurfaceWord;

/**
 * Computes all the lengths of cyclic reduced words of length i, in a pair of pants 
 * with geodesic boundary components of length of the given parameters
 * @author moira
 *
 */
public class DriverAllLengths {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=2; i<20 ; i++){
		shortcut(i,1,2,3,"Maskit-Li/");
		
		}
		/*
		 * 
		shortcut(12,1,1,1,"Febrero2013");
		shortcut(12,0.1,1,10,"Febrero2013");
		 */
	//(16,1,1,1,"Febrero2013");
		
	//	shortcut(14,1,1,1,"Febrero2013");
	}
	
	public static void shortcut(int len, double la, double lb, double lc, String dir){
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
		
		Histogram.findSortedListOfAllGLengthsCommas(len, sw, "list"+la+"_"+lb+"_"+lc, dir);
	//	Histogram.computeMeanStandardMinMaxDevAll(len, la, lb, lc,  sw);
	}


}

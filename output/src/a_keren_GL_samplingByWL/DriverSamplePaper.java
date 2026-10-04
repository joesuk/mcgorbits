
package a_keren_GL_samplingByWL;



import hypLengthComp.RandomWords;


/**
 * This is a driver for the class histogram.
 * Takes a sample of sampleSize random words of lenght combLength, 
 * and produces a list of the prescribed lengths on the given metric.
 * @author moira
 *
 */
public class DriverSamplePaper {
	public static void main(String[] args){
		
		int sampleSize =2;
		
		int combLength = 100;
		double la =2.5;
		double lb=2.6;
		double lc=5;
		
		a_hypLengComp.RandomWords.listOfLengthsTrace(sampleSize, combLength, la, lb, lc);
	
		//RandomWords.matrixEntries(sampleSize, 1, la, lb, lc);
		
	
			
	
	
	
	
	}
	
	
}

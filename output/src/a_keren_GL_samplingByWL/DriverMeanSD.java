package a_keren_GL_samplingByWL;

import hypLengthComp.Histogram;

public class DriverMeanSD {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double cte=0.5;
		for(int a = 1 ; a<10 ; a=a+2 ){
			for(int b= 	a ; b < 10 ; b=b+2){
				for(int c=1 ; c < 22 ; c=c+2){
			
						Histogram.computeMeanStandardMinMaxDevSample(10000, 50,9+ a*cte,9+b*cte,c*cte,"xlcxykkzuRk");
						
				}}}
	}

}

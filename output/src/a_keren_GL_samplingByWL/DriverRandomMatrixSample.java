package a_keren_GL_samplingByWL;


import hypLengthComp.Histogram;
import hypLengthComp.LengthSamples;

import java.util.ArrayList;
import java.util.Collections;



import boundary.SurfaceWord;

/**
 * This is a driver for the class histogram.
 * Takes a sample of sampleSize random words of lenght combLength, 
 * and produces a list of the prescribed lengths onthe given metric.
 * @author moira
 *
 */
public class DriverRandomMatrixSample {
	public static void main(String[] args){
		String dir ="Mar20/";
		statics.CreateDirectory.create(dir);
		String filename = dir+"data1";
		
		int sampleSize =300 ;
		int CL =800;
		double la =1;
		double lb=0.56;
		double lc =0.75;
		
		String sentence =" la \t lb \t lc \t cl \t mean a/Size \t mean/Size b \t mean c/Size \t mean d/Size \t var a\t var b \t var c \t var  d";
		statics.FileStuff.fileSentence(filename, sentence);
	//	String sentence1=" la \t lb \t lc \t cl \t va \t vb \t vc \t vd \t tr";
 	//	statics.FileStuff.fileSentence(filename+"ALL", sentence1);
		for(int cl = 200; cl< 1050; cl=cl+50) {
		hypLengthComp.RandomWords.prodGivenMatricesPants(filename ,sampleSize,cl, la,lb,lc);
		}
		
		for(int i = 50; i<= 1000; i=i+10) {
		//	hypLengComp.RandomWords.randomProductCycRedMatrix( i,100, 1.7,1.089,0.7);
		
		}
		
	/*	
		double a= 1;
		double b=2; //should be non zero.
		
		double det =1;
		double tr =4 ;
		
		double d = tr -a; 
		double c= (det-a*d)/b;
		
		
		double a1 = 1;
		double b1 = 3;
		double det1 = -1;
		double tr1 =-4;
		
		double d1 =tr1-a1;
		double c1 = (det1-a1*d1)/b1;
		
		shortcut(sampleSize, CL, a1,b1,c1,d1, a,b,c,d);
		
*/
	}

	

	
}
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
public class DriverMatricesNotFromPants {
	public static void main(String[] args){
		
		int sampleSize =10000 ;
		int CL =50;
		
		double a= 1;
		double b=2; //should be non zero.
		
		double det =1;
		double tr =2 ;
		
		double d = tr -a; 
		double c= (det-a*d)/b;
		
		
		double a1 = 1;
		double b1 = 3;
		double det1 = -1;
		double tr1 =-2;
		
		double d1 =tr1-a1;
		double c1 = (det1-a1*d1)/b1;
		
		hypLengthComp.Shortcuts.traceMatrices(sampleSize, CL, a1,b1,c1,d1, a,b,c,d);
		
		/*
		 * double x =Math.PI*Math.sqrt(3);
		double xx =Math.PI/4;
		 
		shortcut1(sampleSize, CL, Math.cos(x),-Math.sin(x), Math.sin(x),Math.cos(x), a,b,c,d);
		shortcut1(sampleSize, CL, Math.cos(x),-Math.sin(x), Math.sin(x),Math.cos(x), a,b,cm,d);
		*/
		//shortcut1(sampleSize, CL, Math.cos(x),-Math.sin(x), Math.sin(x),Math.cos(x), 	2,0,0,0.5);
    	//shortcut1(sampleSize, CL, Math.cos(x),-Math.sin(x), Math.sin(x),Math.cos(x), 
	//								Math.cos(xx),-Math.sin(xx), Math.sin(xx),Math.cos(xx)	);
		//shortcut(sampleSize, CL, -1,1,1,0,1,1,1,0);
		//
		
		

	}
	
	

	
	
	
}
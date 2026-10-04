package a_keren_GL_samplingByWL;

import hypLengthComp.RandomWords;

public class DriverRandomMatrix {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int sampleSize = 50000;
		int combLength=600;
		
		double a =1.30;
		double b= 0.2;
		
		double d= -0.20;
		double c = (1+a*d)/b;

		
		double a1 =-1.24;
		
		double c1 =0.8;
		double d1= 1.4;
		double b1= (-1+a1*d1)/c1;
		
		System.out.println(a*d-b*c);
		System.out.println(a1*d1-b1*c1);
		
		
		boolean det0 = false;
		
		RandomWords.prodGivenMatrices(det0, sampleSize, combLength, a,b,c,d,a1,b1,c1,d1);
		
		//for(int i = 2; i <10 ; i++)
		
		int cte= 1;
		int cte1 =1;
		double c01=- 1.01;
		double c0=-1.353132;
		double cc= 1.25;
		double cc1=1.2;
		
		for(int cl=1000 ; cl<1000; cl=cl+100){
		//for(int ka= 0 ; ka<1; ka++ ){
			for(int kb= 0 ; kb<cte; kb++ ){
				for(int kc= 0 ; kc<cte; kc++ ){
					//for(int kd= 0 ; kd<cte; kd++ ){
						for(int ka1= 0 ; ka1<cte1; ka1++ ){
							//for(int kb1= 0 ; kb1<cte1; kb1++ ){
								//for(int kc1 = 0 ; kc1<cte1; kc1++ ){
									for(int kd1= 0 ; kd1<cte1; kd1++ ){
										double aaa=1;
										double bbb=(c0+kb*cc);
										double ccc=(c0+kc*cc);
										double ddd= bbb*ccc+1;
										
										double aaa1=(c01+ka1*cc1);
										double bbb1= 1;
										double ddd1=(c01+kd1*cc1);
										double ccc1=aaa1*ddd1-1;
										
										
										if( aaa1*ddd1-bbb1*ccc1 != 0){
											
											RandomWords.prodGivenMatrices(det0, sampleSize, cl, aaa,bbb,ccc,ddd,aaa1,bbb1,ccc1,ddd1);
										//}}}}
		}}}}}
			
		}
		/*
		 * this values dont seem to conerge
		 * 
		 * 
		 int sampleSize = 200;
		int combLength=1000;
		
		double a = 0.2;
		double b= 1;
		double c = -1;
		double d= 0.3;

		double a1 =5;
		double b1=-5;
		double c1 =0.1;
		double d1= -4.5;
		 * 
		 * 
		 * 
		 * 
	 * 	double x = 0.115*Math.PI;
		double a = Math.cos(x);
		double b= Math.sin(x);
		double c = -Math.sin(x);
		double d= Math.cos(x);
		double a1 = Math.cos(x);
		double b1= Math.sin(x);
		double c1 = -Math.sin(x);
		double d1= Math.cos(x);
	
		*/
		
		//RandomWords.randomProductNonNecCycRedMatrix(sampleSize, combLength, la, lb, lc);
		
	}

}

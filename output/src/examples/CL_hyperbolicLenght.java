package examples;

import hypLengthComp.LengthSamples;
import boundary.*;


public class CL_hyperbolicLenght {
	public static void main(String[] args){
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		int combLength =3;
		/****************************************/
		/*    Sorted list by hyperbolic length  */
		/****************************************/
		//Need  a < 1 < b < c
		double la =1;
		double lb=2;
		double lc=3;
		
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
		
		
		
		System.out.println(mat[0][0]+mat[0][3]);
		System.out.println(mat[1][0]+mat[1][3]);
		sw.setHyperbolicMatrix(mat);
		CurveList cl = new CurveList(sw);
		
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[combLength] , sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();
		
	
		while(c.length()<=combLength){
			cl.add(c);
			System.out.println(c.toString());
			c = c.next();
		}
		
		
		
		cl = cl.sortedCurveList(new SortByHyperbolicLength());
		c.initialize();
		for(int i=0;i<cl.size();i++){
			//cl.add(c);
			double hyp=cl.get(i).hyperbolicLength();
			double q =  (cl.get(i).hyperbolicLength()/cl.get(i).length());
			System.out.println(cl.get(i).toString()+"\t "+hyp+"\t"+q);
			
		}
	}	
}

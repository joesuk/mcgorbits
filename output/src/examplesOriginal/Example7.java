package examplesOriginal;
import boundary.*;
//simple curves sorted by hyperbolic metric.

public class Example7 {
	public static void main(String[] args){
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		
		int total = 100;
		/****************************************/
		/*    Sorted list by hyperbolic length  */
		/****************************************/
		//Need  a < 1 < b < c
		double aa = 0.2;
		double bb= 1.2;
		double cc= 3;
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
				{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,2*bb*cc*d,2*d,(bb+cc)*d}};
		
		
	
		sw.setHyperbolicMatrix(mat);
		CurveList cl = new CurveList(sw);
		
		DoublyCyclicWord dcw = new DoublyCyclicWord(new int[1], sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();
		for(int i=0;i<total;i++){
			cl.add(c);
			
			c = c.next();
		}
		
		cl = cl.sortedCurveList(new SortByHyperbolicLength());
		System.out.println("Simple Curves of length at most 3 for surface "+sw.toString()+" sorted by hyperbolic length");
		for(int i=0;i<total;i++){
			System.out.println(cl.get(i)+"\t"+cl.get(i).hyperbolicLength());
			
			c = c.next();
		}
		
		
	}
}

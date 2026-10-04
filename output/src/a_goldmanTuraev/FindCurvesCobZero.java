
package a_goldmanTuraev;
import boundary.*;
import boundaryBialgebra.CL;
import boundaryBialgebra.CobracketTerms;


public class FindCurvesCobZero {
	public static void main(String[] args){
		String z = "abAB";
		SurfaceWord sw = new SurfaceWord(z);
		CL cl = new CL(z);
		
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[1] , sw.length());
		Curve c = new Curve(dcw, sw);
		c.initialize();
		
	
		while(c.length()<=10){
		//	System.out.println(c.toString() );
			CobracketTerms ct =cl.cobracket(cl.toNumber(c.toString()));
			
			if(ct.getTerms().size()==0){
				System.out.println(c.toString()+" "+c.selfIntersection());
			}
			c = c.next();
		}
		
		
				
	}	
}

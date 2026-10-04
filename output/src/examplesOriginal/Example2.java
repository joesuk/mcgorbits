package examplesOriginal;
import boundary.*;


public class Example2 {
	public static void main(String[] args){
		String z = "abAB";
		SurfaceWord sw = new SurfaceWord(z);
		/****************************************/
		/*    All curve with length k          */
		/****************************************/
		int k = 4;
		//This create a CyclicWord [0,0] or 'aa', with base sw.length()
		PrimitiveDoublyCyclicWord cw = new PrimitiveDoublyCyclicWord(new int[k], sw.length());
		Curve c = new Curve(cw, sw);
		//create the first valid Curve with length k
		c.initialize();
		System.out.println("All the curve with length"+k+" for surface "+sw);
		while(c.length()==k){
			System.out.println(c);
			c = c.next();
		}
	}
}

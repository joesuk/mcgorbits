package examplesOriginal;
import boundary.*;


public class Example1 {
	public static void main(String[] args){
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		/****************************************/
		/*              Basic Example           */
		/****************************************/
		//Every curve is constructed from a CyclicWord and a SurfaceWord
		//This create a CyclicWord [0] or 'a', with base sw.length()
		CyclicWord cw = new CyclicWord(new int[1], sw.length());
		//This create a Curve c from the CyclicWord cw and SurfaceWord sw.
		//This now represents a [0], or 'a' on the surface abABcdCD or [0,2,1,3,4,6,5,7]
		Curve c = new Curve(cw, sw);
		c.initialize();
		System.out.println("First 20 curves for surface "+sw);
		for(int i=0;i<20;i++){
			//This will output c in the String form, println automatically calls the toString() function
			System.out.println(c);
			//Set c to be the next curve
			c = c.next();
		}
	}
}

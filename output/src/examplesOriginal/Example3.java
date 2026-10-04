package examplesOriginal;
import boundary.Curve;
import boundary.CyclicWord;
import boundary.DoublyCyclicWord;
import boundary.SurfaceWord;


public class Example3 {
	public static void main(String[] args){
		String z = "abABcdCD";
		SurfaceWord sw = new SurfaceWord(z);
		/****************************************/
		/*    Change the set of CyclicWords     */
		/****************************************/
		//Sometimes we might want to restrict the words we are using
		//For example, we will use DoublyCyclicWord, a subclass of CyclicWord
		DoublyCyclicWord dcw = new DoublyCyclicWord(new int[1], sw.length());
		//Note how the Curve is constructed from DoublyCyclicWords
		Curve c = new Curve(dcw, sw);
		c.initialize();
		System.out.println("First 20 curves(direction doesn't matter) for surface "+sw);
		for(int i=0;i<20;i++){
			System.out.println(c);
			//Set c to be the next curve, but we restricted the words to be
			//DoublyCyclicWord, therefore next() only return curves
			//That are composed with DoublyCyclicWord
			c = c.next();
		}	
	}
}

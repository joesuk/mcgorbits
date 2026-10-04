package examplesOriginal;
import boundary.Curve;
import boundary.DoublyCyclicWord;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SimpleCurve;
import boundary.SurfaceWord;


public class Example4 {
	public static void main(String[] args){
		String z = "aAbBcC";
		SurfaceWord sw = new SurfaceWord(z);
		/****************************************/
		/*Change the set of Curves to SimpleCurves*/
		/****************************************/
		//If we need to construct curves with property that dependent on
		//the SurfaceWord, for example SimpleCurves, we have to create a subclass of Curve instead.
		
		//This is a subclass of PrimitiveCyclicWord, which is a subclass of CyclicWord
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		//SimpleCurve is a subclass of Curve, it is distinguished from Curve
		//in 2 accounts, the next() function will only return Curves that
		//are simple. Also it require the CyclicWord to be a subclass of PrimitiveCyclicWord
		SimpleCurve sc = new SimpleCurve(pdcw, sw);
		sc.initialize();
		System.out.println("First 20 SimpleCurves(in both direction) for surface "+sw);
		for(int i=0;i<120;i++){
			System.out.println(sc);
			sc = sc.next();
		}	
	}
}

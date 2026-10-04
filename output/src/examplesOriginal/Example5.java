package examplesOriginal;
import boundary.*;

//First 20 Curves with 3 intersections for surface

public class Example5 {
	public static void main(String[] args){
		String z = "abAB";
		SurfaceWord sw = new SurfaceWord(z);
		/****************************************/
		/*       CurveWithNIntersections        */
		/****************************************/
		//CurveWithNIntersections return curves with n intersections using next()
		CyclicWord cw = new CyclicWord(new int[1], sw.length());
		
		CurveWithNIntersections cwni = new CurveWithNIntersections(cw, sw, 3);
		cwni.initialize();
		System.out.println("First 20 Curves with 3 intersections for surface "+sw);
		for(int i=0;i<20;i++){
			System.out.println(cwni);
			cwni = cwni.next();
		}	
	}
}

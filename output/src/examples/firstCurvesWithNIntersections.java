package examples;
import boundary.*;

//First 20 Curves with 3 intersections for surface

public class firstCurvesWithNIntersections {
	public static void main(String[] args){
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		String dir ="dataApril2014x/";
		statics.CreateDirectory.create(dir);
		String filename =dir+ "kv_"+z;
		/****************************************/
		/*       CurveWithNIntersections        */
		/****************************************/
		//CurveWithNIntersections return curves with n intersections using next()
		CyclicWord cw = new CyclicWord(new int[1], sw.length());
		CurveWithNIntersections cwni = new CurveWithNIntersections(cw, sw, 1);
		cwni.initialize();
		System.out.println("First  Curves with 3 intersections for surface "+sw);
		for(int i=0;i<300;i++){
			System.out.println(cwni);
			cwni = cwni.next();
			statics.FileStuff.fileSentence(filename, cwni.toString());
		}	
	}
}

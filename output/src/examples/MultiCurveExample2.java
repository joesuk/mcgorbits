package examples;
import boundary.Curve;
import boundary.CurveList;
import boundary.Helpers;
import boundary.MultiCurve;
import boundary.MultiCurveIterator;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SimpleCurve;
import boundary.SurfaceWord;
import boundary.Word;


public class MultiCurveExample2 {
	public static void main(String[] args){
		//Prepare a list of simple curves
	//	int[] z = {0,2,1,3};
		//SurfaceWord sw = new SurfaceWord(z);
		String z = "abAB";
		SurfaceWord sw = new SurfaceWord(z);
		//MultiCurve lengths
		int len = 16  ;
		int intersections=1;
		int components =2;
		//Prepare a curvelist
		CurveList cl;
		cl = new CurveList(sw);
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		Word i1 = new Word(Word.fromString("abAB",z.length()));
		
		//SimpleCurve sc = new SimpleCurve(pdcw, sw);
		while(pdcw.length()<len+1){
			Curve w1 = new Curve(pdcw,sw);
			if(pdcw.compareTo(i1) !=0 & w1.selfIntersection() ==0 ){
				
				cl.add(pdcw);
				
			}
				
			pdcw = pdcw.next();
		}
		
		//END PREPARATION
		
		/****************************************/
		/*      MultiCurve With 2 Components    */
		/****************************************/
		
		for(int h = 2 ; h <= len; h++){
			countMulticurves(cl, h, components, sw, intersections );
		}
		
		
	}
	
	
	
	public static void countMulticurves(CurveList cl, int len , int numCurves, SurfaceWord sw, int intersections){
		MultiCurveIterator m = new MultiCurveIterator(cl, len, sw, Helpers.partition(len,numCurves));
		int count = 0;
		
		while(m.hasNext()){
			MultiCurve c = m.next();
			if(c.intersections()==intersections){
				count++;
			//	System.out.println(c);
			}
		}
		System.out.println(len+"\t "+count);
		
	}
}

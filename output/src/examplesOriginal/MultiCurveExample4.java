package examplesOriginal;
import boundary.*;


public class MultiCurveExample4 {
	public static void main(String[] args){
		//Prepare a list of simple curves
		int[] z = {0,2,1,3};
		SurfaceWord sw = new SurfaceWord(z);
		//MultiCurve lengths
		int n = 6;
		//Prepare a curvelist
		CurveList cl;
		cl = new CurveList(sw);
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		SimpleCurve sc = new SimpleCurve(pdcw, sw);
		while(sc.length()<n+1){
			cl.add(sc);
			sc = sc.next();
		}
		
		//END PREPARATION
		
		/***************************************************************/
		/*  MultiCurve With 2 Components with at most 1 intersection   */
		/***************************************************************/
		System.out.println("MultiCurve With 2 Components with at most k intersection");
		MultiCurveIterator m = new MultiCurveIterator(cl, n, sw, Helpers.partition(n,2));
		int count = 0;
		int[] stats = IntersectionStatistic.intersectionCount(m);
		for(int i=0;i<stats.length;i++){
			System.out.println(i+" "+stats[i]);
		}
		
	}
}

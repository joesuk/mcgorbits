package examplesOriginal;
import boundary.Curve;
import boundary.CurveList;
import boundary.CyclicWord;
import boundary.DoublyCyclicWord;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SimpleCurve;
import boundary.SurfaceWord;


public class CopyOfExample4 {
	public static void main(String[] args){
		String z = "abAB";
		
		SurfaceWord sw = new SurfaceWord(z);
		int[] a1 = {0};
		int[] aabAB1 ={0,0,2,1,3};
		CyclicWord a11 = new CyclicWord(a1,sw.length());
		Curve a = new Curve(a11,sw);
		
		CyclicWord aabAB11 = new CyclicWord(aabAB1,sw.length());
		Curve aabAB = new Curve(aabAB11,sw);
		
		
		int number = 200;
	 
		/****************************************/
		/*       Create a list of simple curves         */
		/****************************************/
		CurveList cl;
		//The next line is how the curve is constructed
		//CurveList is constructed by a SurfaceWord
		cl = new CurveList(sw);
		
		//create the SimpleCurves
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		SimpleCurve sc = new SimpleCurve(pdcw, sw);
		
		
		sc.initialize();
		
		
		//We can add curves into the CurveList.
		//Let's add all curves with length at most 4
		int count=0;
		while(count < number){
			cl.add(sc);
			sc = sc.next();
			//System.out.println(sc.toString());
			count++;
		}
		
		

	
		System.out.println(a.toString()+" "+aabAB.toString());
		for(int i=0;i<number ;i++){
	
			//System.out.println(cl.get(i).toString());

			if(cl.get(i).intersection(a)!=cl.get(i).intersection(aabAB)){ 
				
				System.out.println(sc.toString()+" "+sc.intersection(a)+" "+sc.intersection(aabAB));
			
			}
		}
		System.out.println("end");
	}
}

package examplesOriginal;
import boundary.Curve;
import boundary.*;


public class Example6 {
	public static void main(String[] args){
		String z = "abAB";
		SurfaceWord sw = new SurfaceWord(z);
		/****************************************/
		/*        Save curves to files          */
		/****************************************/
		CurveList cl;
		//The next line is how the curve is constructed
		//CurveList is constructed by a SurfaceWord
		cl = new CurveList(sw);
		
		//create the SimpleCurves
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		SimpleCurve sc = new SimpleCurve(pdcw, sw);
		
		//We can add curves into the CurveList.
		//Let's add all curves with length at most 4
		while(sc.length()<4){
			cl.add(sc);
			sc = sc.next();
		}
		System.out.println("Saving the CurveList");
		//We can save all CurveList for later use.
		//Of course you want to use a better filename
		cl.writeList("test.txt");
		
		
		/****************************************/
		/*  Iterate from pre-generated curves   */
		/****************************************/
		//Iterating though pre-generated curves a little different
		//We don't use the curve's next() function, we use the CurveList's next function
		
		//Recreate the CurveList, so we have an empty one
		cl = new CurveList(sw);
		//CurveList read from a file
		cl.readList("test.txt");
		System.out.println("Simple Curves of length at most 3 for surface "+sw.toString());
		System.out.println(cl);
		
		
		for(int i=0;i<cl.size();i++){
			for(int j=0;j<cl.size();j++){
				System.out.println("Bracket of "+cl.get(i)+" and " + cl.get(j) + " in surface "+ sw.toString()+" "+cl.get(i).bracket(cl.get(j).baseword));
				
				
			}
		}
	}
}

package examplesOriginal;
import java.util.ArrayList;

import boundary.*;

public class TwoMetrics {
	public static void main(String[] args){
		
		String z = "abAB";
		SurfaceWord sw = new SurfaceWord(z);
		double la=1;
		double lb=2;
		double lc=3;
		
		double[][] mat = hypLengthComp.LengthSamples.computeMatrix(la, lb, lc);
		
		double[][] mat2 = hypLengthComp.LengthSamples.computeMatrix(la, lb, lc);
		
		
		//of course sometimes you might want them side by side
		//Here is a more generalized method, that can take more than
		//one matrix
		double[][][] mats = new double[2][][];
		mats[0] = mat;
		mats[1] = mat2;
		//Record everything into length
	
		
		/****************************************/
		/*  Compute length in different metric  */
		/****************************************/
		//When using get, the CurveList output
		//a Curve with the current SurfaceWord of the CurveList
		
		//First create a CurveList
		PrimitiveDoublyCyclicWord sccc = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		//SimpleCurve sccc = new SimpleCurve(pdcw, sw);
		CurveList cl = new CurveList(sw);
		sccc.initialize();
		for(int i=0;i<50;i++){
			cl.add(sccc);
			sccc = sccc.next();
			//System.out.println(sccc.toString());
		}
	//	System.out.println("done");
		double[][] length = new double[cl.size()][mats.length];
		for(int j =0; j<mats.length;j++){
			cl.sw.setHyperbolicMatrix(mats[j]);
		    cl = cl.sortedCurveList(new SortByHyperbolicLength());
			for(int i=0;i<cl.size();i++){
				length[i][j] = cl.get(i).hyperbolicLength();
			}
		}
		
		for(int i=0;i<cl.size();i++){
			System.out.print(cl.get(i));
			for(int j=0;j<mats.length;j++){
				System.out.print("\t"+length[i][j]);
			}
			System.out.println();
		}
		
	}
}
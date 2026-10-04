package examplesOriginal;
import java.util.ArrayList;

import boundary.*;

public class CopyOfExample10 {
	public static void main(String[] args){
		
		
		int combLength=8;
		int simpleLength=7;
		
		String z = "abAB";
		SurfaceWord sw = new SurfaceWord(z);
		/****************************************/
		/*  Compute length in different metric  */
		/****************************************/
		//When using get, the CurveList output
		//a Curve with the current SurfaceWord of the CurveList
		
		//First create a CurveList
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		Curve sc = new Curve(pdcw, sw);
		CurveList cl = new CurveList(sw);
		sc.initialize();
		for(int i=0;i<50;i++){
			cl.add(sc);
			sc = sc.next();
		}
		
		
		//Need  a < 1 < b < c
		double aa = 0.2;
		double bb= 1.2;
		double cc= 3;
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		//Here is the first matrix
		double[][] mat = 
				{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,2*bb*cc*d,2*d,(bb+cc)*d}};
		//so here is the 2nd matrix, 
		aa=0.3;
		bb=1.4;
		cc=30;
		d = 1/(cc-bb);
		e =1/(1-aa);
		double[][] mat2 = 
				{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,2*bb*cc*d,2*d,(bb+cc)*d}};
		//Now one can assign a hyperbolic matrix to the SurfaceWord
		cl.sw.setHyperbolicMatrix(mat);
		
		
		
		String filename="UptoLength"+combLength+"simpleUPto"+simpleLength+".txt";
		String matrix = mat[0][0]+ "\t\t"+mat[0][1];
		statics.FileStuff.fileSentence(filename, matrix);
		matrix = mat[0][2]+ "\t	"+mat[0][3];
		statics.FileStuff.fileSentence(filename, matrix);
		statics.FileStuff.fileSentence(filename, " ");
		
		String mat2rix = mat2[0][0]+ "\t\t"+mat2[0][1];
		statics.FileStuff.fileSentence(filename, mat2rix);
		mat2rix = mat2[0][2]+ "\t	"+mat2[0][3];
		statics.FileStuff.fileSentence(filename, mat2rix);
		
		Curve c;
		//So now we have to compute the first
		while(cl.hasNext()){
			c = cl.next();
			System.out.println(c+" "+c.hyperbolicLength());
		}
		
		cl.reset();
		//and the second
		cl.sw.setHyperbolicMatrix(mat2);
		while(cl.hasNext()){
			c = cl.next();
			System.out.println(c+" "+c.hyperbolicLength());
		}
		
		//of course sometimes you might want them side by side
		//Here is a more generalized method, that can take more than
		//one matrix
		double[][][] mats = new double[2][][];
		mats[0] = mat;
		mats[1] = mat2;
		//Record everything into length
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
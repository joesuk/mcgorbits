package examplesOriginal;
import java.util.ArrayList;

import boundary.*;

public class Example8Copy2 {
	public static void main(String[] args){
		
		String z = "abAB";
		SurfaceWord sw = new SurfaceWord(z);
		
		
		
		//Need  a < 1 < b < c
		double aa = 0.2;
		double bb= 1.2;
		double cc= 3;
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
				{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,2*bb*cc*d,2*d,(bb+cc)*d}};
		
		
	
		sw.setHyperbolicMatrix(mat);
		
		
		int simpleLength=11;
		int combLength=11;
		/****************************************/
		/*        Save curves to files          */
		/****************************************/
		CurveList cl;
		//The next line is how the curve is constructed
		//CurveList is constructed by a SurfaceWord
		cl = new CurveList(sw);
		
		//create the SimpleCurves
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		//SimpleCurve sc = new SimpleCurve(pdcw, sw);
		
		//We can add curves into the CurveList.
		//Let's add all curves with length at most 4
		while(pdcw.length()<combLength){
			cl.add(pdcw);
			
			pdcw = pdcw.next();
		}
		
		cl = cl.sortedCurveList(new SortByHyperbolicLength());
		System.out.println("Saving the CurveList");
		/****************************************/
		/*        Save curves to files          */
		/****************************************/
		CurveList simplecl;
		//The next line is how the curve is constructed
		//CurveList is constructed by a SurfaceWord
		simplecl = new CurveList(sw);
		
		//create the SimpleCurves
		PrimitiveDoublyCyclicWord ppdcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		SimpleCurve sc = new SimpleCurve(ppdcw, sw);
		
		//We can add curves into the CurveList.
		//Let's add all curves with length at most 4
		while(sc.length()<simpleLength){
			simplecl.add(sc);
			sc = sc.next();
		}
		simplecl = simplecl.sortedCurveList(new SortByHyperbolicLength());
		System.out.println("Saving the CurveList");
		//We can save all CurveList for later use.
		//Of course you want to use a better filename
		
		
		
		
		
		/****************************************/
		/*  Curves that are in the same class   */
		/****************************************/
		//u and v are in the same equivalent class if
		//i(u,x) = i(v,x) for all simple curve x up to length m
		//Here is a program that print out all equivalent class 
		//m=10, containing more than 1 curve with length 5
		ArrayList<CurveList> a = Algorithms.equivalentClass(cl, simplecl);
	//	ArrayList<CurveList> a = Algorithms.equivalentClass(new SurfaceWord("abAB"), 10, 5);
		String filename="CurvesLast/classesUptoLength"+combLength+"simpleUPto"+simpleLength+".txt";
		String matrix = mat[0][0]+ "\t"+mat[0][1];
		statics.FileStuff.fileSentence(filename, matrix);
		matrix = mat[1][0]+ "\t\t	"+mat[1][1];
		statics.FileStuff.fileSentence(filename, matrix);
		for(int i=0;i<a.size();i++){
			if(a.get(i).size()>1){
				//One can actually print a CurveList.
				//Please refer to the output of this program
				System.out.println(a.get(i));
				String l = a.get(i).toString();
				System.out.println();
				statics.FileStuff.fileSentence(filename, " ");
				statics.FileStuff.fileSentence(filename, l);
				for(int h  = 0 ; h < a.get(i).size() ; h++){
				
					String legend =a.get(i).get(h)+" , "+a.get(i).get(h).hyperbolicLength()+" "+a.get(i).get(h).selfIntersection();
					System.out.println(legend);
					statics.FileStuff.fileSentence(filename, legend);
				}
			
								
	
				
				
			}
		}
		
	
	}
}
package examples;
import java.util.ArrayList;

import examples.Algorithms;

import boundary.*;

public class Example8 {
	public static void main(String[] args){
		
		String z = "abAB";
		SurfaceWord sw = new SurfaceWord(z);
		int wordsToTest = 8;
		int auxWords=8;
		/****************************************/
		/*        Save curves to files          */
		/****************************************/
		CurveList cl, cltest;
		//The next line is how the curve is constructed
		//CurveList is constructed by a SurfaceWord
		cl = new CurveList(sw);
		cltest = new CurveList(sw);
		//create the SimpleCurves
		PrimitiveDoublyCyclicWord pdcw = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		Curve sc = new Curve(pdcw, sw);
		
		//We can add curves into the CurveList.
		//Let's add all curves with length at most 4
		while(sc.length()<wordsToTest){
			cl.add(sc);
			sc = sc.next();
		}
		
		//create the SimpleCurves
		PrimitiveDoublyCyclicWord pdcwtest = new PrimitiveDoublyCyclicWord(new int[1], sw.length());
		Curve sctest = new Curve(pdcwtest, sw);
		
		//We can add curves into the CurveList.
		//Let's add all curves with length at most 4
		while(sctest.length()<auxWords){
			cltest.add(sctest);
			sctest = sctest.next();
		}
		/****************************************/
		/*  Curves that are in the same class   */
		/****************************************/
		//u and v are in the same equivalent class if
		//i(u,x) = i(v,x) for all simple curve x up to length m
		//Here is a program that print out all equivalent class 
		//m=10, containing more than 1 curve with length 5
		ArrayList<CurveList> a = Algorithms.equivalentClass( cl, cltest);
		for(int i=0;i<a.size();i++){
			if(a.get(i).size()>1){
				//One can actually print a CurveList.
				//Please refer to the output of this program
				System.out.println(a.get(i));
			}
		}
	}
}

package examplesOriginal;
import java.util.ArrayList;

import boundary.*;

public class Example8Copy {
	public static void main(String[] args){
		
		String z = "aAbB";
		SurfaceWord sw = new SurfaceWord(z);
		//Need 0< a < 1 < b < c Surface=pants
		double aa = 0.2;
		double bb= 1.2;
		double cc= 2;
		double d = 1/(cc-bb);
		double e =1/(1-aa);
		double[][] mat = 
				{{(1+aa)*e,2*aa*e,2*e,(1+aa)*e},
				{(bb+cc)*d,2*bb*cc*d,2*d,(bb+cc)*d}};
		sw.setHyperbolicMatrix(mat);
		
		/****************************************/
		/*  Curves that are in the same class   */
		/****************************************/
		//u and v are in the same equivalent class if
		//i(u,x) = i(v,x) for all simple curve x up to length m
		//Here is a program that print out all equivalent class 
		//m=10, containing more than 1 curve with length 5
		int m=3;
		int j=6;
		ArrayList<CurveList> a = Algorithms.equivalentClass(sw, j, m);
		for(int i=0;i<a.size();i++){
			if(a.get(i).size()>1){
				//One can actually print a CurveList.
				//Please refer to the output of this program
				System.out.println(a.get(i));
			}
		}
	}
}

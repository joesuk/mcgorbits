package examples;
import java.util.ArrayList;

import boundary.*;

public class SimpleInterClasses {
	
	public static void main(String[] args){
		int k=10; //length of elements in the classes
		int m= 10; //length of simple closed curves used to test.
		String filename = "siClasses"+ m +"_"+ k;
		/****************************************/
		/*  Curves that are in the same class   */
		/****************************************/
		//u and v are in the same equivalent class if
		//i(u,x) = i(v,x) for all simple curve x up to length m
		//Here is a program that print out all equivalent class 
		//m=10, containing more than 1 curve with length 
		ArrayList<CurveList> a = Algorithms.equivalentClass(new SurfaceWord("abAB"), k, m);
		for(int i=0;i<a.size();i++){
			if(a.get(i).size()>1){
				//One can actually print a CurveList.
				//Please refer to the output of this program
				System.out.println(a.get(i));
				statics.FileStuff.fileSentence(filename, a.get(i).toString());
			}
		}
	}
}

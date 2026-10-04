package a_goldmanTuraev;

import java.util.Scanner;

import boundaryBialgebra.*;

public class BracketOfTwoCurvesJar {

	/**
	 * @param args
	 */
	public static void main(String[] args) {  
		
		//Surface word
		String sw = "abAB";
		CL cl = new CL(sw);
		
		//strings for curves: Should be cyclically reduced
	//	String h1="bbaaBAb";
	//	String h2="aBBBabb";
		
		System.out.println("Enter the first word: ");
		Scanner scanner = new Scanner(System.in);
		String h1 = scanner.nextLine();
	//	System.out.println("Your username is " + username);
		
		System.out.println("Enter the first word: ");
	//	Scanner scanner = new Scanner(System.in);
		String h2 = scanner.nextLine();
		int[] s1 = cl.toNumber(h1);
		int[] s2 = cl.toNumber(h2);
		
		BTermsNumber bt1 =cl.bracket(s1, s2);
		bt1.simplify();
	   
		
		System.out.println("The words are "+h1+" and "+h2);
		System.out.println("\nThe number of terms of the bracket is "+bt1.addAbsVCoef());
		System.out.println("\nThe intersection of the words is "+cl.si(h1,h2)+"\n");

		System.out.println(bt1.toString(cl));
		
		
		
	
		
		System.out.println("The self-intersection  of "+h1 +" in surface "+ sw +" is "+ cl.si(h1));
			
	

		System.out.println("(*"+h1+", "+h2);
		System.out.println("\n\nThe number of terms: "+bt1.addAbsVCoef());
	
		System.out.println("*)");
		System.out.println("theList="+bt1.toMathematica(cl)+";");

		System.out.println("Classes[theList]");

	}

}

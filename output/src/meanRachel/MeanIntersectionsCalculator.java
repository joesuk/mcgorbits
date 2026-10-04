package meanRachel;
import java.util.*;

import meanRachel.*;
import boundary.Curve;
import boundary.PrimitiveCyclicWord;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;

public class MeanIntersectionsCalculator {
	
	static int[] surfacePortal;
	static int[] curveRep;

	public static void main(String[] args) {
		
		// variables
		String z = "abAB"; //draw this going clockwise
		SurfaceWord sw = new SurfaceWord(z);
		
		
		
		
		PrimitiveDoublyCyclicWord cw = new PrimitiveDoublyCyclicWord(new int[1] , sw.length());
		Curve curve = new Curve(cw, sw);
		curve.initialize();
		while(curve.length()<15){
			String legend=curve.toString()+"\t"+computeMean(curve.toString(),z)+"\t"+curve.selfIntersection();
			System.out.println(legend);
			statics.FileStuff.fileSentence("testNew"+z, legend);
			curve=curve.next();
			
		}
		
	}
	
		private static String computeMean(String c, String z){

			
			// code
			surfacePortal = new int[z.length()];
			for (int i = 0; i < z.length(); i++) {
				int letter = z.charAt(i);
				if (letter < 96) {
					char search = (char) (letter + 32);
					for (int j = 0; j < z.length(); j++) {
						if (z.charAt(j) == search) {
							surfacePortal[i] = j;
							break;
						}
					}
				}
				if (letter > 96) {
					char search = (char) (letter - 32);
					for (int j = 0; j < z.length(); j++) {
						if (z.charAt(j) == search) {
							surfacePortal[i] = j;
							break;
						}
					}
				}
			}
			
			curveRep = new int[c.length()];
			for (int i = 0; i < c.length(); i++) {
				for (int j = 0; j < z.length(); j++) {
					if (c.charAt(i) == z.charAt(j)) {
						curveRep[i] = j;
					}
				}
			}
			
			// System.out.println(Arrays.toString(curveRep));
			
			Fraction[][] probabilities = new Fraction[c.length()][c.length()]; //[start][end]
			
			for (int start = 0; start < c.length(); start++) {
				for (int end = 0; end < c.length(); end++) {
					int left1 = left(start, (start+1)%c.length());
					int right1 = z.length() - 2 - left1;
					int left2 = left(end, (end+1)%c.length());
					int right2 = z.length() - 2 - left2;
					// System.out.println("start: " + start + "; end: " + end + " " + left1 + " " + right1 + " " + left2 + " " + right2);
					probabilities[start][end] = new Fraction((left1*right2) + (left2*right1),1);
				}
			}
			for (int i = 0; i < c.length(); i++) {
				for (int j = 0; j < c.length(); j++) {
					probabilities[i][j].multiply(1, pow(z.length()-1, distanceBetween(i,j)));
				}
			}
			
			/*
			for (int i = 0; i < c.length(); i++) {
				for (int j = 0; j < c.length(); j++) {
					System.out.print(probabilities[i][j].printStr() + "\t");
				}
				System.out.println();
			}
			*/
			
			Fraction total = new Fraction();
			for (int i = 0; i < c.length(); i++) {
				for (int j = 0; j < c.length(); j++) {
					total.add(probabilities[i][j]);
				}
			}
			
			total.multiply(1, (z.length())*(z.length()-1));
			total.multiply(pow(z.length()-1, c.length()), pow(z.length()-1, c.length())-1);
			total.multiply(2, 1);
			
			for (int i = 0; i < c.length(); i++) {
				int left = left(i, (i+1)%c.length());
				int right = z.length() - 2 - left;
				total.add(2*left*right, z.length()*(z.length()-1));
			}
			
		//	System.out.println(total.printStr());
			return total.printStr();
			
		}
		
		private static int distanceBetween(int enter, int exit) { // on curve
			if (enter == exit) {
				return curveRep.length;
			}
			if (exit > enter) {
				return exit-enter;
			}
			return exit+curveRep.length-enter;
		}
		
		
		
		

	
	private static int left(int enter, int exit) {
		int enter2 = curveRep[enter];
		int exit2 = curveRep[exit];
		int goin = surfacePortal[enter2];
		if (exit2 > goin) {
			return (exit2 - goin - 1);
		}
		return (exit2 + surfacePortal.length - goin - 1);
	}
	
	
	private static int right(int enter, int exit) {
		return (surfacePortal.length - 2 - left(enter, exit));
	}
	
	
	private static long pow(int base, int exp) {
		int rep = 1;
		for (int i = 1; i <= exp; i++) {
			rep *= base;
		}
		return rep;
	}

}

package a_equivantCurves;

import boundary.StudyPairsEquivCurves;
import boundary.SurfaceWord;

public class DriverEquivCurvesProportions {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		String z="aAbB"; 
		SurfaceWord sw = new SurfaceWord(z);
		String w1="aabaaBAAbaBabAB";
		String w2="aabaBAAbaBaabAB";
		System.out.println(sw.base+ w1.length()+"\n");
	       String dir ="data_2014/";
	       statics.CreateDirectory.create(dir);
	      System.out.println(sw.base+ w1.length()+"\n");
		   StudyPairsEquivCurves.proportions(sw, dir+z+"_"+w1+"_"+w2+"_"+"",20,w1,w2);
		   sw = new SurfaceWord("abAB");
		  // StudyPairsEquivCurves.proportions(sw, dir+z+"_"+w1+"_"+w2+"_"+"",20,w1,w2);
		   
		
	}

}

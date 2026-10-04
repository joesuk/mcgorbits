package a_torusPantsHL;

import java.awt.List;
import java.util.ArrayList;

import a_hypLengComp.Histogram;
import boundary.Curve;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;
import boundary.Word;

public class DriverRandom {

	public static void main(String[] args) {

		String dir="*RandomCurvesTorus/";
		 
		statics.CreateDirectory.create(dir);
	
	
		SurfaceWord sw = new SurfaceWord("abAB");

		int h=0;
		while( h <6){
		Curve c = Histogram.randomWord(25, sw);
		if(c.selfIntersection()<20){
			String sentence =c.toString()+"\t"+c.selfIntersection();
			System.out.println(sentence);
			statics.FileStuff.fileSentence(dir+"wordsSI_"+c.selfIntersection(), sentence);
			if(c.selfIntersection()==3){h++;}
		}
		}
//			
			

	}
	public static  SurfaceWord torusSmall(){
		SurfaceWord sw = new SurfaceWord("abAB");
		
		double[][] mmSmall =  {{0.001,7.600905,0.000956164},{0.0004999997298,0,0,2000.001081},{1.0000005000,0.0010000001667,0.0010000001667,1.0000005000}};
		double[][] mm={mmSmall[1],mmSmall[2]};
		sw.setHyperbolicMatrix(mm);
		return sw;
	}
	
	
	public static SurfaceWord torusLargeBd(){
		SurfaceWord sw = new SurfaceWord("abAB");
		double[][] mmSmall ={{15,11,6},{0.00001567462998,0,0,63797.35924},{0.009584057212,1770035505,177.0035505,      
		63.269101612 *Math.pow(10,6)}};
		double[][] mm={mmSmall[1],mmSmall[2]};
		sw.setHyperbolicMatrix(mm);
		return sw;
	}

}
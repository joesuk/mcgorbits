package a_equivantCurves;
import java.util.ArrayList;
import boundary.*;

public class StudyALengthEquivClass {
	public static void main(String[] args){


		int exponentBase  = 10;
		int exponent =  5			;
		int top = 5;
		double constant = 0.5;
		String s1="aaabaBaabaBAAbAB";//this is the curve of the paper
		//String s1="aaabaaBAbAABabaB";
		
		double error =Math.pow(exponentBase, -exponent);
		CurveList al=check0(0.1,1,10,error,s1);
	
		System.out.println(al.toString()+"XXXX");

		String dir ="EC2014_PaperClassq/";
		statics.CreateDirectory.create(dir);
		String filename=dir+s1+"newtestXX";
		for(int sa = 0; sa < top ; sa++  ){
			for(int sb = sa ; sb <top ; sb++){
				for(int sc = sb ; sc <top ; sc ++){
					System.out.println(sa+" "+sb+" "+sc);
					al = checkList( 0.1+sa*constant ,0.1 + sb*constant, 0.1 + sc*constant, error,s1,al,dir );
				//	System.out.println(al.toString()+"XXXX");
					System.out.println(al.toString());		
					System.out.println(sa+" "+" "+sb+" "+sc+" **************\n\n");
				}
			}
			//statics.FileStuff.fileSentence(filename, al.toString());
		}
		FindLengthEquivClassesF3.printListOfCurvesMathematica(al,filename+"FINAL");
		
		//	check(0.1,0.10,3,Math.pow(exponentBase, -7),"aBAAb");

	}


	public static CurveList checkList( double la, double lb, double lc,double error, String s1, CurveList al,String dir){
		
		SurfaceWord swPants1 = new SurfaceWord("aAbB");
		double[][] mat = hypLengthComp.LengthSamples.computeMatrix(la, lb, lc);
		swPants1.setHyperbolicMatrix(mat);
		Word ww1 = new Word( Word.fromString(s1, swPants1.length()));
		Curve testCurve = new Curve(ww1, swPants1);
		CurveList listOfCurves = new CurveList(swPants1);

		String sentence = testCurve.toString()+" \t IntialCurve SI "+ testCurve.selfIntersection()+"\t"+testCurve.hyperbolicLength()+"\n";
		statics.FileStuff.fileSentence(s1+error,sentence+"\n");
		System.out.println(sentence);
		

		//We add to the curve-list cl ALL curve-words of combinatorial length CombLength 

		for(int h=0; h < al.size() ; h++){
			String stest=al.get(h).toString();
			Word wwtest = new Word( Word.fromString(stest, swPants1.length()));
			Curve ctest = new Curve(wwtest, swPants1);
			
		//	System.out.println(ctest.toString()+" curve "+ctest.hyperbolicLength() +" "+testCurve.hyperbolicLength());
			if( Math.abs(ctest.hyperbolicLength() - testCurve.hyperbolicLength()) < error ){

				listOfCurves.add(al.get(h));
				//sentence = ctest.toString()+" \t"+ ctest.selfIntersection()+"\t"+ctest.hyperbolicLength();
			//	statics.FileStuff.fileSentence(dir+s1+error,sentence+"\n");
			//	System.out.println(sentence);
			}
		}

		return listOfCurves;
	

	}



	public static CurveList check0( double la, double lb, double lc,double error, String s1){

		SurfaceWord swPants1 = new SurfaceWord("aAbB");
		double[][] mat = hypLengthComp.LengthSamples.computeMatrix(la, lb, lc);
		swPants1.setHyperbolicMatrix(mat);

		Word ww1 = new Word( Word.fromString(s1, swPants1.length()));
		Curve testingCurve = new Curve(ww1, swPants1);

		CurveList listOfCurves = new CurveList(swPants1);

		String sentence = testingCurve.toString()+" \t SI "+ testingCurve.selfIntersection()+"\t HL "+testingCurve.hyperbolicLength()+"\n";
		//statics.FileStuff.fileSentence(s1+error,sentence+"\n");
		System.out.println(sentence);
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[s1.length()] , swPants1.length());
		//	PrimitiveDoublyPalCyclicWord dcw = new PrimitiveDoublyPalCyclicWord(new int[s1.length()] , swPants1.length());
		Curve c = new Curve(dcw, swPants1);
		c.initialize();

		//We add to the curve-list cl ALL curve-words of combinatorial length CombLength 

		while(c.length()==s1.length()){
			if( Math.abs(c.hyperbolicLength() - testingCurve.hyperbolicLength()) < error ){

				listOfCurves.add(c);
				sentence = c.toString()+" \t"+ c.selfIntersection()+"\t"+c.hyperbolicLength();
			//	statics.FileStuff.fileSentence(s1+error,sentence+"\n");
			//	System.out.println(sentence);
			}
			c = c.next();
		}

		return listOfCurves;
	}
}

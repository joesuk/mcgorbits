package a_equivantCurves;
import java.util.ArrayList;
import boundary.*;

public class StudyALengthEquivClassTrace {
	public static void main(String[] args){


		int exponentBase  = 10;
		int exponent =  3			;
		int top = 5;
	
//		String s1="aaabaBaabaBAAbAB";//this is the curve of the paper
		String s1="aaabaaBAbAABabaB";
		double[] m1 = {2,1,1,1};
		double[] m2 = {10,0,0,0.1};
		double error =Math.pow(exponentBase, -exponent);
		String dir ="EC2014_PaperExpMathClass/";
		statics.CreateDirectory.create(dir);
		String filename=dir+s1+"newtestXX";
		
		CurveList al=check0tracePal(m1,m2,error,s1);
		 al= checkListTrace(m2,m1, error,s1,al,dir );
	
		System.out.println(al.toString()+"XXXX");

		
		for(int sa = 1; sa < top ; sa++  ){
			for(int sb = 1 ; sb <top ; sb++){
				for(int sc = 1 ; sc <top ; sc ++){
					for(int su =1 ; su < top ; su++){
					System.out.println(sa+" "+sb+" "+sc+" "+su);
					double sd = (double)(1+sb*sc)/(double)sa;
		 			double[] m11= {sa,sb,sc,sd};
		 			double[] m22={sa,su,su,(1+su*su)/(double)sa};
					al = checkListTrace(m11,m22, error,s1,al,dir );
					al = checkListTrace(m22,m11, error,s1,al,dir );
				//	System.out.println(al.toString()+"XXXX");
					System.out.println(al.toString());		
					System.out.println(sa+" "+" "+sb+" "+sc+" **************\n\n");
				}
				}
			}
			//statics.FileStuff.fileSentence(filename, al.toString());
		}
		FindLengthEquivClassesF3.printListOfCurvesMathematica(al,filename+"FINAL_Pal");
		
		//	check(0.1,0.10,3,Math.pow(exponentBase, -7),"aBAAb");

	}


	public static CurveList checkListTrace( double[] m1, double[] m2,double error, String s1, CurveList al,String dir){
		
		SurfaceWord swPants1 = new SurfaceWord("aAbB");
		double[][] mat = {m1,m2};
		swPants1.setHyperbolicMatrix(mat);
		Word ww1 = new Word( Word.fromString(s1, swPants1.length()));
		Curve testCurve = new Curve(ww1, swPants1);
		CurveList listOfCurves = new CurveList(swPants1);

		String sentence = testCurve.toString()+" \t IntialCurve SI "+ testCurve.selfIntersection()+"\t"+testCurve.hyperbolicTrace()+"\n";
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



	public static CurveList check0trace( double[] m1, double[] m2,double error, String s1){

		SurfaceWord swPants1 = new SurfaceWord("aAbB");
		double[][] mat ={m1,m2};
		swPants1.setHyperbolicMatrix(mat);

		Word ww1 = new Word( Word.fromString(s1, swPants1.length()));
		Curve testingCurve = new Curve(ww1, swPants1);

		CurveList listOfCurves = new CurveList(swPants1);

		String sentence = testingCurve.toString()+" \t SI "+ testingCurve.selfIntersection()+"\t Trace "+testingCurve.hyperbolicTrace()+"\n";
		//statics.FileStuff.fileSentence(s1+error,sentence+"\n");
		System.out.println(sentence);
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[s1.length()] , swPants1.length());
		//	PrimitiveDoublyPalCyclicWord dcw = new PrimitiveDoublyPalCyclicWord(new int[s1.length()] , swPants1.length());
		Curve c = new Curve(dcw, swPants1);
		c.initialize();

		//We add to the curve-list cl ALL curve-words of combinatorial length CombLength 

		while(c.length()==s1.length()){
			if( Math.abs(c.hyperbolicTrace() - testingCurve.hyperbolicTrace()) < error ){

				listOfCurves.add(c);
			//	sentence = c.toString()+" \t"+ c.selfIntersection()+"\t"+c.hyperbolicLength();
			//	statics.FileStuff.fileSentence(s1+error,sentence+"\n");
			///	System.out.println(sentence);
			}
			c = c.next();
		}

		return listOfCurves;
	}
	
	public static CurveList check0tracePal( double[] m1, double[] m2,double error, String s1){

		SurfaceWord swPants1 = new SurfaceWord("aAbB");
		double[][] mat ={m1,m2};
		swPants1.setHyperbolicMatrix(mat);

		Word ww1 = new Word( Word.fromString(s1, swPants1.length()));
		Curve testingCurve = new Curve(ww1, swPants1);

		CurveList listOfCurves = new CurveList(swPants1);

		String sentence = testingCurve.toString()+" \t SI "+ testingCurve.selfIntersection()+"\t Trace "+testingCurve.hyperbolicTrace()+"\n";
		//statics.FileStuff.fileSentence(s1+error,sentence+"\n");
		System.out.println(sentence);
		PrimitiveDoublyPalCyclicWord dcw = new PrimitiveDoublyPalCyclicWord(new int[s1.length()] , swPants1.length());
		//	PrimitiveDoublyPalCyclicWord dcw = new PrimitiveDoublyPalCyclicWord(new int[s1.length()] , swPants1.length());
		Curve c = new Curve(dcw, swPants1);
		c.initialize();

		//We add to the curve-list cl ALL curve-words of combinatorial length CombLength 

		while(c.length()==s1.length()){
			if( Math.abs(c.hyperbolicTrace() - testingCurve.hyperbolicTrace()) < error ){

				listOfCurves.add(c);
			//	sentence = c.toString()+" \t"+ c.selfIntersection()+"\t"+c.hyperbolicLength();
			//	statics.FileStuff.fileSentence(s1+error,sentence+"\n");
			///	System.out.println(sentence);
			}
			c = c.next();
		}

		return listOfCurves;
	}
}

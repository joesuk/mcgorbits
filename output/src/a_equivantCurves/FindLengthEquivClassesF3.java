package a_equivantCurves;
import java.util.ArrayList;

import boundary.*;

public class FindLengthEquivClassesF3 {



    public static boolean checkIf3letters(String s1){
    	char a0= s1.charAt(0);
    	return false;
    	
    	
    }

	public static void printListOfCurvesSI(CurveList cl, String filename){
		SurfaceWord swT12 = new SurfaceWord("abcABC");
		SurfaceWord swT04 = new SurfaceWord("aAbBcC");
		String sent = "\n\nClass size:  " + cl.size()+"\nWord length first word: " +cl.get(0).length()+"\n";
		statics.FileStuff.fileSentence(filename, sent);
		System.out.println(sent);
		for(int h = 0 ; h < cl.size() ; h++){
			Curve cpants = new Curve(cl.get(h).baseword, swT12);
			Curve ctorus = new Curve(cl.get(h).baseword, swT04);
			String sentence = cl.get(h).toString()+"\t Pants: "+cpants.selfIntersection()+"\t Torus: "+ctorus.selfIntersection();
			statics.FileStuff.fileSentence(filename, sentence);
			System.out.println(sentence);
		}
	}
	
	public static void printListofListMathematica(ArrayList<CurveList> al, String filename){
		for(int u = 0 ; u < al.size() ; u++){
			//	System.out.println(al.get(u).size());
			printListOfCurvesMathematica(al.get(u),filename);


		}

	}
//this is not to change the filename.
	public static void printListOfCurvesMathematica(CurveList cl, String filename, boolean change){
	//	filename = filename+"_size_"+cl.size()+"_";
		String sent = "\n\nClass size:  " + cl.size()+"\tWord length: " +cl.get(0).length()+";\n";
		statics.FileStuff.fileSentence(filename, sent);
		String sentence = "theList={";
		System.out.println(sent);
		for(int h = 0 ; h < cl.size() -1; h++){
			sentence=sentence+"\""+cl.get(h).toString()+"\",";
			
		}
		sentence=sentence+"\""+cl.get(cl.size()-1).toString()+"\"";
		sentence=sentence+"}\nTest[theList,1]";
		statics.FileStuff.fileSentence(filename, sentence);
		System.out.println(sentence);
	}
	public static void printListOfCurvesMathematica(CurveList cl, String filename){
		String sentence ="";
		if(cl.size()==0) sentence="no class";
		else {filename = filename+"_size_"+cl.size()+"_";
		String sent= "\n\nClass size:  " + cl.size()+"\tWord length: " +cl.get(0).length()+";\n";
		statics.FileStuff.fileSentence(filename, sent);
		 sentence = "theList={";
		System.out.println(sent);
		for(int h = 0 ; h < cl.size() -1; h++){
			sentence=sentence+"\""+cl.get(h).toString()+"\",";
			
		}
		sentence=sentence+"\""+cl.get(cl.size()-1).toString()+"\"";
		sentence=sentence+"}\nTest3[theList,1]";
		
		}
		statics.FileStuff.fileSentence(filename, sentence);
		System.out.println(sentence);
	}
	

	public static void printListSI(ArrayList<CurveList> al, String filename){
		for(int u = 0 ; u < al.size() ; u++){
			//	System.out.println(al.get(u).size());
			printListOfCurvesSI(al.get(u),filename);


		}

	}



	public static void printListDifferentSI(ArrayList<CurveList> al, String filename){
		SurfaceWord swTorus = new SurfaceWord("abcABC");
		SurfaceWord swPants = new SurfaceWord("aAbBcC");
		for(int u = 0 ; u < al.size() ; u++){
			CurveList theList =al.get(u);
			//	CurveList answer = new CurveList(swPants);
			Curve curve0Pants = al.get(u).get(0);
			Curve curve0Torus = new Curve(curve0Pants.baseword,swTorus);
			for(int h =0 ; h < theList.size() ; h++){
				Curve curvePants = al.get(u).get(h);
				Curve curveTorus = new Curve(curvePants.baseword,swTorus);
				boolean test = false;
				if(curvePants.selfIntersection() != curve0Pants.selfIntersection() 
						|| curveTorus.selfIntersection()!= curve0Torus.selfIntersection()){
					printListOfCurvesSI(theList,filename+"_"+theList.size());
					printListOfCurvesMathematica(al.get(u),filename+"_"+theList.size(),false);
					if(curvePants.selfIntersection() != curve0Pants.selfIntersection() 
							& curveTorus.selfIntersection()!= curve0Torus.selfIntersection()){
						printListOfCurvesSI(theList,filename+"Both"+"_"+theList.size());
						printListOfCurvesMathematica(al.get(u),filename+"Both"+"_"+theList.size(),false);
					}

					
					h=theList.size()+10;

				}
			}






		}

	}


	




	public static ArrayList<CurveList> checkListTrace0(int wordLength, double error, double[] m1, double[] m2, double[] m3, int sizeClass, String surfWord){
		
		//	System.out.println(sentence);
		SurfaceWord sw = new SurfaceWord(surfWord);
		double[][] mat1 = {m1,m2,m3};
		sw.setHyperbolicMatrix(mat1);

		CurveList listOfCurves = new CurveList(sw);
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[wordLength] , sw.length());

		Curve c = new Curve(dcw, sw);
		c.initialize();


		//We add to the curve-list listOfCurves1 ALL curve-words of combinatorial length CombLength 
		while(c.length()==wordLength){
			listOfCurves.add(c);
			//System.out.println(c.toString()+"\t"+c.hyperbolicTrace());
			c = c.next();
		}
		//Sort the list
		listOfCurves = listOfCurves.sortedCurveList(new SortByHyperbolicTrace());
				ArrayList<CurveList> al = Algorithms.equivalentClassByTrace(listOfCurves, error);		
		ArrayList<CurveList> aux = new ArrayList<CurveList>();

		for(int u =0 ; u < al.size() ; u++){
			if(al.get(u).size()>sizeClass){	
				
				aux.add(al.get(u));
			}

		}

		return aux;

	}


	
	
	public static ArrayList<CurveList> checkListTrace( double error, double[] m1, double[] m2,double m3[], ArrayList<CurveList> listOfClasses, int sizeClass, String surfWord){


		SurfaceWord sw = new SurfaceWord(surfWord);
		double[][] mat1 = {m1,m2,m3};
		sw.setHyperbolicMatrix(mat1);


		//to store the answer
		ArrayList<CurveList>  answer  =  new ArrayList<CurveList>();

		for(int h = 0 ; h < listOfClasses.size() ; h++){
			CurveList curvelist = listOfClasses.get(h);

			if(curvelist.size()>sizeClass){

				CurveList  aux  =  new CurveList(sw);
				//get every element in the class curvelist and look at it with the new metric.
				for(int h1 = 0 ; h1 < curvelist.size() ; h1++){
					Curve cc3 = new Curve(curvelist.get(h1).baseword, sw);
			//		System.out.println(cc3.toString());
					aux.add(cc3);					
				}
				aux = aux.sortedCurveList(new SortByHyperbolicTrace());

				ArrayList<CurveList> classesAux=Algorithms.equivalentClassByTrace(aux, error);

				//printListSI(classesAux,"test", swPants);

				for(int s =0 ; s <classesAux.size() ; s++ ){

					if(classesAux.get(s).size()>sizeClass){

						//	printListOfCurvesSILength(classesAux.get(s), "test", swPants);
					//	System.out.println(classesAux.get(s).toString());
						answer.add(classesAux.get(s));

					}
				}
			}
		}
		//printListSI(answer,"test", swPants);
		return answer;

	}




}






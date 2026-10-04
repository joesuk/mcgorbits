package a_equivantCurves;
import java.util.ArrayList;

import boundary.*;

public class FindLengthEquivClassesF2 {





	public static void printListOfCurvesSI(CurveList cl, String filename){
		SurfaceWord swPants = new SurfaceWord("aAbB");
		SurfaceWord swTorus = new SurfaceWord("abAB");
		String sent = "\n\nClass size:  " + cl.size()+"\nWord length first word: " +cl.get(0).length()+"\n";
		statics.FileStuff.fileSentence(filename, sent);
		System.out.println(sent);
		for(int h = 0 ; h < cl.size() ; h++){
			Curve cpants = new Curve(cl.get(h).baseword, swPants);
			Curve ctorus = new Curve(cl.get(h).baseword, swTorus);
			String sentence = cl.get(h).toString()+"\t Pants: "+cpants.selfIntersection()+"\t Torus: "+ctorus.selfIntersection();
			statics.FileStuff.fileSentence(filename, sentence);
			System.out.println(sentence);
		}
	}
	
	public static void printListofListMathematica(ArrayList<CurveList> al, String filename){
		int maxClasses = 50000;
		int[] sizes = new int[maxClasses];
		for(int u = 0 ; u < al.size() ; u++){
			//	System.out.println(al.get(u).size());
			sizes[al.get(u).size()]++;
		//	statics.FileStuff.fileSentence(filename, "Number of classes "+al.size());
			printListOfCurvesMathematica(al.get(u),filename+"ClassSize_"+al.get(u).size());


		}
		for(int h = 1 ; h<maxClasses; h++ ){
			if(sizes[h]>0){
		statics.FileStuff.fileSentence(filename+"size_"+h, "\nNumber of classes "+sizes[h]);
			}
		}
	}
//this is not to change the filename.
	public static void printListOfCurvesMathematica(CurveList cl, String filename, boolean change){
	//	filename = filename+"_size_"+cl.size()+"_";
	//	String sent = "\n\nClass size:  " + cl.size()+"\tWord length: " +cl.get(0).length()+";\n";
		//statics.FileStuff.fileSentence(filename, sent);
		String sentence = "theList={";
	//	System.out.println(sent);
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
	//	if(cl.size()==0) sentence="no class";
	//	else {filename = filename+"_size_"+cl.size()+"_";
		////String sent= "\n\nClass size:  " + cl.size()+"\tWord length: " +cl.get(0).length()+";\n";
		//statics.FileStuff.fileSentence(filename, sent);
		//System.out.println(sent);
		 sentence = "theList={";
		
		for(int h = 0 ; h < cl.size() -1; h++){
			sentence=sentence+"\""+cl.get(h).toString()+"\",";
			
		}
		sentence=sentence+"\""+cl.get(cl.size()-1).toString()+"\"";
		sentence=sentence+"}\nTest[theList,1]";
		
		//}
		statics.FileStuff.fileSentence(filename, sentence);
		System.out.println(sentence);
	}
	public static void printListOfCurvesSILength(CurveList cl, String filename, SurfaceWord swPants){
		String sent = "\n\nClass size:  " + cl.size()+"\nWord length: " +cl.get(0).length()+"\n";
		statics.FileStuff.fileSentence(filename, sent);
		System.out.println(sent);
		SurfaceWord swTorus = new SurfaceWord("abAB");
		for(int h = 0 ; h < cl.size() ; h++){
			Curve cpants = new Curve(cl.get(h).baseword, swPants);
			Curve ctorus = new Curve(cl.get(h).baseword, swTorus);
			String sentence = cl.get(h).toString()+"\t Pants: "+cpants.selfIntersection()+"\t Torus: "+ctorus.selfIntersection()+ "\t h-length: "+cpants.hyperbolicLength();
			statics.FileStuff.fileSentence(filename, sentence);
			System.out.println(sentence);
		}
	}

	public static void printListSI(ArrayList<CurveList> al, String filename){
		for(int u = 0 ; u < al.size() ; u++){
			//	System.out.println(al.get(u).size());
			printListOfCurvesSI(al.get(u),filename);


		}

	}

	public static void printListOfListSILength(ArrayList<CurveList> al, String filename,SurfaceWord swPants){
		
		for(int u = 0 ; u < al.size() ; u++){
			//System.out.println(al.get(u).size());
			
			printListOfCurvesSILength(al.get(u),filename+al.get(u).size(), swPants);


		}

	}

	public static void printListDifferentSI(ArrayList<CurveList> al, String filename){
		SurfaceWord swTorus = new SurfaceWord("abAB");
		SurfaceWord swPants = new SurfaceWord("aAbB");
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


	public static void printListDifferentSI(ArrayList<CurveList> al, String filename, SurfaceWord swPants){
		SurfaceWord swTorus = new SurfaceWord("abAB");
		//		SurfaceWord swPants = new SurfaceWord("aAbB");
		for(int u = 0 ; u < al.size() ; u++){
			CurveList theList =al.get(u);
			//	CurveList answer = new CurveList(swPants);
			Curve curve0Pants = al.get(u).get(0);
			Curve curve0Torus = new Curve(curve0Pants.baseword,swTorus);
			for(int h =0 ; h < theList.size() ; h++){
				Curve curvePants = al.get(u).get(h);
				Curve curveTorus = new Curve(curvePants.baseword,swTorus);
				if(curvePants.selfIntersection() != curve0Pants.selfIntersection() 
						|| curveTorus.selfIntersection()!= curve0Torus.selfIntersection()){
					printListOfCurvesSILength(theList,filename,swPants);
					h=theList.size()+10;

				}
			}
		}}




	public static ArrayList<CurveList> checkList0(int wordLength, double error, double la, double lb, double lc, int sizeClass){
		
		//	System.out.println(sentence);
		SurfaceWord swPants = new SurfaceWord("aAbB");
		double[][] mat1 = hypLengthComp.LengthSamples.computeMatrix(la,lb,lc);
		swPants.setHyperbolicMatrix(mat1);

		CurveList listOfCurves = new CurveList(swPants);
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[wordLength] , swPants.length());
	//	PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[wordLength] , swPants.length());
		//PrimitiveDoublyPalCyclicWord dcw = new PrimitiveDoublyPalCyclicWord(new int[wordLength] , swPants.length());
		Curve c = new Curve(dcw, swPants);
		c.initialize();


		//We add to the curve-list listOfCurves1 ALL curve-words of combinatorial length CombLength 
		while(c.length()==wordLength){
			listOfCurves.add(c);		
			c = c.next();
		}
		//Sort the list
		listOfCurves = listOfCurves.sortedCurveList(new SortByHyperbolicLength());
		ArrayList<CurveList> al = Algorithms.equivalentClassByHyperbolicLength(listOfCurves, error);		
		ArrayList<CurveList> aux = new ArrayList<CurveList>();

		for(int u =0 ; u < al.size() ; u++){
			if(al.get(u).size()>sizeClass){	
				aux.add(al.get(u));
			}

		}

		return aux;

	}
	public static ArrayList<CurveList> checkListTrace0(int wordLength, double error, double[] m1, double[] m2, int sizeClass){
		
		//	System.out.println(sentence);
		SurfaceWord swPants = new SurfaceWord("aAbB");
		double[][] mat1 = {m1,m2};
		swPants.setHyperbolicMatrix(mat1);

		CurveList listOfCurves = new CurveList(swPants);
		PrimitiveDoublyCyclicWord dcw = new PrimitiveDoublyCyclicWord(new int[wordLength] , swPants.length());
		//PrimitiveDoublyPalCyclicWord dcw = new PrimitiveDoublyPalCyclicWord(new int[wordLength] , swPants.length());
		Curve c = new Curve(dcw, swPants);
		c.initialize();


		//We add to the curve-list listOfCurves1 ALL curve-words of combinatorial length CombLength 
		while(c.length()==wordLength){
			listOfCurves.add(c);
		//	System.out.println(swPants.toString()+" " +c.toString()+"\t"+c.hyperbolicTrace());
			c = c.next();
		}
		//Sort the list
		listOfCurves = listOfCurves.sortedCurveList(new SortByHyperbolicTrace());
				ArrayList<CurveList> al = Algorithms.equivalentClassByTrace(listOfCurves, error);		
		ArrayList<CurveList> aux = new ArrayList<CurveList>();

		for(int u =0 ; u < al.size() ; u++){
			if(al.get(u).size()>= sizeClass){	
				
				aux.add(al.get(u));
			}

		}

		return aux;

	}


	public static ArrayList<CurveList> checkList( double error, double la, double lb, double lc, 
			ArrayList<CurveList> listOfClasses, int sizeClass){


		SurfaceWord swPants = new SurfaceWord("aAbB");
		double[][] mat1 = hypLengthComp.LengthSamples.computeMatrix(la,lb,lc);
		swPants.setHyperbolicMatrix(mat1);


		//to store the answer
		ArrayList<CurveList>  answer  =  new ArrayList<CurveList>();

		for(int h = 0 ; h < listOfClasses.size() ; h++){
			CurveList curvelist = listOfClasses.get(h);

			if(curvelist.size()>sizeClass){

				CurveList  aux  =  new CurveList(swPants);
				//get every element in the class curvelist and look at it with the new metric.
				for(int h1 = 0 ; h1 < curvelist.size() ; h1++){
					Curve cc3 = new Curve(curvelist.get(h1).baseword, swPants);
					aux.add(cc3);					
				}
				aux = aux.sortedCurveList(new SortByHyperbolicLength());

				ArrayList<CurveList> classesAux=Algorithms.equivalentClassByHyperbolicLength(aux, error);

				//printListSI(classesAux,"test", swPants);

				for(int s =0 ; s <classesAux.size() ; s++ ){

					if(classesAux.get(s).size()>sizeClass){

						//	printListOfCurvesSILength(classesAux.get(s), "test", swPants);

						answer.add(classesAux.get(s));

					}
				}
			}
		}
		//printListSI(answer,"test", swPants);
		return answer;

	}
	
	public static ArrayList<CurveList> checkListTrace( double error, double[] m1, double[] m2,ArrayList<CurveList> listOfClasses, int sizeClass){


		SurfaceWord swPants = new SurfaceWord("aAbB");
		double[][] mat1 = {m1,m2};
		swPants.setHyperbolicMatrix(mat1);


		//to store the answer
		ArrayList<CurveList>  answer  =  new ArrayList<CurveList>();

		for(int h = 0 ; h < listOfClasses.size() ; h++){
			CurveList curvelist = listOfClasses.get(h);

			if(curvelist.size()>sizeClass){

				CurveList  aux  =  new CurveList(swPants);
				//get every element in the class curvelist and look at it with the new metric.
				for(int h1 = 0 ; h1 < curvelist.size() ; h1++){
					Curve cc3 = new Curve(curvelist.get(h1).baseword, swPants);
					aux.add(cc3);					
				}
				aux = aux.sortedCurveList(new SortByHyperbolicLength());

				ArrayList<CurveList> classesAux=Algorithms.equivalentClassByTrace(aux, error);

				//printListSI(classesAux,"test", swPants);

				for(int s =0 ; s <classesAux.size() ; s++ ){

					if(classesAux.get(s).size()>sizeClass){

						//	printListOfCurvesSILength(classesAux.get(s), "test", swPants);

						answer.add(classesAux.get(s));

					}
				}
			}
		}
		//printListSI(answer,"test", swPants);
		return answer;

	}

	public static void check(int combLength, double error){


		String dir ="EC2013/";
		statics.CreateDirectory.create(dir);
		String filename = dir+"CounterexamplesToConjectureSI"+"_Error Exponent: "+ error+"_CombLengh_"+combLength;
		String sentence=
				"\n\n\nWord length: "+combLength+"\n"
						+"Error: "+error+"\n\n";
		System.out.println(sentence);


		SurfaceWord swTorus = new SurfaceWord("abAB");
		SurfaceWord swPants1 = new SurfaceWord("aAbB");
		SurfaceWord swPants2 = new SurfaceWord("aAbB");
		SurfaceWord swPants3 = new SurfaceWord("aAbB");

		double[][] mat1 = hypLengthComp.LengthSamples.computeMatrix(1,2,10);
		double[][] mat2 = hypLengthComp.LengthSamples.computeMatrix(10, 1, 0.5);
		double[][] mat3 = hypLengthComp.LengthSamples.computeMatrix(1, 1, 1);

		swPants1.setHyperbolicMatrix(mat1);
		swPants2.setHyperbolicMatrix(mat2);
		swPants3.setHyperbolicMatrix(mat3);

		CurveList listOfCurves1 = new CurveList(swPants1);
		PrimitiveDoublyPalCyclicWord dcw = new PrimitiveDoublyPalCyclicWord(new int[combLength] , swPants1.length());
		Curve c = new Curve(dcw, swPants1);
		c.initialize();

		//We add to the curve-list listOfCurves1 ALL curve-words of combinatorial length CombLength 
		while(c.length()==combLength){
			listOfCurves1.add(c);		
			c = c.next();
		}
		//Sort the list
		listOfCurves1 = listOfCurves1.sortedCurveList(new SortByHyperbolicLength());
		ArrayList<CurveList> classes1=Algorithms.equivalentClassByHyperbolicLength(listOfCurves1, error);	





		//	boolean foundLargeCounterEx = false;
		boolean foundCounterEx =false;
		for(int i=0;i<classes1.size();i++){

			CurveList classFromCurveList1 = classes1.get(i);
			if(classFromCurveList1.size()>2){// & ai.size()!=4 & ai.size()!=8){
				CurveList classFromCurveList1_InTermsOfPants2 = new CurveList(swPants2);
				for(int s =0 ; s < classFromCurveList1.size() ; s++){
					Curve cc2 = new Curve(classFromCurveList1.get(s).baseword, swPants2);
					classFromCurveList1_InTermsOfPants2.add(cc2);
				}
				ArrayList<CurveList> subClasses12 = Algorithms.equivalentClassByHyperbolicLength(classFromCurveList1_InTermsOfPants2, error);	

				for(int u = 0 ; u <  subClasses12.size() ; u++){
					CurveList classFromCurveList12 = subClasses12.get(u);
					if(classFromCurveList12.size()>2){
						CurveList classFromCurveList12_InTermsOfPants3 = new CurveList(swPants3);
						for(int s3 = 0 ; s3 < classFromCurveList12.size() ; s3++){
							Curve cc3 = new Curve(classFromCurveList12.get(s3).baseword, swPants3);
							classFromCurveList12_InTermsOfPants3.add(cc3);
						}
						ArrayList<CurveList> subClasses123 = Algorithms.equivalentClassByHyperbolicLength(classFromCurveList12_InTermsOfPants3, error);	
						for(int u3 = 0 ; u3 < subClasses123.size() ; u3++){
							CurveList finalClass = subClasses123.get(u3);
							if(finalClass.size() > 1){
								sentence=
										"\n\n\nWord length: "+combLength+"\n"
												+"Class size: "+finalClass.size()+"\n"+
												"Error: "+error+"\n\n";
								foundCounterEx = true;

								//	Curve cpants = new Curve(finalClass.get(0).baseword, swPants1);
								//	Curve ctorus = new Curve(finalClass.get(0).baseword, swTorus);

								for(int st = 0 ; st < finalClass.size(); st++){

									Curve cpantsst = new Curve(finalClass.get(st).baseword, swPants1);
									Curve ctorusst = new Curve(finalClass.get(st).baseword, swTorus);

									///	boolean hom = statics.DealWithStrings.hom(cpantsst.toString(),cpants.toString());
									//	boolean interTorus = ctorusst.selfIntersection() != ctorus.selfIntersection();
									//	boolean interPants =cpantsst.selfIntersection() != cpants.selfIntersection();

									sentence=sentence+
											cpantsst.toString()+"\t"+cpantsst.selfIntersection()+"\t"+ctorusst.selfIntersection()+"\n"; 

								}

								if(foundCounterEx){
									statics.FileStuff.fileSentence(filename,sentence+"\n");
								}
							}
						}

					}	


				}


			}	

		}
	}



}






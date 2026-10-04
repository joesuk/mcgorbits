package a_orbits;

import java.util.HashSet;
import java.util.Iterator;

import boundary.Curve;
import boundary.CyclicWord;
import boundary.PrimitiveDoublyCyclicWord;
import boundary.SurfaceWord;
import boundary.Word;

public class Mcg {





	/**
	 * Takes a word s
	 *  replaces b->ab, B->BA
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String ab(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a' || s.charAt(h)=='A') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='b') answer = answer+ "ab";
			else answer= answer +"BA";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}




	/**
	 * Takes a word s
	 *  //replaces b ->Ab, B->Ba
	 *  
	 * @param s
	 * @return s after replacement
	 */
	public static String Ab(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a' || s.charAt(h)=='A') answer = answer+s.charAt(h);
			else if (s.charAt(h)=='b') answer = answer+ "Ab";
			else answer= answer +"Ba";

		}
		//System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		Word w1=w.reduce();
		Word w2=w1.permute(2);
		w1=w2.reduce();
		return w1.toString();
	}



	/**
	 * Takes a word s
	 * Swaps a and b, A and B.
	 * Return the word after all swaps
	 * 
	 * @param s
	 * @return
	 */
	public static String swab(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a') answer = answer+'b';
			else if (s.charAt(h)=='b') answer = answer+ 'a';
			else if (s.charAt(h)=='A') answer = answer+ 'B';
			else answer= answer +"A";

		}
		//	System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		w.reduce();
		return w.toString();
	}




	/**
	 * Takes a word s.
	 * Swaps b and B.
	 * @param s
	 * @return
	 */
	public static String bB(String s){
		String answer ="";


		for(int h = 0 ; h < s.length() ; h++){
			if(s.charAt(h)=='a') answer = answer+'a';
			else if (s.charAt(h)=='b') answer = answer+ 'B';
			else if (s.charAt(h)=='A') answer = answer+ 'A';
			else answer= answer +"b";

		}
		//	System.out.println(answer);
		Word w = Word.fromString(answer, 4);
		w.reduce();
		return w.toString();
	}


	/**
	 * Takes a word s, a character x.
	 * Returns how many occurrences of x are there in s.
	 * @param s
	 * @param x
	 * @return
	 */
	public static int count(String s, char x){
		int answer=0;
		for(int h =0 ; h< s.length() ; h++){
			if(s.charAt(h)==x) answer++;
		}
		return answer;
	}


	/**
	 * Takes a word s.
	 * REturns s in standard form.
	 * @param sent1
	 * @return
	 */
	public static String normalize(String sent1){



		//	System.out.println(sent1+" \t "+sent2);
		Word w1 = new Word(Word.fromString(sent1,4));
		int[] p1 =w1.getWord();
		CyclicWord w11 = new CyclicWord(p1,4);

		if(w11.isDoublySmallest()){
			return w11.toString();	
		}else{
			w1= Word.bar(w11);	
			p1=w1.getWord();
			w11= new CyclicWord(p1,4);
			return w11.toString();
		}



	}	



	/**
	 * Take a word st.
	 * Returns a set containing ab(st), Ab(st), swab(st) and bB(st)
	 * 
	 * @param st
	 * @return
	 */
	public static HashSet<String> once(String st){
		HashSet<String> answer = new HashSet<String>();

		answer.add(normalize(ab(st)));
		answer.add(normalize(Ab(st)));
		answer.add(normalize(swab(st)));
		answer.add(normalize(bB(st)));
		return answer;
	}









	/**
	 * Takes a set of words "curs".
	 * Returns a set containing all the word obtaining applying each of teh generators to each of the elements of curus.
	 * @param curs
	 * @return
	 */
	public static HashSet<String> applyOnceToASet(HashSet<String> curs){
		HashSet<String> answer = new HashSet<String>();
		Iterator<String> iterator = curs.iterator();
		while (iterator.hasNext()) {
			String s= iterator.next();
			answer.addAll(once(s));

		}
		return answer;
	}

	/**
	 * Takes a set of words "curs".
	 * Returns a set containing all the word obtaining applying each of teh generators to each of the elements of curus which are not longer 
	 * than boundWLOrbit
	 * @param curs
	 * @return
	 */
	
	public static HashSet<String> applyOnceToASetBounded(HashSet<String> curs, int boundWLOrbit){
		HashSet<String> answer = new HashSet<String>();
		Iterator<String> iteratorCurs = curs.iterator();
		while (iteratorCurs.hasNext()) {
			String s= iteratorCurs.next();
			HashSet children = once(s);
			Iterator<String> chilIterator = children.iterator();
			while(chilIterator.hasNext()){
				String st=chilIterator.next();
				//System.out.println(st);
				if(st.length()<=boundWLOrbit)
					answer.addAll(children);
			}

		}
		//	System.out.println("This size is "+answer.size());
		return answer;
	}



	public static HashSet<String> findOrbitsShortWL(int times, HashSet<String> answer, String dir, int maxWL,int boundWLOrbit, String filename){

		String intial="";
		Iterator<String> it1 = answer.iterator();
		while(it1.hasNext()){
			intial=intial+", "+it1.next();}

		statics.CreateDirectory.create(dir);
		filename =dir+"/"+filename+"_count";
		long tStart = System.currentTimeMillis();

		for(int h =1 ; h  < times  ; h=h+1){

			int total[]= new int[maxWL+1];
		//	int[][] mat = new int[boundWLOrbit][boundWLOrbit];
			
			//this is just to see how long does it take this process "applyOnceToASetBounded"
			tStart = System.currentTimeMillis();

			answer = applyOnceToASetBounded(answer,boundWLOrbit);

			//this is the end of couting the time and printing and storing  the results.
			long tEnd = System.currentTimeMillis();
			long tDelta = tEnd - tStart;
			double elapsedSeconds = tDelta / 1000.0;
			System.out.println(h+" done in "+elapsedSeconds+"\n The number of elements is "+answer.size());
			statics.FileStuff.fileSentence("aux", filename+" "+h+" done in "+elapsedSeconds+"\n The number of elements is "+answer.size());
			
			//the next block is to count the number of elements in the found orbit for each word length.
			Iterator<String> it = answer.iterator();
			while(it.hasNext()){

				String s = (String) it.next();
				if(s.length()<=maxWL){	
					total[s.length()]++;
					//	mat[s.length()-1][Math.abs((count(s,'a')-count(s,'A')))+Math.abs((count(s,'b')-count(s,'B')))]++;
					//		statics.FileStuff.fileSentence(filename+"_"+times+"_"+s.length(), s+"\t"+s.length()+"\t"+
					//	(count(s,'a')-count(s,'A'))+"\t"+(count(s,'b')-count(s,'B')));
				}
			}

			System.out.println(h+"  "+intial );
			if(h> times-2){
				for(int hh =5  ;  hh <=maxWL ; hh++){		
					System.out.println(hh+"\t"+total[hh] );
				}

				System.out.println("The end of "+ h  +" \n\n" );

				statics.FileStuff.fileVector(total, filename+"_ve"+h,filename+"_ve"+h);
			}
		}

		//statics.FileStuff.fileSentence(filename+times+"count", u+" \t" +counter);
		//statics.FileStuff.fileSentenceNoNewLine(filename+times+"ckust", counter+", ");
		
		//prepares the answer, removing the words longer than maxWL
		HashSet<String> hs = new HashSet<String>();
		Iterator<String> it = answer.iterator();
		while(it.hasNext()){
			String st = it.next();
			if(st.length()<=maxWL) 
				hs.add(st);
		}


		return hs;


	}

	
	
	
	public static HashSet<String> findOrbits(int times, HashSet<String> answer, String dir, int maxWL){


		String intial="";
		Iterator<String> it1 = answer.iterator();
		while(it1.hasNext()){
			intial=intial+", "+it1.next();
		}

		statics.CreateDirectory.create(dir);
		String filename =dir+ "/count";
		long tStart = System.currentTimeMillis();
		int size=60;
		for(int h =1 ; h  <= times  ; h=h+1){
			int total[]= new int[size];
			int[][] mats = new int[size][size];
			tStart = System.currentTimeMillis();

			answer = applyOnceToASet(answer);

			long tEnd = System.currentTimeMillis();
			long tDelta = tEnd - tStart;
			double elapsedSeconds = tDelta / 1000.0;
			System.out.println(h+" done in "+elapsedSeconds);

			Iterator<String> it = answer.iterator();
			while(it.hasNext()){
				String s = (String) it.next();

				if(s.length()<size){
					//	if(s.length()==9 || s.length()==10){
					//System.out.println(s.toString());
					total[s.length()]++;
					//	mat[s.length()-1][Math.abs((count(s,'a')-count(s,'A')))+Math.abs((count(s,'b')-count(s,'B')))]++;
					//	statics.FileStuff.fileSentence(filename+"_"+times+"_"+s.length(), s+"\t"+s.length()+"\t"+
					//		(count(s,'a')-count(s,'A'))+"\t"+(count(s,'b')-count(s,'B')));					
				}
			}
			//	System.out.println(h+"  "+intial );
			for(int hh =5  ;  hh <30 ; hh++){	
				//	System.out.println(hh+"\t"+total[hh] );
			}
			//	System.out.println(10+"\t"+total[10] );
			System.out.println("The end of "+ h  +" \n\n" );
			//		statics.FileStuff.fileMatrix(mat, filename+"_"+h);
			statics.FileStuff.fileVector(total, filename+h,filename+h);

		}
		//statics.FileStuff.fileSentence(filename+times+"count", u+" \t" +counter);
		//statics.FileStuff.fileSentenceNoNewLine(filename+times+"ckust", counter+", ");
		HashSet<String> hs = new HashSet<String>();
		Iterator<String> it = answer.iterator();
		while(it.hasNext()){
			String st = it.next();

			if(st.length()<=2*maxWL){
				hs.add(st);
				//	System.out.println(st);
			}
		}
		System.out.println("Size "+hs.size() +" answer "+answer.size());
		//return answer;
		return hs;


	}











}


/*	
 * 
 * 
 * 
 
 	
	
 
 public static HashSet<String> defineSet2(){
String s5= "aabABabAB";
String s6= "aaabAB";
String s7= "aaabb";
String s8= "abaBabAB";
String s9= "aabAAB";
String s10= "aabaB";
//wl-si



HashSet<String> answer = new HashSet<String>();
answer.add(s5);

//answer.add(s6);
//answer.add(s7);
//answer.add(s8);
//answer.add(s9);
//answer.add(s10);

return answer;
}
 */





/*	public static HashSet<String> defineSet3(){
String s1= "aabABabABabAB";
String s2="aaabABabAB";
String s3="aabAbaBAb";
String s4="aabAABabAB";
String s5="abaBAbaBAbaB";
String s6= "aaaabAB";
String s7= "aabbAB";
String s8= "aabaBabAB";
String s9= "aabABabaB";
String s10= "aabABabaB";
String s11= "aaabAAB";
String s12= "aaaabb";
String s13= "aaabaB";
String s14= "aabaaB";
String s15= "abaBAbAB";
//wl-si



HashSet<String> answer = new HashSet<String>();



HashSet<String> m1 = new HashSet<String>();
m1.add(s1);
m1.add(s3);
m1.add(s7);


HashSet<String> m2 = new HashSet<String>();
m2.add(s2);
m2.add(s4);
m2.add(s5);
m2.add(s12);
m2.add(s15);


HashSet<String> m3 = new HashSet<String>();
m3.add(s6);
m3.add(s9);
m3.add(s8);
m3.add(s10);
m3.add(s11);

HashSet<String> m4 = new HashSet<String>();

m4.add(s13);
m4.add(s14);



answer.addAll(m1);
answer.addAll(m2);
answer.addAll(m3);
answer.addAll(m4);

return answer;
}*/




//	public static boolean cutWord(String st){
//
//		int det;
//		for(int h = 0 ; h < st.length()-1; h++){
//			for(int j = h+1; j < st.length(); j++){
//				String s1= st.substring(0, h)+st.substring(j, st.length());
//				String s2=st.substring(h,j);
//
//				det = (count(s1,'a')-count(s1,'A'))*
//						(count(s2,'b')-count(s2,'B'))-
//						(count(s1,'b')-count(s1,'B'))*
//						(count(s2,'a')-count(s2,'A'));
//
//				if(det==1 || det ==-1){
//					System.out.println(h+"\t"+j+"\t"+s1+"\t"+s2+"\t"+det);
//					//	 return true;
//
//
//				}
//
//			}
//		}
//
//
//
//		return false; 
//	}


